package eg.edu.guc.tala3nybachelor.singleton;

import retrofit.RestAdapter;

/**
 * Created by salmaali on 12/15/15.
 */
public class RetrofitSingleton {

    private static RestAdapter restAdapter = null;

    private RetrofitSingleton(){

    }

    public static RestAdapter getInstance(){
        if (restAdapter == null) {
            restAdapter = new RestAdapter.Builder().setEndpoint("http://10.0.2.2:3000").build();
        }
        return restAdapter;
    }
}
