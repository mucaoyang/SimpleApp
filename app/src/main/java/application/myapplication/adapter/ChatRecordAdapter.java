package application.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import application.myapplication.R;
import application.myapplication.bean.ChatRecordBean;

public class ChatRecordAdapter extends RecyclerView.Adapter<ChatRecordAdapter.ChatRecordholder> {
    private static final String TAG = "ChatRecordAdapter";
    private Context mContext;
    private List<ChatRecordBean> mDatas;

    public ChatRecordAdapter(Context context, List<ChatRecordBean> data) {
        this.mContext = context;
        this.mDatas = data;
    }

    @Override
    public ChatRecordholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_chat_record_layout, parent, false);
        return new ChatRecordholder(view);
    }

    @Override
    public void onBindViewHolder(ChatRecordholder holder, int position) {
        ChatRecordBean bean = mDatas.get(position);
        if (bean.getType() == 0) {  //接收
            holder.mLeftLayout.setVisibility(View.VISIBLE);
            holder.mRightLayout.setVisibility(View.GONE);
            holder.mLeftContent.setText(bean.getContent());
        } else if (bean.getType() == 1) {  //发送
            holder.mLeftLayout.setVisibility(View.GONE);
            holder.mRightLayout.setVisibility(View.VISIBLE);
            holder.mRightContent.setText(bean.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    static class ChatRecordholder extends RecyclerView.ViewHolder {
        TextView mLeftContent, mRightContent;
        LinearLayout mLeftLayout, mRightLayout;

        public ChatRecordholder(View v) {
            super(v);
            mLeftContent = v.findViewById(R.id.left_user_content);
            mRightContent = v.findViewById(R.id.right_user_content);
            mLeftLayout = v.findViewById(R.id.left_user_layout);
            mRightLayout = v.findViewById(R.id.right_user_layout);
        }
    }


}
