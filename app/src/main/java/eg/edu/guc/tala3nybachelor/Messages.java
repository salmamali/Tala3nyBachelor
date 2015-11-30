package eg.edu.guc.tala3nybachelor;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import eg.edu.guc.tala3nybachelor.adapter.MessagesAdapter;
import eg.edu.guc.tala3nybachelor.model.Message;
import eg.edu.guc.tala3nybachelor.model.User;

public class Messages extends FullScreenActivity {
    @Bind(R.id.messages_recycler_view)
    RecyclerView messagesRecycler;

    private MessagesAdapter messagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        ButterKnife.bind(this);

        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        messagesRecycler.setLayoutManager(lm);

        //static data
        ArrayList<Message> dMessages = new ArrayList<>();
        User u1 = new User("Tarek", "Elbieh");
        Message m1 = new Message("Tarek: Msh fahem", 4, u1);
        User u2 = new User("Salma", "elTarzi");
        Message m2 = new Message("You: Hello", 20, u2);
        User u3 = new User("Menna", "Darwish");
        Message m3 = new Message("Menna: Ok", 52, u3);
        dMessages.add(m1);
        dMessages.add(m2);
        dMessages.add(m3);

        messagesAdapter = new MessagesAdapter(this, dMessages);
        messagesRecycler.setAdapter(messagesAdapter);


    }

}
