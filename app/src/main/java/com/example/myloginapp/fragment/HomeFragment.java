package com.example.myloginapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myloginapp.Chanel;
import com.example.myloginapp.ChanelAdapter;
import com.example.myloginapp.R;
import com.example.myloginapp.api.ApiService;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment<onViewCreated> extends Fragment {
//
//    private RecyclerView rcvUser ;
//    private RecyclerView rcvUser1 ;
//
//    private UserAdapter mUserAdapter;

    ChanelAdapter chanelAdapter;
    RecyclerView recyclerView;
    private LinkedList<Chanel> mChanelsList;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        rcvUser = view.findViewById(R.id.rcv_user);
//        rcvUser1 = view.findViewById(R.id.rcv_user1);
//
////        getActivity().findViewById(R.id.rcv_user);
//        mUserAdapter = new UserAdapter(requireContext());
//
//        LinearLayoutManager gridLayoutManager = new LinearLayoutManager (requireContext(), LinearLayoutManager.HORIZONTAL, false);
//        rcvUser.setLayoutManager(gridLayoutManager );
//        LinearLayoutManager gridLayoutManager1 = new LinearLayoutManager (requireContext(), LinearLayoutManager.HORIZONTAL, false);
//        rcvUser1.setLayoutManager(gridLayoutManager1 );
//
//        mUserAdapter.setData(getListUser());
//        rcvUser.setAdapter(mUserAdapter);
//
//
//        rcvUser1.setAdapter(mUserAdapter);

        mChanelsList  = new LinkedList<>();
        recyclerView = view.findViewById(R.id.recycleChanel);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(),1));



        chanelAdapter = new ChanelAdapter(requireContext(),mChanelsList);
        recyclerView.setAdapter(chanelAdapter);

        fetchChanels();
    }


//    private List<User> getListUser() {
//        List<User> list = new ArrayList<>();
//        list.add(new User(R.drawable.fim1, "User Name 1"));
//        list.add(new User(R.drawable.fim2, "User Name 2"));
//        list.add(new User(R.drawable.fim3, "User Name 3"));
//        list.add(new User(R.drawable.fim4, "User Name 4"));
//
//        list.add(new User(R.drawable.fim1, "User Name 1"));
//        list.add(new User(R.drawable.fim2, "User Name 2"));
//        list.add(new User(R.drawable.fim3, "User Name 3"));
//        list.add(new User(R.drawable.fim4, "User Name 4"));
//
//        list.add(new User(R.drawable.fim1, "User Name 1"));
//        list.add(new User(R.drawable.fim2, "User Name 2"));
//        list.add(new User(R.drawable.fim3, "User Name 3"));
//        list.add(new User(R.drawable.fim4, "User Name 4"));
//
//        return list;
//    }
//    private List<User> getListUser1() {
//        List<User> list1 = new ArrayList<>();
//        list1.add(new User(R.drawable.fb, "User Name 1"));
//        list1.add(new User(R.drawable.ig, "User Name 2"));
//        list1.add(new User(R.drawable.fim3, "User Name 3"));
//        list1.add(new User(R.drawable.fim4, "User Name 4"));
//
//        return list1;
//    }
    private void fetchChanels(){
        ApiService.apiService.GetChanels().enqueue(new Callback<List<Chanel>>() {
            @Override
            public void onResponse(Call<List<Chanel>> call, Response<List<Chanel>> response) {
                Toast.makeText(getActivity(),"LOad",Toast.LENGTH_SHORT).show();

                if(response.isSuccessful() && response.body() !=null ){

                    Toast.makeText(getActivity(),"LOad 2",Toast.LENGTH_SHORT).show();
                    mChanelsList.addAll(response.body());
                    Toast.makeText(getActivity(),"load 3",response.code()).show();
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home2, container, false);
    }
}