package eg.edu.guc.tala3nybachelor.model;

/**
 * Created by TarekElBeih on 02/12/15.
 */

public class Comment {
    private String sender;
    private String body;
    private int timeStamp;

    public Comment(String sender, String body, int timeStamp) {
        this.sender = sender;
        this.body = body;
        this.timeStamp = timeStamp;
    }

    public String getSender() {
        return sender;
    }

    public String getBody() {
        return body;
    }

    public int getTimeStamp() {
        return timeStamp;
    }
}
