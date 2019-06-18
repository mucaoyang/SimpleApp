package application.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import application.myapplication.adapter.ChatRecordAdapter;
import application.myapplication.bean.ChatRecordBean;
import application.myapplication.database.DatabaseHelper;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mUserName;
    private RecyclerView mList;
    private List<ChatRecordBean> mDatas = new ArrayList<>();
    private SQLiteDatabase database;
    private String name;
    private ChatRecordAdapter mAdapter;
    private LinearLayoutManager mManager;
    private EditText mEditContent;
    private TextView mEnter;
    private int mode = 1;
    private ImageView mPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_layout);
        name = getIntent().getStringExtra("accountName");
        DatabaseHelper dbHelper = new DatabaseHelper(this, "account.db", null, 1);
        database = dbHelper.getWritableDatabase();
        queryData();
        initViewAndEvent();
        mUserName.setText(name);
    }

    private void initViewAndEvent() {
        mUserName = findViewById(R.id.user_name);
        mList = findViewById(R.id.list_chat_record);
        mAdapter = new ChatRecordAdapter(this, mDatas);
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mList.setAdapter(mAdapter);
        mList.setLayoutManager(mManager);
        mEditContent = findViewById(R.id.edit_content);
        mEnter = findViewById(R.id.enter);
        mEnter.setOnClickListener(this);
        mPhone = findViewById(R.id.phone);
        mPhone.setOnClickListener(this);
    }

    private boolean isFirst = true;

    @Override
    protected void onResume() {
        super.onResume();
        if (isFirst) {
            mEditContent.requestFocus();
        }
        isFirst = false;
    }

    private void queryData() {
        Cursor cursor = database.query("chatRecord", new String[]{"name,type,content,time"}, "name=?", new String[]{name}, null, null, null);
        if (!cursor.moveToFirst()) {
            return;
        }
        do {
            ChatRecordBean bean = new ChatRecordBean();
            bean.setName(name);
            bean.setType(cursor.getInt(cursor.getColumnIndex("type")));
            bean.setContent(cursor.getString(cursor.getColumnIndex("content")));
            bean.setTime(cursor.getInt(cursor.getColumnIndex("time")));
            mDatas.add(bean);
        } while (cursor.moveToNext());
        cursor.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.enter:
                sendMessage();
                break;
            case R.id.phone:
                if (mode == 0) {
                    mode = 1;
                } else if (mode == 1) {
                    mode = 0;
                }
                break;
        }
    }

    private void sendMessage() {
        String content = mEditContent.getText().toString();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }
        ChatRecordBean bean = new ChatRecordBean();
        bean.setName(name);
        bean.setType(mode);
        bean.setTime((int) System.currentTimeMillis());
        bean.setContent(content);
        mDatas.add(bean);
        mAdapter.notifyDataSetChanged();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("type", mode);
        values.put("time", bean.getTime());
        values.put("content", content);
        database.insert("chatRecord", null, values);
        mEditContent.setText("");
    }
}
