package view.cli;

import java.util.Date;

public interface IO {

    public String nextLine();
    public void println(String input);
    void printDate(Date date);
}