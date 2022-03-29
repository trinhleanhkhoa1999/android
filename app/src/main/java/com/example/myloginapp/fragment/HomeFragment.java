package com.example.myloginapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myloginapp.R;
import com.example.myloginapp.User;
import com.example.myloginapp.UserAdapter;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment<onViewCreated> extends Fragment {

    private RecyclerView rcvUser ;
    private RecyclerView rcvUser1 ;

    private UserAdapter mUserAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcvUser = view.findViewById(R.id.rcv_user);
        rcvUser1 = view.findViewById(R.id.rcv_user1);

//        getActivity().findViewById(R.id.rcv_user);
        mUserAdapter = new UserAdapter(requireContext());

        LinearLayoutManager gridLayoutManager = new LinearLayoutManager (requireContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvUser.setLayoutManager(gridLayoutManager );
        LinearLayoutManager gridLayoutManager1 = new LinearLayoutManager (requireContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvUser1.setLayoutManager(gridLayoutManager1 );

        mUserAdapter.setData(getListUser());
        rcvUser.setAdapter(mUserAdapter);


        rcvUser1.setAdapter(mUserAdapter);


    }

    private List<User> getListUser() {
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
//    private List<User> getListUser1() {
//        List<User> list1 = new ArrayList<>();
//        list1.add(new User(R.drawable.fb, "User Name 1"));
//        list1.add(new User(R.drawable.ig, "User Name 2"));
//        list1.add(new User(R.drawable.fim3, "User Name 3"));
//        list1.add(new User(R.drawable.fim4, "User Name 4"));
//
//        return list1;
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home2, container, false);
    }
}