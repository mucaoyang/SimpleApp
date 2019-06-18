package application.myapplication;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import application.myapplication.fragment.AddressFragment;
import application.myapplication.fragment.ChatFragment;
import application.myapplication.fragment.MineFragment;

/**
 * Created by Administrator on 2019-06-17.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout mAddressTab, mAppTab, mMineTab;
    private RelativeLayout mChatTab;
    private ChatFragment mChatFragment;
    private Fragment mCurrentFragment;
    private MineFragment mMineFragment;
    private AddressFragment mAddressFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        initView();
        initEvent();
        initFragment();
    }

    private void initView() {
        mAddressTab = findViewById(R.id.address_tab);
        mAppTab = findViewById(R.id.app_tab);
        mMineTab = findViewById(R.id.mine_tab);
        mChatTab = findViewById(R.id.chat_tab);
    }

    private void initEvent() {
        mAddressTab.setOnClickListener(this);
        mAppTab.setOnClickListener(this);
        mMineTab.setOnClickListener(this);
        mChatTab.setOnClickListener(this);
    }

    private void initFragment() {
        mChatFragment = new ChatFragment();
        mMineFragment = new MineFragment();
        mAddressFragment = new AddressFragment();
        FragmentManager manager = this.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.content, mChatFragment);
        transaction.add(R.id.content, mMineFragment);
        transaction.add(R.id.content, mAddressFragment);
        transaction.hide(mChatFragment);
        transaction.hide(mMineFragment);
        transaction.hide(mAddressFragment);
        transaction.show(mChatFragment);
        mCurrentFragment = mChatFragment;
        transaction.commit();
    }

    private void switchFragment(Fragment targetFragment) {
        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.hide(mCurrentFragment).show(targetFragment);
        ft.commit();
        this.mCurrentFragment = targetFragment;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chat_tab:
                switchFragment(mChatFragment);
                break;
            case R.id.address_tab:
                switchFragment(mAddressFragment);
                break;
            case R.id.app_tab:
                break;
            case R.id.mine_tab:
                switchFragment(mMineFragment);
                break;
        }

    }
}
