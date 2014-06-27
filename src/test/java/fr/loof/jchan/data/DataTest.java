package fr.loof.jchan.data;


import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.*;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class DataTest {

    @Test
    public void encode_HelloWorld() {
        assertEquals("12:hello world!,", Data.encodeString("hello world!"));
    }

    @Test
    public void encode_empty_string() {
        assertEquals("0:,", Data.encodeString(""));
    }

    @Test
    public void encode_empty_list() {
        assertEquals("0:,", Data.encodeList(EMPTY_LIST));
    }

    @Test
    public void encode_empty_map() {
        assertEquals("000;", Data.encode(EMPTY_MAP));
    }

    @Test
    public void encode_1key_1value() {
        Map<String, Collection<String>> m = new HashMap<>();
        m.put("hello", Collections.singleton("world"));
        assertEquals("000;5:hello,8:5:world,,", Data.encode(m));
    }

    @Test
    public void encode_1key_2value() {
        Map<String, Collection<String>> m = new HashMap<>();
        m.put("hello", Arrays.asList("beautiful", "world"));
        assertEquals("000;5:hello,20:9:beautiful,5:world,,", Data.encode(m));
    }

    @Test
    public void encode_empty_value() {
        Map<String, Collection<String>> m = new HashMap<>();
        m.put("foo", EMPTY_LIST);
        assertEquals("000;3:foo,0:,", Data.encode(m));
    }

    @Test
    public void encode_empty_binary_key() {
        Map<String, Collection<String>> m = new HashMap<>();
        m.put("foo\u0000bar\u007f", Collections.singleton("\u0001\u0002\u0003\u0004"));
        assertEquals("000;8:foo\u0000bar\u007f,7:4:\u0001\u0002\u0003\u0004,,", Data.encode(m));
    }

    @Test
    public void decode_string() {
        StringBuilder sb = new StringBuilder("3:foo,");
        assertEquals("foo", Data.decodeString(sb));
        assertEquals(0, sb.length());
    }


}