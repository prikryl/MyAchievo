package cz.admin24.myachievo.connector.http;

import org.apache.commons.lang3.StringEscapeUtils;

public class EscapeUtils {

    public static String unescape(String str) {
        str = str.replaceAll("&nbsp;", " ");
        str = StringEscapeUtils.unescapeHtml4(str);
        return str;
    }

}
