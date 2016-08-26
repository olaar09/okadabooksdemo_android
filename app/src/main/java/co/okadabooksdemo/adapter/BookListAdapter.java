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
public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder> {

    private List<Book> books;
    private List<User> users;
    private Books frag;
    private Users frag2;


    public BookListAdapter(List<Book> bookList, List<User> userList, Books frag, Users frag2) {
        this.books = bookList;
        this.users = userList;
        this.frag = frag;
        this.frag2 = frag2;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView item1, item2;
        public ImageButton delItem;
        public CardView row;

        public ViewHolder(View v) {
            super(v);

            item1 = (TextView) v.findViewById(R.id.data1);
            item2 = (TextView) v.findViewById(R.id.data2);
            delItem = (ImageButton) v.findViewById(R.id.del);
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
        if (users != null) {
            final User currentUser = users.get(pos);
            vh.item1.setText(currentUser.getuser_firstname());
            vh.item2.setText(currentUser.getuser_lastname());

            vh.delItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int posi = users.indexOf(currentUser);
                    frag2.deleteUser("" + currentUser.id, posi);
                }
            });
        } else {
            final Book currentbook = books.get(pos);
            vh.item1.setText(currentbook.getbook_name());
            vh.item2.setText("" + currentbook.getbook_price());

            vh.delItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int posi = books.indexOf(currentbook);
                    frag.deleteBook("" + currentbook.id, posi);
                }
            });
        }

    }


    public int getItemCount() {
        if (books != null && books.size() > 0) {
            return books.size();
        } else if (users != null && users.size() > 0) {
            return users.size();
        } else {
            return 0;
        }

    }
}
