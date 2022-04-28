package com.example.myloginapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartoonAdapter extends RecyclerView.Adapter<CartoonAdapter.UserViewHolder> {

    private Context mContext;
    private List<Cartoon> mListUser;

    public CartoonAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Cartoon> list){
        this.mListUser = list;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cartoon, parent, false );
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Cartoon user = mListUser.get(position);
        if (user == null) {
            return;
        }
        holder.imgUser.setImageResource(user.getResouceImg());
        holder.tvName.setText(user.getName());
    }

    @Override
    public int getItemCount() {
        if (mListUser != null){
            return mListUser.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgUser;
        private TextView tvName;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            imgUser =itemView.findViewById(R.id.img_user);
            tvName = itemView.findViewById(R.id.tv_name);

        }
    }
}
