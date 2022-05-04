package com.example.myloginapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myloginapp.api.ApiService;
import com.example.myloginapp.database.ChanelDatabase;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChanelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private Context mContext;
    private List<Chanel> mChanelsList;
    private List<Chanel> getmChanelsList;
    public  String token;
    Dialog myDialog;
    SharedPreferences sharedPreferences;


    public ChanelAdapter(Context mContext, List<Chanel> mChanelsList, int type_layout){
        this.mChanelsList = mChanelsList;
        this.mContext = mContext;
        this.getmChanelsList = mChanelsList;
        TYPE_LAYOUT = type_layout;
    }

    private int TYPE_LAYOUT ;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(mContext);
//        View chanels = inflater.inflate(R.layout.item_chanel, parent, false);
//        ChanelViewHolder viewHolder = new ChanelViewHolder(chanels);
//        return  viewHolder;
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        if(TYPE_LAYOUT == 1){
            View view = inflater.inflate(R.layout.item_chanel, parent, false);
            return new ChanelViewHolder(view);
        }else if(TYPE_LAYOUT==0){
            View view = inflater.inflate(R.layout.history_chanel, parent, false);
            return new HistoryChanelViewHolder(view);
        }else {
            View view = inflater.inflate(R.layout.item_favorite, parent, false);
            return new FavoriteChanelViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Chanel chanel = mChanelsList.get(position);
        if (TYPE_LAYOUT ==1 ){
            ChanelViewHolder chanelViewHolder = (ChanelViewHolder) holder;
            Picasso.get().load(mChanelsList.get(position).getImage()).into(chanelViewHolder.chanelView);
            chanelViewHolder.chanelTextView1.setText(chanel.getTitle());
            chanelViewHolder.chanelTextView2.setText(chanel.getContent());
        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.dialog_channel);

        chanelViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mChanelsList.get(position).isStatus()){
                    CheckLogin(mChanelsList.get(position).getId().toString(),view);
                }else {
                    ChanelDatabase.getInstance(view.getContext()).chanelDao().InsertChanel(chanel);
                    Intent i = new Intent(view.getContext(),Detail.class);
                    i.putExtra("chanel",mChanelsList.get(position).getUrl());
                    i.putExtra("chaneltitle",mChanelsList.get(position).getTitle());
                    i.putExtra("chanelcontent",mChanelsList.get(position).getContent());
                    view.getContext().startActivity(i);
                }
            }
        });
        }else if( TYPE_LAYOUT == 0){
            myDialog = new Dialog(mContext);
            myDialog.setContentView(R.layout.delete_dialog);
            HistoryChanelViewHolder historyChanelViewHolder = (HistoryChanelViewHolder) holder;
            Picasso.get().load(mChanelsList.get(position).getImage()).into(historyChanelViewHolder.chanelView);
//            historyChanelViewHolder.chanelTextView1.setText(chanel.getTitle());
            historyChanelViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    deleteDiaLog(chanel);
                    return false;
                }
            });
            historyChanelViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mChanelsList.get(position).isStatus()){
                        CheckLogin(mChanelsList.get(position).getId().toString(),view);
//                    openDiaLog();
                    }else {
                        ChanelDatabase.getInstance(view.getContext()).chanelDao().InsertChanel(chanel);
                        ChanelDatabase.getInstance(view.getContext()).chanelDao().getListChanel();
                        Intent i = new Intent(view.getContext(),Detail.class);
                        i.putExtra("chanel",mChanelsList.get(position).getUrl());
                        i.putExtra("chaneltitle",mChanelsList.get(position).getTitle());
                        i.putExtra("chanelcontent",mChanelsList.get(position).getContent());
                        Loaddata();
                        view.getContext().startActivity(i);
                    }
                }
            });
        } else {
            FavoriteChanelViewHolder favoriteChanelViewHolder  = (FavoriteChanelViewHolder) holder;
            Picasso.get().load(mChanelsList.get(position).getImage()).into(favoriteChanelViewHolder.chanelView);
            favoriteChanelViewHolder.chanelName.setText(chanel.getTitle());
            favoriteChanelViewHolder.chanelTextRate.setText(String.valueOf(chanel.getRate()));
            favoriteChanelViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mChanelsList.get(position).isStatus()){
                        CheckLogin(mChanelsList.get(position).getId().toString(),view);
                    }else {
                        ChanelDatabase.getInstance(view.getContext()).chanelDao().InsertChanel(chanel);
                        ChanelDatabase.getInstance(view.getContext()).chanelDao().getListChanel();
                        Intent i = new Intent(view.getContext(),Detail.class);
                        i.putExtra("chanel",mChanelsList.get(position).getUrl());
                        i.putExtra("chaneltitle",mChanelsList.get(position).getTitle());
                        i.putExtra("chanelcontent",mChanelsList.get(position).getContent());
                        view.getContext().startActivity(i);
                    }
                }
            });
        }
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
                    List<Chanel> list = new ArrayList<>();
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
                mChanelsList = (List<Chanel>) filterResults.values;
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

    public class HistoryChanelViewHolder extends RecyclerView.ViewHolder {
        private ImageView chanelView;
//        private TextView chanelTextView1;
        public HistoryChanelViewHolder(@NonNull View itemView) {
            super(itemView);

            chanelView= itemView.findViewById(R.id.img_chanel);
//            chanelTextView1 = itemView.findViewById((R.id.tv_title));
        }
    }

    public class FavoriteChanelViewHolder extends RecyclerView.ViewHolder {
        private ImageView chanelView;
        private TextView chanelTextRate;
        private TextView chanelName;
        public FavoriteChanelViewHolder(@NonNull View itemView) {
            super(itemView);

            chanelView= itemView.findViewById(R.id.ivImage);
            chanelTextRate = itemView.findViewById((R.id.tvRate));
            chanelName = itemView.findViewById(R.id.tvName);
        }
    }

    public void Loaddata(){
        mChanelsList = ChanelDatabase.getInstance(mContext).chanelDao().getListChanel();
        notifyDataSetChanged();
    }

    public void addView(List<Chanel> items){
        mChanelsList.addAll(items);
        notifyDataSetChanged();
    }

    public void clear() {
        int size = mChanelsList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                mChanelsList.remove(0);
            }
            notifyItemRangeRemoved(0, size);
        }
    }

    public void CheckLogin(String s, View view){
        sharedPreferences = mContext.getSharedPreferences("token", mContext.MODE_PRIVATE);
        token = sharedPreferences.getString("token","");
        Chanel chanel = new Chanel();
        Call<ResponseBody> chanelCall =   ApiService.apiService.getChanelswithId(s,token);

        chanelCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()){
                        String chanel1 = response.body().string();
                        if (chanel1 != null){
                            JSONObject json = new JSONObject(chanel1);
                            JSONObject k = json.getJSONObject("chanel");
                            Intent i = new Intent(view.getContext(),Detail.class);
                            i.putExtra("chanel",k.getString("url"));
                            view.getContext().startActivity(i);
                        }
                    }
                    else {
                        openDiaLog();
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }
//    Intent intent = new Intent(view.getContext(),Detail.class);
//                    intent.putExtra("chanel",response.body().getUrl());
//                    view.getContext().startActivity(intent);

    private  void  openDiaLog(){
        myDialog.show();
        Button btnNo = myDialog.findViewById(R.id.btn_no);
        Button btnLogin = myDialog.findViewById(R.id.btn_login);

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),SignIn.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    private  void  deleteDiaLog(Chanel chanel){
        myDialog.show();
        Button btnNo = myDialog.findViewById(R.id.btn_no);
        Button btnLogin = myDialog.findViewById(R.id.btn_delete);

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChanelDatabase.getInstance(view.getContext()).chanelDao().Delete(chanel);
                Loaddata();
                myDialog.dismiss();
            }
        });
    }

