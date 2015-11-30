package eg.edu.guc.tala3nybachelor.model;

/**
 * Created by salmaali on 11/28/15.
 */
public class Message {

    public Message(String text, int timestamp, User user) {
        this.text = text;
        this.timestamp = timestamp;
        this.user = user;
    }

    private int timestamp;
    private User user;
    private String text;

    public int getTimestamp() {
        return timestamp;
    }

    public String getText() {
        return text;
    }

    public User getUser() {
        return user;
    }
}
