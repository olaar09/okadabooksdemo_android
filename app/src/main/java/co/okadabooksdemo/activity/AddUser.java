package co.okadabooksdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import co.okadabooksdemo.R;
import co.okadabooksdemo.classes.CommonTasks;

public class AddUser extends AppCompatActivity {

    private Toolbar toolbar;
    private Button addBtn;
    private ProgressBar loading;
    private LinearLayout err1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add user");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setTitleTextColor(getResources().getColor(R.color.dark));
        setSupportActionBar(toolbar);

        loading = (ProgressBar) findViewById(R.id.addingUser);
        addBtn = (Button) findViewById(R.id.addUsrBtn);

        err1 = (LinearLayout) findViewById(R.id.errlayput);
        onClicks();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onClicks() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                EditText fNameField = (EditText) findViewById(R.id.add_first_name);
                String fName = fNameField.getText().toString();
                EditText lNameField = (EditText) findViewById(R.id.add_last_name);
                String lName = lNameField.getText().toString();
                CommonTasks tasks = new CommonTasks(AddUser.this);
                tasks.addItem(AddUser.this, loading, err1, "User added", "Could not add user", fName, lName, 2);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
