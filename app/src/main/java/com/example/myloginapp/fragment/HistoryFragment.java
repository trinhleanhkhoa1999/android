package com.example.myloginapp.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myloginapp.Chanel;
import com.example.myloginapp.ChanelAdapter;
import com.example.myloginapp.R;
import com.example.myloginapp.CartoonAdapter;
import com.example.myloginapp.database.ChanelDatabase;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HistoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    ChanelAdapter chanelAdapter;
    RecyclerView recyclerView;
    private List<Chanel> mChanelsList;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshLayout = view.findViewById(R.id.swiperReflayout);

        mChanelsList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rcv_user);
//        getActivity().findViewById(R.id.rcv_user);
        chanelAdapter = new ChanelAdapter(requireContext(),mChanelsList,0);
        recyclerView.setAdapter(chanelAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        getListChanel();
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        swipeRefreshLayout.setOnRefreshListener(this);

        chanelAdapter.addView(getListChanel());

//        ChanelAdapter.
//        recyclerView.setAdapter(chanelAdapter);
    }
    private List<Chanel> getListChanel() {
        mChanelsList.clear();
        mChanelsList = ChanelDatabase.getInstance(getContext()).chanelDao().getListChanel();
        return mChanelsList;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onRefresh() {
        chanelAdapter.clear();
        chanelAdapter.addView(getListChanel());
        Handler handler  = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        },3000);
    }
}
