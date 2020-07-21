package client.gui.interfaces;

import client.model.Message;

import java.io.IOException;

public interface MessageReceiver {
    void received(Message message) throws IOException;
}
