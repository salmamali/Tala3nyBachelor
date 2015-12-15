package eg.edu.guc.tala3nybachelor.model;

/**
 * Created by salmaali on 12/15/15.
 */
public class SetData {

    public String body;
    public Integer likes;
    public String text;
    public Integer post_id;
    public Integer user_id;
    public Integer postId;
    public Integer destination;

    public SetData(String text, Integer postId, Integer post_id, Integer user_id, String body, Integer dest) {
        this.text = text;
        this.postId = postId;
        this.post_id = post_id;
        this.user_id = user_id;
        this.body = body;
        this.destination = dest;
    }
}
