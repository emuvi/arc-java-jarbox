package pin.jarbox.wzd;

import java.util.Random;

public class WzdRand {

    private static volatile Random rand = null;

    private static void iniciaRand() {
        if (rand == null) {
            rand = new Random();
        }
    }

    public static Boolean getBool() {
        iniciaRand();
        return rand.nextBoolean();
    }

    public static Boolean getBool(Integer min, Integer max, Integer odds) {
        iniciaRand();
        return getInt(min, max) < odds;
    }

    public static Integer getDigit() {
        iniciaRand();
        return rand.nextInt(10);
    }

    public static Integer getInt() {
        iniciaRand();
        return rand.nextInt();
    }

    public static Integer getInt(Integer max) {
        iniciaRand();
        return rand.nextInt(max);
    }

    public static Integer getInt(Integer min, Integer max) {
        iniciaRand();
        return min + rand.nextInt(max - min);
    }

    public static char getChar() {
        return WzdChar.SIMPLE[getInt(0, WzdChar.SIMPLE.length)];
    }

    public static String getChars(int size) {
        String result = "";
        if (size > 0) {
            while (result.length() < size) {
                result = result + getChar();
            }
        }
        return result;
    }

    public static Float getFloat() {
        iniciaRand();
        return rand.nextFloat();
    }

    public static Double getDouble() {
        iniciaRand();
        return rand.nextDouble();
    }

    public static Double getGaussian() {
        iniciaRand();
        return rand.nextGaussian();
    }

    public static <T> T getItem(T[] fromList) {
        if (fromList != null) {
            if (fromList.length > 0) {
                return fromList[getInt(fromList.length)];
            }
        }
        return null;
    }
}
