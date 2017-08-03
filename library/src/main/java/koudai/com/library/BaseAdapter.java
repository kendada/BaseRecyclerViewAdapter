package koudai.com.library;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import java.util.List;

/**
 * Created by abc on 2017/8/2.
 * 添加头部和尾部
 */

public abstract class BaseAdapter<T> extends BaseRecyclerAdapter<T, BaseViewHolder>{

    private static final String TAG = BaseAdapter.class.getSimpleName();

    public static final int HEADER_VIEW_TYPE = 100001;
    public static final int FOOTER_VIEW_TYPE = 100002;

    private LinearLayout mHeaderRootView;
    private LinearLayout mFooterRootView;
    
    public BaseAdapter(Context context, int layoutResId, List<T> list) {
        super(context, layoutResId, list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(Context context, ViewGroup parent, int layoutResId, int viewType) {
        switch (viewType){
            case HEADER_VIEW_TYPE:
                return BaseViewHolder.createViewHolder(context, mHeaderRootView);
            case FOOTER_VIEW_TYPE:
                return BaseViewHolder.createViewHolder(context, mFooterRootView);
        }
        return BaseViewHolder.createViewHolder(context, parent, layoutResId);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        position = position - getHeaderLayoutCount();
        int viewType = holder.getItemViewType();
        switch (viewType){
            case 0:
                superbindViewHolder(holder, position);
                break;
            case HEADER_VIEW_TYPE:
            case FOOTER_VIEW_TYPE:
                break;
            default:
                superbindViewHolder(holder, position);
                break;
        }
    }

    /**
     * 这里主要是设置单击和长按事件
     * */
    private void superbindViewHolder(BaseViewHolder holder, int position){
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return getHeaderLayoutCount() + mList.size() + getFooterLayoutCount();
    }

    @Override
    public int getItemViewType(int position) {
        int numHeaders = getHeaderLayoutCount();
        if(position < numHeaders){
            return HEADER_VIEW_TYPE;
        } else {
            int adjPosition = position - numHeaders;
            int adapterCount = mList.size();
            if(adjPosition < adapterCount){
                return getDefItemViewType(adjPosition);
            } else {
                adjPosition = adjPosition - adapterCount;
                int numFooters = getFooterLayoutCount();
                if(adjPosition < numFooters){
                    return FOOTER_VIEW_TYPE;
                } else {
                    return getDefItemViewType(position);
                }
            }
        }
    }

    protected int getDefItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public LinearLayout getHeaderLayout(){
        return mHeaderRootView;
    }

    public LinearLayout getFooterLayout(){
        return mFooterRootView;
    }

    public int addHeaderView(View header) {
        return addHeaderView(header, -1);
    }

    public int addHeaderView(View header, int index) {
        return addHeaderView(header, index, LinearLayout.VERTICAL);
    }

    public int addHeaderView(View header, int index, int orientation){
        if(mHeaderRootView == null){
            mHeaderRootView = new LinearLayout(mContext);
            mHeaderRootView.setOrientation(orientation);
            if(orientation == LinearLayout.VERTICAL){
                mHeaderRootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            } else {
                mHeaderRootView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
            }
        }

        int childCount = mHeaderRootView.getChildCount();
        if(index < 0 || index > childCount){
            index = childCount;
        }
        mHeaderRootView.addView(header, index);
        if (mHeaderRootView.getChildCount() == 1) {
            int position = getHeaderViewPosition();
            if (position != -1) {
                notifyItemInserted(position);
            }
        }

        return index;
    }

    public void removeHeaderView(View headerView) {
        if(getHeaderLayoutCount() == 0) return;

        mHeaderRootView.removeView(headerView);
        if(mHeaderRootView.getChildCount() == 0){
            int position = getHeaderViewPosition();
            if(position != -1){
                notifyItemRemoved(position);
            }
        }
    }

    public void removeAllHeaderView() {
        if(getHeaderLayoutCount() == 0) return;

        mHeaderRootView.removeAllViews();
        int position = getHeaderViewPosition();
        if(position != -1){
            notifyItemRemoved(position);
        }
    }

    public int setHeaderView(View headerView){
        return setHeaderView(headerView, 0);
    }

    public int setHeaderView(View headerView, int index){
        return setHeaderView(headerView, index, LinearLayout.VERTICAL);
    }

    /**
     * @param headerView
     * @param index
     * @param orientation 取值为 LinearLayout.VERTICAL or LinearLayout.HORIZONTAL
     * */
    public int setHeaderView(View headerView, int index, int orientation){
        if(mHeaderRootView == null || mHeaderRootView.getChildCount() <= index){
            return addHeaderView(headerView, index, orientation);
        } else {
            mHeaderRootView.removeViewAt(index);
            mHeaderRootView.addView(headerView, index);
            return index;
        }
    }

    public int addFooterView(View footerView){
        return addFooterView(footerView, -1);
    }

    public int addFooterView(View footerView, int index){
        return addFooterView(footerView, index, LinearLayout.VERTICAL);
    }

    /**
     * @param footerView
     * @param index
     * @param orientation 取值为 LinearLayout.VERTICAL or LinearLayout.HORIZONTAL
     * */
    public int addFooterView(View footerView, int index, int orientation){
        if(mFooterRootView == null){
            mFooterRootView = new LinearLayout(mContext);
            mFooterRootView.setOrientation(orientation);
            if(orientation == LinearLayout.VERTICAL){
                mFooterRootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            } else {
                mFooterRootView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
            }
        }
        int childCount = mFooterRootView.getChildCount();
        if(index < 0 || index > childCount){
            index = childCount;
        }
        mFooterRootView.addView(footerView, index);
        if(mFooterRootView.getChildCount() == 1){
            int position = getFooterViewPosition();
            if(position != -1){
                notifyItemInserted(position);
            }
        }
        return index;
    }

    public void removeFooterView(View footerView) {
        if (getFooterLayoutCount() == 0) return;

        mFooterRootView.removeView(footerView);
        if (mFooterRootView.getChildCount() == 0) {
            int position = getFooterViewPosition();
            if (position != -1) {
                notifyItemRemoved(position);
            }
        }
    }

    public void removeAllFooterView() {
        if (getFooterLayoutCount() == 0) return;

        mFooterRootView.removeAllViews();
        int position = getFooterViewPosition();
        if (position != -1) {
            notifyItemRemoved(position);
        }
    }

    public int setFooterView(View footerView) {
        return setFooterView(footerView, 0);
    }

    public int setFooterView(View footerView, int index) {
        return setFooterView(footerView, index, LinearLayout.VERTICAL);
    }

    /**
     * @param footerView
     * @param index
     * @param orientation 取值为 LinearLayout.VERTICAL or LinearLayout.HORIZONTAL
     * */
    public int setFooterView(View footerView, int index, int orientation){
        if(mFooterRootView == null || mFooterRootView.getChildCount() <= index){
            return addFooterView(footerView, index, orientation);
        } else {
            mFooterRootView.removeViewAt(index);
            mFooterRootView.addView(footerView, index);
            return index;
        }
    }

    private int getHeaderViewPosition() {
        return 0;
    }

    private int getFooterViewPosition() {
        return getHeaderLayoutCount() + mList.size();
    }

    public int getHeaderLayoutCount() {
        return mHeaderRootView == null || mHeaderRootView.getChildCount() == 0 ? 0 : 1;
    }

    public int getFooterLayoutCount() {
        return mFooterRootView == null || mFooterRootView.getChildCount() == 0 ? 0 : 1;
    }

}
