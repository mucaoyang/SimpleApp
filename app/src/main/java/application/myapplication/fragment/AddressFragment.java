package application.myapplication.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import application.myapplication.adapter.AddressAdapter;
import application.myapplication.adapter.ChatListAdapter;
import application.myapplication.R;

public class AddressFragment extends Fragment {
    private View mView;
    private Context mContext;
    private ListView mAddressList;
    private AddressAdapter mAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.fragment_address_layout, container, false);
        }
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        mAddressList = mView.findViewById(R.id.address_list);
        mAdapter = new AddressAdapter(mContext, R.layout.adapter_address_layout, AddressAdapter.names);
        mAddressList.setAdapter(mAdapter);
    }

}
