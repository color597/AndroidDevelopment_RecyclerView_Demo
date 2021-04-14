package com.lxp.recyclerview_demo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ApplicationViewHolder> {

    private List<Application> mApplicationList;
    private View mItemView;
    private int mLayoutMode;

    /**
     * @param applicationList: 类型为Application的List集
     * @param layoutMode:      0，线性；1：网格
     */
    public ApplicationAdapter(List<Application> applicationList, int layoutMode) {
        this.mApplicationList = applicationList;
        this.mLayoutMode = layoutMode;
    }

    @NonNull
    @Override
    public ApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (this.mLayoutMode) {
            case 0:
                this.mItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.application_item_linear_view, parent, false);
                break;
            case 1:
                this.mItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.application_item_grid_view, parent, false);
                break;
        }
        return new ApplicationViewHolder(this.mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicationViewHolder holder, int position) {
        Application application = this.mApplicationList.get(position);
        holder.mAppIconView.setImageDrawable(application.getAppIcon());
        holder.mTextView.setText(application.getAppLabel());

//      设置单击监听事件
        holder.itemView.setOnClickListener(v -> {
            v.getContext().startActivity(application.getAppIntent());
        });

//      设置长按监听事件
        holder.itemView.setOnLongClickListener(v -> {
            String tip = application.isSystemApp() ? "这是系统应用" : "这是用户应用";
            Toast.makeText(v.getContext(), tip, Toast.LENGTH_SHORT).show();
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return mApplicationList == null ? 0 : mApplicationList.size();
    }

    public static class ApplicationViewHolder extends RecyclerView.ViewHolder {

        private ImageView mAppIconView;
        private TextView mTextView;

        public ApplicationViewHolder(@NonNull View view) {
            super(view);
            this.mAppIconView = view.findViewById(R.id.app_icon);
            this.mTextView = view.findViewById(R.id.app_label);
        }
    }

}
