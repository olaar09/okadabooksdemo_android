package co.okadabooksdemo.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import co.okadabooksdemo.R;
import co.okadabooksdemo.fragment.Books;
import co.okadabooksdemo.fragment.Users;
import co.okadabooksdemo.object.Book;
import co.okadabooksdemo.object.User;

/**
 * Created by olaar on 8/23/16.
 */
public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    private List<User> users;
    private Users frag;


    public UserListAdapter(List<User> courseLesson, Users frag) {
        this.users = courseLesson;
        this.frag = frag;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView userFirstName, userLasName;
        public ImageButton delUser;
        public CardView row;

        public ViewHolder(View v) {
            super(v);

            userFirstName = (TextView) v.findViewById(R.id.data1);
            userLasName = (TextView) v.findViewById(R.id.data2);
            delUser = (ImageButton) v.findViewById(R.id.del);
            row = (CardView) v.findViewById(R.id.row);


        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int pos) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reclist, parent, false);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int pos) {
        final User currentUser= users.get(pos);
        vh.userFirstName.setText(currentUser.getuser_firstname());
        vh.userLasName.setText(currentUser.getuser_lastname());
    }


    public int getItemCount() {
        if (users != null && users.size() > 0) {
            return users.size();
        } else {
            return 0;
        }

    }
}
