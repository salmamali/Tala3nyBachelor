package eg.edu.guc.tala3nybachelor.model;

/**
 * Created by TarekElBeih on 02/12/15.
 */

public class Comment {
    private String sender;
    private String text;
    private String created_at;
    private String updated_at;
    private int id;
    private Integer post_id;
    private Integer user_id;


    public Comment(String sender, String body, int timeStamp) {
        this.sender = sender;
        //this.body = body;
        //this.timeStamp = timeStamp;
    }

    public String getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }


}
