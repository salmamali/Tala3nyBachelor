package eg.edu.guc.tala3nybachelor;

import java.util.ArrayList;

import eg.edu.guc.tala3nybachelor.controller.Controller;
import eg.edu.guc.tala3nybachelor.model.Post;
import eg.edu.guc.tala3nybachelor.model.User;
import eg.edu.guc.tala3nybachelor.singleton.RetrofitSingleton;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by salmaali on 12/15/15.
 */
public class APICalls {

    public void getPost(Integer id) {

        Controller.getPost retr = RetrofitSingleton.getInstance().create(Controller.getPost.class);
        retr.get_post(id, new Callback<Post>() {
            @Override
            public void success(Post post, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }


    public void getPosts() {
        Controller.getPosts retr = RetrofitSingleton.getInstance().create(Controller.getPosts.class);
        retr.get_posts(new Callback<ArrayList<Post>>() {
            @Override
            public void success(ArrayList<Post> posts, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }


}
