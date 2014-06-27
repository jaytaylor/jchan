package fr.loof.jchan.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
public class Data {

    public static void encodeHeader(int msgtype, StringBuilder sb) {
        sb.append(String.format("%0,3d;", msgtype));
    }

    public static String encodeString(CharSequence s) {
        return encodeString(s, new StringBuilder()).toString();
    }
    public static StringBuilder encodeString(CharSequence s, StringBuilder sb) {
        return sb.append(String.format("%d:%s,", s.length(), s));
    }

    public static String encodeList(Collection<String> strings) {
        return encodeList(strings, new StringBuilder()).toString();
    }

    private static StringBuilder encodeList(Collection<String> strings, StringBuilder msg) {
        StringBuilder sb = new StringBuilder();
        for (String s : strings) {
            encodeString(s, sb);
        }
        encodeString(sb, msg);
        return msg;
    }

    private static StringBuilder encodeNamedList(String name, Collection<String> value, StringBuilder msg) {
        encodeString(name, msg);
        encodeList(value, msg);
        return msg;
    }

    public static String encode(Map<String, Collection<String>> m) {
        StringBuilder msg = new StringBuilder();
        encodeHeader(0, msg);
        for (Map.Entry<String, Collection<String>> e : m.entrySet()) {
            encodeNamedList(e.getKey(), e.getValue(), msg);
        }
        return msg.toString();
    }

    public static Map<String, Collection<String>> decode(String s) {

        Map<String, Collection<String>> map = new HashMap<>();

        StringBuilder msg = new StringBuilder(s);
        int msgtype = decodeHeader(msg);
        if (msgtype != 0) {
            throw new IllegalArgumentException("unknown message type :"+msgtype);
        }

        while (msg.length() >= 0) {
            String key = decodeString(msg);
            List<String> values = decodeList(msg);
            map.put(key, values);
        }
        return map;
    }

    public static int decodeHeader(StringBuilder msg) {
        if (msg.length() < 4) {
            throw new IllegalArgumentException("message too small");
        }
        int msgtype = Integer.parseInt(msg.substring(0,3));
        msg.delete(0, 4);
        return msgtype;
    }

    public static String decodeString(StringBuilder msg) {
        int i = msg.indexOf(":");
        if (i < 0) {
            throw new IllegalArgumentException("invalid format: no column");
        }
        int length = Integer.parseInt(msg.substring(0,i));
        msg.delete(0,i+1);
        if (msg.length() < length+1) {
            throw new IllegalArgumentException(String.format("message '%s' is %d bytes, expected at least %d", msg, msg.length(), length+1));
        }

        String payload = msg.substring(0, length+1);
        msg.delete(0,length);

        if (payload.charAt(length) != ',') {
            throw new IllegalArgumentException("message is not comma-terminated");
        }
        msg.delete(0,1);
        return payload.substring(0, length);
    }

    public static List<String> decodeList(StringBuilder msg) {
        StringBuilder blob = new StringBuilder(decodeString(msg));

        List<String> strings = new ArrayList<>();
        while (blob.length() >= 0) {
            strings.add(decodeString(blob));
        }
        return strings;
    }

}
