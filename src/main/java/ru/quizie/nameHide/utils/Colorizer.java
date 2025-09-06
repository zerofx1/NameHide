package ru.quizie.nameHide.utils;

import lombok.experimental.UtilityClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class Colorizer {

    private final Pattern HEX_PATTERN = Pattern.compile("&#([a-fA-F\\d]{6})");

    public String use(String message) {
        if (message == null || message.isEmpty()) {
            return message;
        }
        final Matcher matcher = HEX_PATTERN.matcher(message);
        final StringBuilder buffer = new StringBuilder(message.length() + 32);
        while (matcher.find()) {
            String group = matcher.group(1);
            matcher.appendReplacement(buffer,
                    COLOR_CHAR + "x" + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1) + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3) + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5));
        }
        matcher.appendTail(buffer);
        return translateAlternateColorCodes(buffer.toString());
    }

    private final char COLOR_CHAR = '§';

    private String translateAlternateColorCodes(String textToTranslate) {
        final char[] b = textToTranslate.toCharArray();

        for (int i = 0, length = b.length - 1; i < length; ++i) {
            if (b[i] == '&' && isValidColorCharacter(b[i + 1])) {
                b[i++] = COLOR_CHAR;
                b[i] |= 0x20;
            }
        }

        return new String(b);
    }

    private boolean isValidColorCharacter(char c) {
        return (c >= '0' && c <= '9') ||
                (c >= 'a' && c <= 'f') ||
                c == 'r' ||
                (c >= 'k' && c <= 'o') ||
                c == 'x' ||
                (c >= 'A' && c <= 'F') ||
                c == 'R' ||
                (c >= 'K' && c <= 'O') ||
                c == 'X';
    }
}
