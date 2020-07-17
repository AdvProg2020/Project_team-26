package client.gui.interfaces;

import exception.InvalidIdException;
import exception.InvalidTokenException;
import exception.NoAccessException;

import java.io.IOException;

public interface InitializableController {

    void initialize(int id) throws IOException, InvalidTokenException, NoAccessException, InvalidIdException;
}
