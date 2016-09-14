package com.xiarh.aireader.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xiarh.aireader.R;
import com.xiarh.aireader.bean.zhihu.ZhiHuStory;
import com.xiarh.aireader.config.Config;
import com.xiarh.aireader.ui.activity.ZhiHuDetailActivity;
import com.xiarh.aireader.utils.DBUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiarh on 2016/9/8.
 */
public class ZhiHuFragmentAdapter extends RecyclerView.Adapter<ZhiHuFragmentAdapter.ZhiHuViewHolder> {
    private ArrayList<ZhiHuStory> zhiHuStories;
    private Context mContext;

    public ZhiHuFragmentAdapter(ArrayList<ZhiHuStory> zhiHuStories, Context mContext) {
        this.zhiHuStories = zhiHuStories;
        this.mContext = mContext;
    }

    @Override
    public ZhiHuFragmentAdapter.ZhiHuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ZhiHuViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_zhihu, parent, false));
    }

    @Override
    public void onBindViewHolder(final ZhiHuFragmentAdapter.ZhiHuViewHolder holder, int position) {
        final ZhiHuStory zhiHuStory = zhiHuStories.get(position);
        //刚开始加载，如果没有阅读过字体显示黑色，阅读过则显示灰色
        if (!DBUtils.getDB(mContext).isRead(Config.ZHIHU, zhiHuStory.getId() + "", 1))
            holder.tvZhihu.setTextColor(Color.BLACK);
        else
            holder.tvZhihu.setTextColor(Color.GRAY);
        holder.tvZhihu.setText(zhiHuStory.getTitle());
        holder.tvDate.setText(zhiHuStory.getDate());
        Picasso.with(mContext)
                .load(zhiHuStory.getImages().get(0))
                .fit()
                .tag(mContext)
                .into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBUtils.getDB(mContext).insertHasRead(Config.ZHIHU, zhiHuStory.getId()+"", 1);
                holder.tvZhihu.setTextColor(Color.GRAY);
                Intent intent = new Intent();
                intent.setClass(mContext, ZhiHuDetailActivity.class);
                intent.putExtra("id", zhiHuStory.getId());
                intent.putExtra("title", zhiHuStory.getTitle());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return zhiHuStories.size();
    }

    public class ZhiHuViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_zhihu)
        ImageView imageView;
        @BindView(R.id.tv_zhihu)
        TextView tvZhihu;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.cardview)
        CardView cardView;

        public ZhiHuViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
