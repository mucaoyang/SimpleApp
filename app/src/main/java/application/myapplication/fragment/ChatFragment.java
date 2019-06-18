package application.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import application.myapplication.ChatActivity;
import application.myapplication.adapter.ChatListAdapter;
import application.myapplication.R;

/**
 * Created by Administrator on 2019-06-17.
 */

public class ChatFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private View view;
    private Context mContext;
    private RecyclerView mChatList;
    private ChatListAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout mFreshLayout;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private int lastVisibleItem = 0;
    public List<String> mDatas = new ArrayList<>();
    private final int PAGE_COUNT = 5;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null)
            view = LayoutInflater.from(mContext).inflate(R.layout.fragment_chat_layout, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initView();
    }

    private void initView() {
        mChatList = view.findViewById(R.id.list_chat);
        if (mAdapter == null)
            mAdapter = new ChatListAdapter(mContext);
        mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mChatList.setAdapter(mAdapter);
        mChatList.setLayoutManager(mLayoutManager);
        mAdapter.setOnClickListener(new ChatListAdapter.OnClickListener() {
            @Override
            public void goChat(String name) {
                if (TextUtils.isEmpty(name)) {
                    return;
                }
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("accountName", name);
                mContext.startActivity(intent);
            }
        });
        mFreshLayout = view.findViewById(R.id.refresh_layout);
        mFreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mFreshLayout.setOnRefreshListener(this);

        mChatList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (lastVisibleItem + 1 == mAdapter.getItemCount()) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(mAdapter.getItemCount(), mAdapter.getItemCount() + PAGE_COUNT);
                            }
                        });
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private boolean isFirst = true;

    @Override
    public void onRefresh() {
        if (isFirst) {
            mAdapter.resetDatas();
            updateRecyclerView(0, PAGE_COUNT);
            isFirst = false;
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mFreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            mDatas.add("测试" + i);
        }
    }

    private void updateRecyclerView(int fromIndex, int toIndex) {
        List<String> newDatas = getDatas(fromIndex, toIndex);
        if (newDatas == null) {
            Toast.makeText(getContext(), "Request failure", Toast.LENGTH_SHORT).show();
        }
        if (newDatas.size() > 0) {
            mAdapter.updateList(newDatas);
        } else {
            mAdapter.updateList(null);
        }
    }

    private List<String> getDatas(final int firstIndex, final int lastIndex) {
        List<String> resList = new ArrayList<>();
        for (int i = firstIndex; i < lastIndex; i++) {
            if (i < mDatas.size()) {
                resList.add(mDatas.get(i));
            }
        }
        return resList;
    }
}
