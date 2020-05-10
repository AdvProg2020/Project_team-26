package view;

import java.util.ArrayList;
import java.util.List;

public class InputOutput implements IO {
    public static List<String> output = new ArrayList<>();
    public static int numbers = 0;
    @Override
    public String nextLine() {
        numbers++;
        return output.get(numbers-1);
    }

    @Override
    public void println(String input) {

    }
}
