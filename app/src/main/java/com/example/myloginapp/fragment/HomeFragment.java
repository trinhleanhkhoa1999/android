package com.example.myloginapp.fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myloginapp.R;
import com.example.myloginapp.User;
import com.example.myloginapp.UserAdapter;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {


    public class Test extends AppCompatActivity {

        private RecyclerView rcvUser;
        private UserAdapter mUserAdapter;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_home);

            rcvUser = findViewById(R.id.rcv_user);
            mUserAdapter = new UserAdapter(this);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
            rcvUser.setLayoutManager(gridLayoutManager);

            mUserAdapter.setData(getListUser());
            rcvUser.setAdapter(mUserAdapter);
        }
        private List<User> getListUser(){
            List<User> list = new ArrayList<>();
            list.add(new User(R.drawable.fim1, "User Name 1"));
            list.add(new User(R.drawable.fim2, "User Name 2"));
            list.add(new User(R.drawable.fim3, "User Name 3"));
            list.add(new User(R.drawable.fim4, "User Name 4"));

            list.add(new User(R.drawable.fim1, "User Name 1"));
            list.add(new User(R.drawable.fim2, "User Name 2"));
            list.add(new User(R.drawable.fim3, "User Name 3"));
            list.add(new User(R.drawable.fim4, "User Name 4"));

            list.add(new User(R.drawable.fim1, "User Name 1"));
            list.add(new User(R.drawable.fim2, "User Name 2"));
            list.add(new User(R.drawable.fim3, "User Name 3"));
            list.add(new User(R.drawable.fim4, "User Name 4"));

            return list;
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_home, container, false);


    }
}

