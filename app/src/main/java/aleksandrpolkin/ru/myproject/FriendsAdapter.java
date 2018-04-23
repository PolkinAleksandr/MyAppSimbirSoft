package aleksandrpolkin.ru.myproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import com.squareup.picasso.Picasso;

import com.squareup.picasso.Picasso;

import java.util.List;

public class FriendsAdapter extends
        RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder> {

    private List<Friend> friends;

 public FriendsAdapter(List<Friend> friends){
    this.friends=friends;
    notifyDataSetChanged();
}

    //public FriendsAdapter(){}

@Override
    public FriendsViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
    View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.friend_item,parent,false);
    return new FriendsViewHolder(view);
}


@Override
    public void onBindViewHolder(FriendsViewHolder holder, int position){
    holder.setFriend(friends.get(position));
}

@Override
    public int getItemCount(){
    return friends.size();
}

    public class FriendsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView name;
    private TextView message;
    private TextView date;
    private TextView messages_badge;
    private ImageView avatar;

    FriendsViewHolder(View itemView){
        super(itemView);
        name = itemView.findViewById(R.id.name);
        message = itemView.findViewById(R.id.message);
        date = itemView.findViewById(R.id.date);
        messages_badge= itemView.findViewById(R.id.messages_badge);
        avatar = itemView.findViewById(R.id.avatar);
        itemView.setOnClickListener(this);
    }

    void setFriend(Friend friend){
        name.setText(friend.getName());
        message.setText(friend.getMessage());
        date.setText(friend.getDate());
        messages_badge.setText(friend.getMessage_badge().toString());

        Picasso.get().load(friend.getAvatar()).into(avatar);

    }

        @Override
        public void onClick(View v) {
            final Context context = v.getContext();
           Intent intent = new Intent(context,ChatKitActivity.class);
           context.startActivity(intent);
        }
    }
}
