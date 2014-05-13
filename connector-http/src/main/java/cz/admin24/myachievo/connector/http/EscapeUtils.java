package cz.admin24.myachievo.connector.http;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.text.translate.UnicodeUnescaper;

public class EscapeUtils {
    private static final UnicodeUnescaper UNICODE_UNESCAPER = new UnicodeUnescaper();

    public static String unescape(String str) {
        str = str.replaceAll("&nbsp;", " ");
        str = UNICODE_UNESCAPER.translate(str);
        str = StringEscapeUtils.unescapeHtml4(str);
        return str;
    }

}
