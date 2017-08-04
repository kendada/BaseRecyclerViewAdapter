package com.koudai.demo.ad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.koudai.demo.ad.adapter.MainAdapter;

import java.util.ArrayList;
import java.util.List;

import koudai.com.library.BaseAdapter;
import koudai.com.library.BaseRecyclerAdapter;
import koudai.com.library.BaseViewHolder;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    MainAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> mList = new ArrayList<>();
        for (int i=0; i<100; i++){
            mList.add("Item " + i);
        }

        mAdapter = new MainAdapter(this, mList);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, long id) {
                Toast.makeText(MainActivity.this, "position = " + position, Toast.LENGTH_SHORT).show();
            }
        });

        mAdapter.setOnItemLongClickListener(new BaseRecyclerAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, int position, long id) {
                Toast.makeText(MainActivity.this, "ItemLong && position = " + position, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }
}
