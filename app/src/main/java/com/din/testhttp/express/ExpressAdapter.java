package com.din.testhttp.express;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.din.testhttp.R;

import java.util.List;

public class ExpressAdapter extends RecyclerView.Adapter<ExpressAdapter.ViewHolder> {

    private List<Express> list;

    //-------- Adapter添加类型为DataBean的List数据 ---------------
    public ExpressAdapter(List<Express> list) {
        this.list = list;
    }

    //--------  内容ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView time, context;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            time = (TextView) view.findViewById(R.id.time);
            context = (TextView) view.findViewById(R.id.context);
        }

        public void bindData(Express express) {
            time.setText(express.getTime());
            context.setText(express.getContext());
        }
    }

    /**
     * @param parent
     * @param viewType
     * @return
     */
    public ExpressAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.express_item, parent, false);
        ExpressAdapter.ViewHolder holder = new ExpressAdapter.ViewHolder(view);
        return holder;
    }

    /**
     * @param holder
     * @param position
     */
    public void onBindViewHolder(ExpressAdapter.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        Express express = list.get(position);
        vh.bindData(express);
    }

    //  获取Item总数
    public int getItemCount() {
        return list.size();
    }
}