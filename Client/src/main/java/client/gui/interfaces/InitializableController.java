package client.gui.interfaces;


import client.exception.*;

import java.io.IOException;

public interface InitializableController {

    void initialize(int id) throws IOException, InvalidTokenException, NoAccessException, InvalidIdException;
}
