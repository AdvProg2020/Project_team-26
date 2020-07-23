package repository;

import java.util.ArrayList;
import java.util.List;

public class ChatRepository {

    private static ChatRepository instance = new ChatRepository();

    public static ChatRepository getInstance() {
        return instance;
    }

    private List<String> onlineUsers;
    private List<String> onlineSupports;

    public ChatRepository(){
        onlineUsers = new ArrayList<>();
        onlineSupports = new ArrayList<>();
    }

    public void addOnlineUser(String username) {
        onlineUsers.add(username);
    }

    public void addOnlineSupport(String username) {
        onlineUsers.add(username);
        onlineSupports.add(username);
    }

    public void removeOnlineUser(String username) {
        onlineUsers.remove(username);
    }

    public void removeOnlineSupport(String username) {
        onlineUsers.remove(username);
        onlineSupports.remove(username);
    }

    public List<String> getOnlineUsers() {
        return onlineUsers;
    }

    public List<String> getOnlineSupports() {
        return onlineSupports;
    }
}
