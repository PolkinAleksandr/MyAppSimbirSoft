package aleksandrpolkin.ru.myproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

public class ChatKitActivity extends AppCompatActivity {

    private Profile profile;
     private MessagesList messagesList;
     private MessageInput messageInput;
     private MessagesListAdapter<Message> adapter;
     private MessageRepository repository;
     private FirebaseDatabase database = FirebaseDatabase.getInstance();
     private DatabaseReference mDatabase = database.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_kit);

        messagesList = (MessagesList) findViewById(R.id.messageList);
        messageInput = (MessageInput) findViewById(R.id.input);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        adapter = new MessagesListAdapter<>(user.getUid(),null);
        messagesList.setAdapter(adapter);


        repository = new MessageRepository();
        //Query query = database.getReference("messages").orderByChild("createdAt/time");
        repository.loadMessages(new MessageRepository.MessageLoadListener() {
                                    @Override
                                    public void onMessagesReceived(Message messages) {
                                        adapter.addToStart(messages, true);
                                    }

                                    @Override
                                    public void onError(Throwable error) {

                                    }
                                });

         messageInput.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
              //  loadProfile();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                repository.addMessage(input.toString(),user.getEmail(),user.getUid());
                return true;
            }
        });
    }


    public void loadProfile(){
        profile = new Profile();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        mDatabase.child("profile").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                profile = dataSnapshot.getValue(Profile.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
