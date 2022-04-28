package com.example.myloginapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;


public class ChanelAdapter extends RecyclerView.Adapter<ChanelAdapter.ChanelViewHolder> implements Filterable {

    private Context mContext;
    private LinkedList<Chanel> mChanelsList;
    private LinkedList<Chanel> getmChanelsList;

    public ChanelAdapter(Context mContext, LinkedList<Chanel> mChanelsList){
        this.mChanelsList = mChanelsList;
        this.mContext = mContext;
        this.getmChanelsList = mChanelsList;
    }
    @NonNull
    @Override
    public ChanelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View chanels = inflater.inflate(R.layout.item_chanel, parent, false);
        ChanelViewHolder viewHolder = new ChanelViewHolder(chanels);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChanelViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Chanel chanel = mChanelsList.get(position);
        Picasso.get().load(mChanelsList.get(position).getImage()).into(holder.chanelView);
        holder.chanelTextView1.setText(chanel.getTitle());
        holder.chanelTextView2.setText(chanel.getCobtent());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(),Detail.class);
                i.putExtra("chanel",mChanelsList.get(position));
                view.getContext().startActivity(i);

                System.out.println("linear click>>>>>>>>>>>>");

            }
        });
    }

    @Override
    public int getItemCount() {
        if (mChanelsList != null){
            return mChanelsList.size();
        }
        return 0;

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String search = charSequence.toString();
                if (search.isEmpty()){
                    mChanelsList=getmChanelsList;

                }else {
                    LinkedList<Chanel> list = new LinkedList<>();
                    for (Chanel chanel: getmChanelsList  ){
                        if (chanel.getTitle().toLowerCase().contains(search.toLowerCase())){
                            list.add(chanel);
                        }
                    }
                    mChanelsList = list;

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mChanelsList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mChanelsList = (LinkedList<Chanel>) filterResults.values;
            }
        };
    }

    public class ChanelViewHolder extends RecyclerView.ViewHolder {
        private ImageView chanelView;
        private TextView chanelTextView1;
        private TextView chanelTextView2;

        public ChanelViewHolder(@NonNull View itemView) {
            super(itemView);

            chanelView= itemView.findViewById(R.id.imvChanel);
            chanelTextView1 = itemView.findViewById((R.id.txvChanel));
            chanelTextView2 = itemView.findViewById((R.id.txvChanel2));
        }
    }
    public void addView(LinkedList<Chanel> items){
        mChanelsList.addAll(items);
        notifyDataSetChanged();
    }

}
