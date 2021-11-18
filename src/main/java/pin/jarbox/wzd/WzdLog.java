package pin.jarbox.wzd;

import pin.jarbox.bin.Console;

public class WzdLog {

    public static void log(String message, Object... values) {
        var builder = new StringBuilder("[ LOG ] ");
        builder.append(values != null ? String.format(message, values) : message);
        builder.append(System.lineSeparator());
        System.out.println(builder.toString());
    }

    public static void treat(String message, Object... values) {
        var builder = new StringBuilder("[ TREAT ] [ MESSAGE ] ");
        builder.append(values != null ? String.format(message, values) : message);
        builder.append(System.lineSeparator());
        builder.append("[ ORIGIN ] ");
        builder.append(Console.getOrigin());
        builder.append(System.lineSeparator());
        System.out.println(builder.toString());
    }

    public static void treat(Throwable error) {
        var builder = new StringBuilder("[ TREAT ] [ ERROR ] ");
        builder.append(error.getMessage());
        builder.append(System.lineSeparator());
        builder.append("[ ORIGIN ] ");
        builder.append(Console.getOrigin(error));
        builder.append(System.lineSeparator());
        System.out.println(builder.toString());
    }

    public static void debug(String topic, String message, Object... values) {
        var builder = new StringBuilder("[ DEBUG ] ");
        builder.append(topic);
        builder.append("[ MESSAGE ] ");
        builder.append(values != null ? String.format(message, values) : message);
        builder.append(System.lineSeparator());
        builder.append("[ ORIGIN ] ");
        builder.append(Console.getOrigin());
        builder.append(System.lineSeparator());
        System.out.println(builder.toString());
    }

    public static void debug(String topic, Throwable error) {
        var builder = new StringBuilder("[ DEBUG ] ");
        builder.append(topic);
        builder.append("[ ERROR ] ");
        builder.append(error.getMessage());
        builder.append(System.lineSeparator());
        builder.append("[ ORIGIN ] ");
        builder.append(Console.getOrigin(error));
        builder.append(System.lineSeparator());
        System.out.println(builder.toString());
    }

}
