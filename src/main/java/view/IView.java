package view;


import exception.AlreadyLoggedInException;

public interface IView {
    public abstract View run() throws AlreadyLoggedInException;
}
