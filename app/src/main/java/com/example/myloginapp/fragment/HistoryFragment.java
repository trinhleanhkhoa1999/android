package com.example.myloginapp.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myloginapp.R;
import com.example.myloginapp.Cartoon;
import com.example.myloginapp.CartoonAdapter;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {
    private RecyclerView rcvUser ;
    private CartoonAdapter mUserAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcvUser = view.findViewById(R.id.rcv_user);
//        getActivity().findViewById(R.id.rcv_user);
        mUserAdapter = new CartoonAdapter(requireContext());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 3);
        rcvUser.setLayoutManager(gridLayoutManager);

        mUserAdapter.setData(getListUser());
        rcvUser.setAdapter(mUserAdapter);
    }

    private List<Cartoon> getListUser() {
        List<Cartoon> list = new ArrayList<>();
        list.add(new Cartoon(R.drawable.fim1, "User Name 1"));
        list.add(new Cartoon(R.drawable.fim2, "User Name 2"));
        list.add(new Cartoon(R.drawable.fim3, "User Name 3"));
        list.add(new Cartoon(R.drawable.fim4, "User Name 4"));

        list.add(new Cartoon(R.drawable.fim1, "User Name 1"));
        list.add(new Cartoon(R.drawable.fim2, "User Name 2"));
        list.add(new Cartoon(R.drawable.fim3, "User Name 3"));
        list.add(new Cartoon(R.drawable.fim4, "User Name 4"));

        list.add(new Cartoon(R.drawable.fim1, "User Name 1"));
        list.add(new Cartoon(R.drawable.fim2, "User Name 2"));
        list.add(new Cartoon(R.drawable.fim3, "User Name 3"));
        list.add(new Cartoon(R.drawable.fim4, "User Name 4"));

        return list;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }
}
