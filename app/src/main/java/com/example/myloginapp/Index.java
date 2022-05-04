package com.example.myloginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Index extends AppCompatActivity {

    private TabLayout mTablayout;
    private Context mContext;
    private ViewPager2 mViewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    Toolbar toolbar;
    SearchView searchView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String TOKEN = "token";
    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        mTablayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.dialog_logout);

        sharedPreferences = this.getSharedPreferences(TOKEN,0);

        myViewPagerAdapter = new MyViewPagerAdapter(this);
        mViewPager.setAdapter(myViewPagerAdapter);

        new TabLayoutMediator(mTablayout, mViewPager, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setText("Home");
                    break;
                case 1:
                    tab.setText("Favorite");
                    break;
                case 2:
                    tab.setText("History");
                    break;
            }
        }).attach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
//        return true;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search:
                return true;
            case R.id.action_logout:

                openDiaLog();
                return true;
            default:
             return  super.onOptionsItemSelected(item);
        }
    }

    private void logoutUser() {
        // Launching the login activity
        sharedPreferences.edit().clear().apply();
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
    }

    private  void  openDiaLog(){
        myDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
        Button btnNo = myDialog.findViewById(R.id.btn_no_logout);
        Button btnLogin = myDialog.findViewById(R.id.btn_logout);

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit().clear().apply();
                Intent intent = new Intent(view.getContext(), SignIn.class);
                startActivity(intent);
            }
        });
    }
}