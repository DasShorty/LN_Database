package com.laudynetwork.database.mysql;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SQLWrapper {

    public static String fromMapToString(Map<String, String> values) {
        return Joiner.on(",").withKeyValueSeparator("=").join(values);
    }

    public static String fromListToString(List<String> value) {

        StringBuilder builder = new StringBuilder();

        for (String s : value) {
            builder.append(s).append(",");
        }

        return builder.substring(0, Math.max(builder.toString().length() - 1, 0));
    }

    public static Map<String, String> fromStringToMap(String input) {
        return Splitter.on(",").withKeyValueSeparator("=").split(input);
    }

    public static List<String> fromStringToList(String input) {
        return new ArrayList<>(Arrays.asList(input.split(",")));
    }
}
