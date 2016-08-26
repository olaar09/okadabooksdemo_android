package co.okadabooksdemo.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import co.okadabooksdemo.R;
import co.okadabooksdemo.activity.AddUser;
import co.okadabooksdemo.adapter.BookListAdapter;
import co.okadabooksdemo.adapter.decorator.ItemDecorator;
import co.okadabooksdemo.classes.CommonTasks;
import co.okadabooksdemo.object.User;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Users#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Users extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<User> users;
    private RecyclerView recyclerView;
    private BookListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public FloatingActionButton fab;
    private LinearLayout errLayout;
    private Button errBtn;
    private ProgressBar loading;
    private CommonTasks tasks;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Users.
     */
    // TODO: Rename and change types and number of parameters
    public static Users newInstance(String param1, String param2) {
        Users fragment = new Users();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Users() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        errLayout = (LinearLayout) view.findViewById(R.id.userserr);
        errBtn = (Button) view.findViewById(R.id.errBtn);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        loading = (ProgressBar) view.findViewById(R.id.usersloading);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddUser.class);
                startActivityForResult(intent, 200);
            }
        });

        tasks = new CommonTasks(getActivity());
        tasks.toggleLoading(loading, errLayout, true);

        setUpXcycler(view);
        getUsers();
        onClicks();
        return view;
    }

    public void onClicks() {
        errBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUsers();
            }
        });
    }

    public void setUpXcycler(View v) {
        users = new ArrayList<>();
        recyclerView = (RecyclerView) v.findViewById(R.id.user_recycler);
        adapter = new BookListAdapter(null, users, null, this);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        // set decorator
        recyclerView.addItemDecoration(new ItemDecorator(getContext(), LinearLayout.VERTICAL));
        // setAnimator
        recyclerView.setItemAnimator(new SlideInLeftAnimator());

        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getUsers();
    }

    public void getUsers() {
        tasks.getItems(loading, errLayout, users, new CommonTasks.GetItemCallBack() {
            @Override
            public void callBack(List itemsList, JSONArray jsonArr) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<User>>() {
                }.getType();
                if (itemsList != null) {
                    itemsList.clear();
                }
                itemsList.addAll((List<User>) gson.fromJson(jsonArr.toString(), listType));
                Log.d("TAG", itemsList.toString());
                adapter.notifyDataSetChanged();
            }
        }, 2);
    }

    public void deleteUser(String id, int pos) {
        tasks.deleteItem(id, "deleting user", "user deleted", "could not complete request", pos, users, adapter, 2);
    }
}
