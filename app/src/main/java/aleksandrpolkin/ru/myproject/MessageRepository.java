package aleksandrpolkin.ru.myproject;

import android.support.annotation.NonNull;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class MessageRepository {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("messages");


    public void loadMessages(@NonNull final MessageLoadListener messageLoadListener){
      /*  myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messageLoadListener.onMessagesReceived(toMessageList(dataSnapshot));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                messageLoadListener.onError(databaseError.toException());
            }
        });*/
            Query query = myRef.orderByChild("createdAt/time");
            query.addChildEventListener(new ChildEventListener() {
                @Override
           public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               messageLoadListener.onMessagesReceived(toMessageList(dataSnapshot).get(0));
           }

           @Override
           public void onChildChanged(DataSnapshot dataSnapshot, String s) {

           }

           @Override
           public void onChildRemoved(DataSnapshot dataSnapshot) {

           }

           @Override
           public void onChildMoved(DataSnapshot dataSnapshot, String s) {

           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
    }
    public interface MessageLoadListener{
        void onMessagesReceived(Message messages);
        void onError(Throwable error);
    }

    private List<Message> toMessageList(DataSnapshot dataSnapshot){
        List<Message> messages = new ArrayList<>();

        Message message = dataSnapshot.getValue(Message.class);
        messages.add(message);
        /*
        for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
            Message msg = new Message();

            msg.setId((String) snapshot.child("id").getValue());
            HashMap s =(HashMap) snapshot.child("createdAt").getValue();
            Date date = new Date((long) s.get                                                                                                                                                                                                                                                                                                                                                                               ("time"));

            msg.setCreatedAt(date);

            msg.setText((String) snapshot.child("text").getValue());
            Author user = new Author();
            user = snapshot.child("user").getValue(Author.class);
            msg.setUser(user);

            messages.add(msg);

        }*/

        return messages;
    }



    public void addMessage(String text,String email, String uid){
        String id = UUID.randomUUID().toString();

            myRef.child(id).setValue(new Message(id, text, Calendar.getInstance().getTime(),
                    new Author(uid, email, "")));

}
}
