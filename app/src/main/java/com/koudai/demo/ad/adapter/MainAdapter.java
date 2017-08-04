package com.koudai.demo.ad.adapter;

import android.content.Context;

import com.koudai.demo.ad.R;

import java.util.List;

import koudai.com.library.BaseAdapter;
import koudai.com.library.BaseViewHolder;

/**
 * Created by abc on 2017/8/4.
 *
 */

public class MainAdapter extends BaseAdapter<String>{


    public MainAdapter(Context context, List<String> list) {
        super(context, R.layout.adapter_main_layout, list);
    }

    @Override
    public void onBindData(BaseViewHolder holder, String item, int position) {
        holder.setText(R.id.text_view, item);
    }
}
