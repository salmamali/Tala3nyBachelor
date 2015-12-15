package eg.edu.guc.tala3nybachelor.model;

/**
 * Created by salmaali on 12/3/15.
 */
public class Friend {

    private String friend;
    private boolean accepted;
    private String imageUrl;


    public Friend(String friend) {
        this.accepted = false;
        this.friend = friend;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public String getFriend() {
        return friend;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
