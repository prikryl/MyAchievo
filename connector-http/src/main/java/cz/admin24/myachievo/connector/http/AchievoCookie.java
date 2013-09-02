package cz.admin24.myachievo.connector.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.time.DateUtils;

public class AchievoCookie {
    private static final Integer SESSION_TIMEOUT = 15; // minutes

    private final String         httpCookie;
    private final String         user;
    private final Long           expirationTime;
    private String               login;
    private String               name;
    private String               userId;


    public AchievoCookie(HttpURLConnection conn) throws IOException {
        httpCookie = conn.getHeaderField("Set-Cookie");
        user = conn.getHeaderField("user");
        expirationTime = DateUtils.addMinutes(new Date(), SESSION_TIMEOUT).getTime();
    }


    /**
     * Expired cookie construtor
     */
    public AchievoCookie() {
        httpCookie = null;
        user = null;
        expirationTime = 0L;
    }


    public boolean isExpired() {
        return expirationTime <= System.currentTimeMillis();
    }


    public void apply(HttpURLConnection conn) {
        conn.setRequestProperty("Cookie", httpCookie);
        conn.setRequestProperty("user", user);
    }


    public void setLogin(String login) {
        this.login = login;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getLogin() {
        return login;
    }


    public String getName() {
        return name;
    }


    public String getUserId() {
        return userId;
    }


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE, false, false);
    }
}
