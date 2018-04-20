package com.din.testhttp.html;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.din.testhttp.util.FileUtil;
import com.din.testhttp.R;

import java.util.List;

/**
 * Created by dinzhenyan on 2018/4/1.
 */

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder> {

    private List<Item> list;
    private Activity activity;

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
                        viewHolder.picture.setDrawingCacheEnabled(true);
                        Bitmap bitmap = Bitmap.createBitmap(viewHolder.picture.getDrawingCache());
                        new FileUtil().savePhoto(bitmap, "pictures", viewHolder.title.getText().toString());
                        viewHolder.picture.setDrawingCacheEnabled(false);
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
        Item item = list.get(position);
        holder.title.setText(item.getTitle());
        holder.picture.setImageResource(R.drawable.ic_launcher_background);
        String tag = item.getPicture();
        holder.picture.setTag(R.id.image_key, tag);
        if (tag != null) {
            Glide.with(activity).load(item.getPicture()).into(holder.picture);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public PictureAdapter(Activity activity, List<Item> list) {
        this.activity = activity;
        this.list = list;
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
