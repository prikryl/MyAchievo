package cz.admin24.myachievo.connector.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistentConnection {
    private static final Logger  LOG                   = LoggerFactory.getLogger(PersistentConnection.class);

    private static final int     CONNECT_TIMEOUT       = 15000 /* milliseconds */;
    private static final int     READ_TIMEOUT          = 15000 /* milliseconds */;
    private static final String  CHARSET               = "utf-8";
    private static final int     MAX_RECONNECT_COUNT   = 5;
    private static final Pattern USER_INFO_PATTERN     = Pattern
                                                               .compile("(?ms).*: <b><b>([^)]+)</b> \\[([^)]+)\\]</b> &nbsp;.*atknodetype=employee.userprefs&atkaction=edit&atkselector=person.id%3D%27([^%]+)%27&atklevel.*");
    private static final String  PATTERN_INVALID_LOGIN = "Username and/or password are incorrect. Please try again.";
    //
    private final String         loginUrlString        = "https://timesheet.trask.cz/achievo/index.php";
    private final String         userInfoUrlString     = "https://timesheet.trask.cz/achievo/top.php";
    private final String         dispatchUrlString     = "https://timesheet.trask.cz/achievo/dispatch.php?";
    //
    private final URL            loginUrl;
    private final URL            userInfoUrl;
    private final URL            dispatchUrl;
    //
    private AchievoCookie        cookie                = new AchievoCookie();

    private String               username;

    private String               password;


    public PersistentConnection() {
        try {
            loginUrl = new URL(loginUrlString);
            dispatchUrl = new URL(dispatchUrlString);
            userInfoUrl = new URL(userInfoUrlString);
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Invalid url", e);
        }
    }


    public String doGet(URL url) throws IOException {

        HttpURLConnection conn = getAuthorizedConnection(url);
        try {
            conn.setRequestMethod("GET");
            conn.connect();
            int responseCode = conn.getResponseCode();
            String responseMessage = conn.getResponseMessage();
            String responseBody = readIs(conn);
            LOG.debug("Reponse code: '{}'", responseCode);
            LOG.trace("Response message: '{}'", responseMessage);
            LOG.trace("Response body: '{}'", responseBody);
            return responseBody;
        } finally {
            conn.disconnect();
        }
    }


    public String doPost(Map<String, String> params) throws IOException {
        return doPost(params, dispatchUrl);
    }


    public String doPost(Map<String, String> params, URL url) throws IOException {

        HttpURLConnection conn = getAuthorizedConnection(url);
        try {
            setParams(params, conn);
            conn.connect();
            int responseCode = conn.getResponseCode();
            String responseMessage = conn.getResponseMessage();
            String responseBody = readIs(conn);
            LOG.debug("Reponse code: '{}'", responseCode);
            LOG.trace("Response message: '{}'", responseMessage);
            LOG.trace("Response body: '{}'", responseBody);
            return responseBody;
        } finally {
            conn.disconnect();
        }
    }


    public String getUserName() {
        authorizeIfRequired();
        return cookie.getName();
    }


    public String getUserId() {
        authorizeIfRequired();
        return cookie.getUserId();
    }


    private HttpURLConnection getAuthorizedConnection(URL url) {
        authorizeIfRequired();
        return createConnectionByCookie(url);
    }


    private HttpURLConnection createConnectionByCookie(URL url) {
        // there is valid cookie, build new connection
        int counter;
        for (counter = 0; counter <= MAX_RECONNECT_COUNT; counter++) {
            LOG.debug("Building new connection based on cookie {}...", cookie);
            try {
                HttpURLConnection authorizedConnection = createAuthorizedConnection(url);
                LOG.debug("Authorized connection created.");
                return authorizedConnection;
            } catch (IOException e) {
                LOG.error("Failed to connect to '{}'. Msg: {}", e.getMessage(), e);
            }
        }

        LOG.error("Failed to connect to '{}'. Connection retried {}", loginUrl, counter + "/" + MAX_RECONNECT_COUNT);
        throw new IllegalStateException("Failed to connect to: " + loginUrl);
    }


    private void authorizeIfRequired() {
        int counter = 0;
        while (cookie.isExpired()) {
            LOG.debug("Cookie '{}' is expired. Reconnecting...", cookie);
            try {
                reconnect();
            } catch (IOException e) {
                LOG.error("Failed to connect to '{}'. Msg: {}", e.getMessage(), e);
            }
            counter++;
            if (counter >= MAX_RECONNECT_COUNT) {
                LOG.error("Failed to connect to '{}'. Connection retried {}", loginUrl, counter + "/" + MAX_RECONNECT_COUNT);
                throw new IllegalStateException("Failed to connect to: " + loginUrl);
            }
        }
    }


    private HttpURLConnection createAuthorizedConnection(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(READ_TIMEOUT);
        conn.setConnectTimeout(CONNECT_TIMEOUT);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setInstanceFollowRedirects(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("charset", CHARSET);
        conn.setUseCaches(false);
        cookie.apply(conn);
        return conn;
    }


    private void reconnect() throws IOException {
        authentize();
        // load user details
        loadUserDetails();
    }


    private void loadUserDetails() throws IOException {
        LOG.debug("Loading user details ...");
        HttpURLConnection conn = createConnectionByCookie(userInfoUrl);
        try {
            conn.setRequestMethod("GET");
            conn.connect();
            String response = readIs(conn);
            Matcher matcher = USER_INFO_PATTERN.matcher(response);
            if (!matcher.matches()) {
                LOG.error("Failed to parse user details from html: {}", response);
                throw new IllegalStateException("Failed to parse user details from html: " + response);
            }
            cookie.setLogin(matcher.group(1));
            cookie.setName(matcher.group(2));
            cookie.setUserId(matcher.group(3));
        } finally {
            conn.disconnect();
        }
        LOG.debug("User details loaded...");
    }


    private void setParams(Map<String, String> params, HttpURLConnection conn) throws IOException {
        LOG.trace("Setteng post parameters:\n{}", params);
        String urlParameters = buildUrlEncodedForm(params);
        conn.setRequestProperty("Content-Length", "" + urlParameters.getBytes().length);

        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        try {
            wr.writeBytes(urlParameters);
        } finally {
            wr.close();
        }

    }


    private Map<String, String> getLoginParam() {
        Map<String, String> params = new HashMap<String, String>();
        if (StringUtils.isBlank(username)) {
            throw new AuthentizationException("No username supplied!");
        }
        params.put("auth_user", username);
        params.put("auth_pw", password);
        params.put("login", "Login");
        return params;

    }


    private String buildUrlEncodedForm(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        for (Iterator<Entry<String, String>> iterator = params.entrySet().iterator(); iterator.hasNext();) {
            Entry<String, String> entry = iterator.next();
            sb.append(encode(entry.getKey()));
            sb.append("=");
            sb.append(encode(entry.getValue()));

            if (iterator.hasNext()) {
                sb.append("&");
            }
        }
        return sb.toString();
    }


    private Object encode(String string) {
        try {
            return URLEncoder.encode(string, CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("unknown encoding " + CHARSET, e);
        }
    }


    private String readIs(HttpURLConnection connection) throws IOException {
        InputStream inputStream = connection.getInputStream();
        String contentEncoding = CHARSET;
        Scanner sc = new Scanner(inputStream, contentEncoding);
        try {
            sc.useDelimiter("\\A");
            return sc.hasNext() ? sc.next() : "";
        } finally {
            sc.close();
        }
    }


    public void setCredentials(String username, String password) {
        this.username = username;
        this.password = password;

    }


    public void authentize() throws IOException, AuthentizationException {
        HttpURLConnection conn = (HttpURLConnection) loginUrl.openConnection();
        try {
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", CHARSET);
            conn.setUseCaches(false);
            setParams(getLoginParam(), conn);
            conn.connect();

            int responseCode = conn.getResponseCode();
            LOG.trace("Response code: {}", responseCode);

            String content = readIs(conn);
            LOG.trace("Content:\n{}", content);
            if (responseCode != HttpURLConnection.HTTP_OK) {
                LOG.warn("Failed to connect to server. Reposnse code {}. Response {}", responseCode, content);
                throw new IOException(MessageFormat.format("Failed to connect to server. Reposnse code {0}. Response {1}", responseCode, content));
            }

            // verify login successfully
            if (content.contains(PATTERN_INVALID_LOGIN)) {
                LOG.debug("Probably invalid login. Throw exception ...");
                throw new AuthentizationException("Probably invalid login. Throw exception ...");
            }
            LOG.debug("Probably login OK.");

            // get cookie
            cookie = new AchievoCookie(conn);
            LOG.info("Authorized with cookie: '{}'", cookie);

        } finally {
            conn.disconnect();
        }
    }

}
