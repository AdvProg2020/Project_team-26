package view;

import org.hibernate.dialect.SybaseAnywhereDialect;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InputOutput implements IO {
    public static List<String> input = new ArrayList<>();
    public static int inputNumber = 0;
    public static List<String> output = new ArrayList<>();
    public static int outPutNumber = 0;

    @Override
    public String nextLine() {
        inputNumber++;
        return input.get(inputNumber - 1);
    }

    @Override
    public void println(String input) {
        output.add(input);
    }

    @Override
    public void printDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String strDate = formatter.format(date);
        System.out.println(strDate);
    }

    public static List<String> getOutput() {
        return output;
    }
}
