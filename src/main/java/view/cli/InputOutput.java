package view.cli;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class InputOutput implements IO {
    public static List<String> input = new ArrayList<>();
    public static int inputNumber = 0;
    public static List<String> output = new ArrayList<>();
    public static int outPutNumber = 0;
    public static String now;
    String inputFile;
    Scanner scanner;
    String fileName = "temp.txt";
    BufferedWriter bufferedWriter;
    FileWriter fileWriter;

    public InputOutput(String console) {
        if (console.equals("console"))
            scanner = new Scanner(System.in);
    }

    public InputOutput() {
        try {
            fileWriter = new FileWriter(fileName);
            bufferedWriter = new BufferedWriter(fileWriter);
        } catch (IOException e) {
            println(e.getMessage());
        }
    }

    @Override
    public String nextLine() {
        return scanner.nextLine();
    }

    @Override
    public void println(String input) {
        System.out.println(input);
    }

    public void printlnForTest(String input) {
        output.add(input);
        now = input;

    }

    public String nextLineForTest() {
        inputNumber++;
        inputFile = input.get(inputNumber - 1);
        try {
            bufferedWriter.write(inputFile);
            bufferedWriter.newLine();
        } catch (IOException e) {
            println(e.getMessage());
        }
        return inputFile;
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