//    private  void  openDialog(int gravity, Context context){
//        final Dialog dialog = new Dialog(mContext);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_channel);
//
//        Window window = dialog.getWindow();
//        if(window == null){
//            return;
//        }
//        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
//        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        WindowManager.LayoutParams windowattribute = window.getAttributes();
//        windowattribute.gravity = gravity;
//        window.setAttributes(windowattribute);
//
//        if (Gravity.BOTTOM == gravity){
//        dialog.setCancelable(true);
//        }else {
//            dialog.setCancelable(false);
//        }
//        Button btnNo = dialog.findViewById(R.id.btn_no);
//        Button btnLogin = dialog.findViewById(R.id.btn_login);
//
//        btnNo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(view.getContext(),SignIn.class);
//                view.getContext().startActivity(intent);
//            }
//        });
//    }


    //    @Override
//    public void onBindViewHolder(@NonNull ChanelViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        Chanel chanel = mChanelsList.get(position);
//        Picasso.get().load(mChanelsList.get(position).getImage()).into(holder.chanelView);
//        holder.chanelTextView1.setText(chanel.getTitle());
//        if (chanel.isStatus()){
//            holder.chanelTextView2.setText("must register");
//        }else {
//            holder.chanelTextView2.setText("free");
//        }
//
//        myDialog = new Dialog(mContext);
//        myDialog.setContentView(R.layout.dialog_channel);
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mChanelsList.get(position).isStatus()){
//                    CheckLogin(mChanelsList.get(position).getId().toString(),view);
////                    openDiaLog();
//                }else {
//                    ChanelDatabase.getInstance(view.getContext()).chanelDao().InsertChanel(chanel);
//
//                    ChanelDatabase.getInstance(view.getContext()).chanelDao().getListChanel();
//                    Intent i = new Intent(view.getContext(),Detail.class);
//                    i.putExtra("chanel",mChanelsList.get(position).getUrl());
//                    view.getContext().startActivity(i);
//                }
//            }
//        });
//    }

}
