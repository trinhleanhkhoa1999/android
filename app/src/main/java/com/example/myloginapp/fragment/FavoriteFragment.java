package com.example.myloginapp.fragment;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import android.content.SharedPreferences;
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
import com.example.myloginapp.api.ApiService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteFragment extends Fragment {


    ChanelAdapter chanelAdapter;
    RecyclerView recyclerView;
    private List<Chanel> mChanelsList;
    private SwipeRefreshLayout swipeRefreshLayout;
    String token;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mChanelsList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rcv_user);
//        getActivity().findViewById(R.id.rcv_user);
        chanelAdapter = new ChanelAdapter(requireContext(),mChanelsList,3);
        recyclerView.setAdapter(chanelAdapter);
        mChanelsList.addAll(getItemChanel());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

    }


    private List<Chanel> getItemChanel(){
        mChanelsList.clear();
        SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("token", getActivity().getApplicationContext().MODE_PRIVATE);
        token = sharedPreferences.getString("token","");
        Call<ResponseBody> chanelCall = ApiService.apiService.GetFavorite2(token);
        chanelCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        String jsonData = response.body().string();
                        JSONObject json = new JSONObject(jsonData);
                        JSONObject json2 = json.getJSONObject("data");
                        JSONArray array = json2.getJSONArray("favorites");
                        Type listType = new TypeToken<ArrayList<Chanel>>(){}.getType();
                        List<Chanel> list = new Gson().fromJson(String.valueOf(array),listType);
                        for(Chanel chanel: list)
                        {
                            mChanelsList.add(chanel);
                            chanelAdapter.notifyDataSetChanged();
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
        return mChanelsList;
    }


    private void getListChanel(){
        ApiService.apiService.GetChanels().enqueue(new Callback<List<Chanel>>() {
            @Override
            public void onResponse(Call<List<Chanel>> call, Response<List<Chanel>> response) {
                mChanelsList.clear();
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

    @Override
    public void onResume() {
        mChanelsList.clear();
        mChanelsList.addAll(getItemChanel());
        super.onResume();
    }
}
