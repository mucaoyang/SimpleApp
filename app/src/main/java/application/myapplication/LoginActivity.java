package application.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import application.myapplication.database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mEditAccountName, mEditAccountPassword;
    private ImageView mDeleteAccountName, mHidePassword;
    private TextView mLogin, mForgetPassword;
    private SQLiteDatabase database;
    private boolean isInsertData;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_layout);
        initView();
        initEvent();
        DatabaseHelper dbHelper = new DatabaseHelper(this, "account.db", null, 1);
        database = dbHelper.getWritableDatabase();
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        isInsertData = preferences.getBoolean("isInsertData", false);
        if (!isInsertData) {
            insertData();
            isInsertData = true;
            editor.putBoolean("isInsertData", isInsertData);
            editor.commit();
        }
    }

    private void initView() {
        mEditAccountName = findViewById(R.id.edit_account);
        mEditAccountPassword = findViewById(R.id.edit_password);
        mDeleteAccountName = findViewById(R.id.img_delete_account);
        mHidePassword = findViewById(R.id.img_hide_password);
        mLogin = findViewById(R.id.login);
        mForgetPassword = findViewById(R.id.forget_password);
    }


    private void initEvent() {
        mDeleteAccountName.setOnClickListener(this);
        mHidePassword.setOnClickListener(this);
        mLogin.setOnClickListener(this);
        mForgetPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_delete_account:

                break;
            case R.id.img_hide_password:

                break;
            case R.id.login:
                if (checkAccount()) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.forget_password:
                break;
        }
    }

    private boolean checkAccount() {
        if (TextUtils.isEmpty(mEditAccountName.getText()) || TextUtils.isEmpty(mEditAccountPassword.getText())) {
            Toast.makeText(this, "账户密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        String name = mEditAccountName.getText().toString();
        String password = mEditAccountPassword.getText().toString();
        return queryAccountName(name, password);
    }

    private boolean queryAccountName(String name, String password) {
        Cursor cursor = database.query("account", new String[]{"account,password"}, "account=?", new String[]{name}, null, null, null);
        if (cursor == null) {
            Toast.makeText(this, "不存在该用户", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!cursor.moveToFirst()) {
            Toast.makeText(this, "不存在该用户", Toast.LENGTH_SHORT).show();
            return false;
        }
        do {
            String queryPassword = cursor.getString(cursor.getColumnIndex("password"));
            if (password.equals(queryPassword)) {
                cursor.close();
                return true;
            }
        } while (cursor.moveToNext());
        cursor.close();
        return false;
    }

    private void insertData() {
        ContentValues values = new ContentValues();
        values.put("account", "admin");
        values.put("password", "admin");
        database.insert("account", null, values);
        values.clear();
        values.put("account", "xudawei");
        values.put("password", "xudawei");
        database.insert("account", null, values);
        values.clear();
        values.put("account", "xudawei");
        values.put("password", "admin");
        database.insert("account", null, values);
    }
}
