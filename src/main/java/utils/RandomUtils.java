package utils;

import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.String.valueOf;

public class RandomUtils {

    private final static Integer MIN_STRING = 5;
    private final static Integer MAX_STRING = 9;

    private final static String ENGLISH_VOC = "abcdefghijklpnopqrstuvwxyz";

    public static String genENString(int count, boolean firstUp) {
        Random random = new Random();
        StringBuilder out = new StringBuilder();

//        boolean first = true;
        for (int i = 0; i < count; ++i) {
            String s = valueOf(ENGLISH_VOC.toCharArray()[abs(random.nextInt()) % ENGLISH_VOC.length()]);
            if (firstUp) {
                s = s.toUpperCase();
                firstUp = false;
            }
            out.append(s);
        }

        return out.toString();
    }

    public static String genENString(boolean firstUp) {
        Random random = new Random();
        int c = abs(random.nextInt()) % (MAX_STRING - MIN_STRING) + MIN_STRING;
        return genENString(c, firstUp);
    }

    public static String genENString(int count) {
        return genENString(count, false);
    }

    public static String genENString() {
        Random random = new Random();
        int c = abs(random.nextInt()) % (MAX_STRING - MIN_STRING) + MIN_STRING;

        return genENString(c);
    }

    public static String genSameLetter(int count) {
        Random random = new Random();
        StringBuilder out = new StringBuilder();

        String s = valueOf(ENGLISH_VOC.toCharArray()[abs(random.nextInt()) % ENGLISH_VOC.length()]);
        out.append(s.repeat(Math.max(0, count)));
        return out.toString();
    }

    public static String genEmail(String domain) {
        return genENString() + "@" + domain;
    }

    public static String genEmail() {
        return genEmail(genENString() + ".com");
    }
}
