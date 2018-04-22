package aleksandrpolkin.ru.myproject;

import com.google.firebase.database.PropertyName;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.Date;

public class Message implements IMessage{

    private String id;
    private String text;
    @PropertyName("user")
    private Author user;
    private Date createdAt;


    public Message(){}

    public Message(String id, String text, Date createdAt, Author author){
        this.id=id;
        this.text=text;
        this.createdAt=createdAt;
        this.user=author;
    }

    @Override
    public String getId(){
        return id;
    }

    @Override
    public String getText(){
        return text;
    }

    @Override
    public IUser getUser(){
        return user;
    }


    public Date getCreatedAt(){
        return createdAt;
    }


    public void setCreatedAt(Date createdAt) {
      this.createdAt = createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUser(Author user) {
        this.user = user;
    }
}
