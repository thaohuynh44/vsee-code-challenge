package com.vsee.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

    /**
     * Extracts the first number found inside parentheses in the given text
     * Returns null if no number is found
     *
     * @param text Input string to search
     * @return Number inside parentheses as String, or null if not found
     */
    public static String extractNumberInParentheses(String text) {
        Pattern pattern = Pattern.compile("\\((\\d+)\\)");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
