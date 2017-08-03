package koudai.com.library;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abc on 2017/8/2.
 * RecyclerView 基础适配器，主要添加单击和长按事件
 */

public abstract class BaseRecyclerAdapter<T, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH>{

    public Context mContext;
    public List<T> mList;

    private int mLayoutResId;

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public BaseRecyclerAdapter(Context context, int layoutResId, List<T> list){
        mContext = context;
        mLayoutResId = layoutResId;
        this.mList = list == null ? new ArrayList<T>() : list;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType){
        VH holde = onCreateViewHolder(mContext, parent, mLayoutResId, viewType);
        return holde;
    }

    @Override
    public void onBindViewHolder(final VH holder, final int position){
        bindViewItemClickListener(holder, position);
        onBindData(holder, getItem(position), position);
    }

    private void bindViewItemClickListener(VH holder, final int position){
        if(holder == null) throw new NullPointerException("holder is null...");

        View itemView = holder.itemView;
        if(itemView == null) throw new NullPointerException("itemView is null...");

        if(mOnItemClickListener != null){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, position, getItemId(position));
                }
            });
        }

        if(mOnItemLongClickListener != null){
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return mOnItemLongClickListener.onItemLongClick(v, position, getItemId(position));
                }
            });
        }
    }

    public T getItem(int position){
        if(position >= getItemCount()){
            return null;
        }
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public abstract VH onCreateViewHolder(Context context, ViewGroup parent, int layoutResId, int viewType);

    /**
     * 对每个Item进行数据设置，需要在业务继承类实现
     * */
    public abstract void onBindData(VH holder, T t, int position);

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    public OnItemClickListener getOnItemClickListener(){
        return mOnItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        mOnItemLongClickListener = onItemLongClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener(){
        return mOnItemLongClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, long id);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, int position, long id);
    }
}
