package com.din.testhttp.jsoup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.din.testhttp.util.FileUtil;
import com.din.testhttp.R;

import java.util.List;

/**
 * Created by dinzhenyan on 2018/4/1.
 */

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder> {

    private Activity activity;
    private List<Item> initList;
    private String[] urls;

    public PictureAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setInitList(List<Item> initList) {
        if (initList.isEmpty()) {
            return;
        }
        this.initList = initList;
        notifyDataSetChanged();
    }

    public void setCurrentPage(int start, int end) {
        if (urls == null) {
            urls = new String[initList.size()];
        }
        for (int i = start; i <= end; i++) {
            if (urls[i] == null) {
                urls[i] = initList.get(i).getPicture();
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public PictureAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pic_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("是否保存").setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Bitmap bitmap = viewHolder.picture.getDrawingCache();
                        new FileUtil().savePhoto(bitmap, viewHolder.title.getText().toString());
                        dialog.cancel();
                    }
                }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create();
                builder.show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PictureAdapter.ViewHolder holder, int position) {
        Item item = initList.get(position);
        holder.title.setText(item.getTitle());
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.a).skipMemoryCache(true);
        Glide.with(activity).setDefaultRequestOptions(options)
                .load(urls[position])
                .into(holder.picture);
    }

    @Override
    public int getItemCount() {
        return initList == null ? 0 : initList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView picture;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.text);
            picture = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}