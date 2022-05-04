package com.example.myloginapp.fragment;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myloginapp.Chanel;
import com.example.myloginapp.ChanelAdapter;
import com.example.myloginapp.R;
import com.example.myloginapp.Cartoon;
import com.example.myloginapp.CartoonAdapter;
import com.example.myloginapp.api.ApiService;
import com.example.myloginapp.database.ChanelDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteFragment extends Fragment {


    ChanelAdapter chanelAdapter;
    RecyclerView recyclerView;
    private List<Chanel> mChanelsList;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mChanelsList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rcv_user);
//        getActivity().findViewById(R.id.rcv_user);
        chanelAdapter = new ChanelAdapter(requireContext(),mChanelsList,3);
        recyclerView.setAdapter(chanelAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        fetchChanels();
    }

    private void fetchChanels(){
        ApiService.apiService.GetFavorite().enqueue(new Callback<List<Chanel>>() {
            @Override
            public void onResponse(Call<List<Chanel>> call, Response<List<Chanel>> response) {

                if(response.isSuccessful() && response.body() !=null ){

                    Toast.makeText(getActivity(),"Loading",Toast.LENGTH_SHORT).show();
                    mChanelsList.addAll(response.body());
                    chanelAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(chanelAdapter);
                }
            }
            @Override
            public void onFailure(Call<List<Chanel>> call, Throwable t) {

                Toast.makeText(getActivity(),"Not Load",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }
}
