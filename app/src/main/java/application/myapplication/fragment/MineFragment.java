package application.myapplication.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import application.myapplication.R;

/**
 * Created by Administrator on 2019-06-18.
 */

public class MineFragment extends Fragment {
    private View mView;
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.fragment_mine_layout, container, false);
        }
        return mView;
    }
}
