package application.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import application.myapplication.R;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatHolder> {

    private Context mContext;
    private String[] times = new String[]{"18:09", "18:08", "18:07", "18:06", "18:05", "18:04", "18:03", "18:02", "18:01", "15:02",
            "14:09", "14:08", "14:07", "14:06", "14:05", "14:04", "14:03", "14:02", "12:01", "11:02"};
    private OnClickListener mListener;
    private List<String> mDatas;


    public ChatListAdapter(Context context) {
        this.mContext = context;
        mDatas = new ArrayList<>();
    }

    @Override
    public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_chat_layout, parent, false);
        return new ChatHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatHolder holder, final int position) {
        holder.mChatUserContent.setText(mDatas.get(position));
        holder.mChatUserName.setText(mDatas.get(position));
        holder.mChatUserTime.setText(times[position]);
        holder.mChatUserAvatar.setImageResource(R.drawable.account);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.goChat(mDatas.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    static class ChatHolder extends RecyclerView.ViewHolder {
        TextView mChatUserName, mChatUserTime, mChatUserContent;
        ImageView mChatUserAvatar, mChatUserLock;


        public ChatHolder(View view) {
            super(view);
            mChatUserName = view.findViewById(R.id.txt_chat_user_name);
            mChatUserTime = view.findViewById(R.id.txt_chat_user_time);
            mChatUserContent = view.findViewById(R.id.txt_chat_user_content);
            mChatUserAvatar = view.findViewById(R.id.img_chat_user_avatar);
            mChatUserLock = view.findViewById(R.id.img_chat_user_lock);
        }
    }

    public interface OnClickListener {
        void goChat(String name);
    }

    public void setOnClickListener(OnClickListener listener) {
        mListener = listener;
    }


    public void resetDatas() {
        mDatas = new ArrayList<>();
    }

    public void updateList(List<String> newPreviewUrls) {
        if (newPreviewUrls != null) {
            mDatas.addAll(newPreviewUrls);
            notifyDataSetChanged();
        }

    }

}
