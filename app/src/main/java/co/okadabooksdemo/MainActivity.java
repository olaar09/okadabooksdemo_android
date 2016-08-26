package co.okadabooksdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import co.okadabooksdemo.fragment.Books;
import co.okadabooksdemo.fragment.Users;

public class MainActivity extends AppCompatActivity {

    private  int currFrag = 0;
   // private FragmentManager fragmentManager;
    private Books books;
    private Users users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.dark));
        toolbar.setTitle("Okada books demo");
        setSupportActionBar(toolbar);

        books = Books.newInstance("", "");
        users = Users.newInstance("","");

        chooseFrag(1, "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_book && currFrag != 1) {
            chooseFrag(1, "");
            return true;
        }

        if (id == R.id.action_user && currFrag != 2){
            chooseFrag(2, "");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void chooseFrag(int action, String extra) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        switch (action) {
            case 1:
                fragmentTransaction.replace(R.id.main_frame, books);
                currFrag = 1;
                break;
            case 2:
                fragmentTransaction.replace(R.id.main_frame, users);
                currFrag = 2;
                break;
            default:
                fragmentTransaction.replace(R.id.main_frame, books);
                currFrag = 1;
                break;
        }
        fragmentTransaction.commit();
    }

    public void setActionBar() {

    }
}
