package group.tonight.healthmanagement;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import group.tonight.healthmanagement.dao.UserBeanDao;
import group.tonight.healthmanagement.model.UserBean;

/**
 * 注册
 */
public class RegisterActivity extends BackEnableBaseActivity {

    private EditText mUserIdView;
    private EditText mUserNameView;
    private EditText mGenderView;
    private EditText mBirthdayView;
    private EditText mPasswordView;
    private EditText mConfirmPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }

        mUserIdView = (EditText) findViewById(R.id.user_id);
        mUserNameView = (EditText) findViewById(R.id.user_name);
        mGenderView = (EditText) findViewById(R.id.gender);
        mBirthdayView = (EditText) findViewById(R.id.birthday);
        mPasswordView = (EditText) findViewById(R.id.password);
        mConfirmPasswordView = (EditText) findViewById(R.id.confirm_password);

        findViewById(R.id.commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = mUserIdView.getText().toString();
                String userName = mUserNameView.getText().toString();
                String gender = mGenderView.getText().toString();
                String birthday = mBirthdayView.getText().toString();
                String password = mPasswordView.getText().toString();
                String confirmPassword = mConfirmPasswordView.getText().toString();

                if (!TextUtils.equals(password, confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<UserBean> list = App.getDaoSession().getUserBeanDao().queryBuilder().where(UserBeanDao.Properties.UserId.eq(userId)).build().list();
                if (!list.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "账号已存在", Toast.LENGTH_SHORT).show();
                    return;
                }
                UserBean user = new UserBean();
                user.setUserId(Long.parseLong(userId));
                user.setUserName(userName);
                user.setGender(gender);
                user.setBirthday(birthday);
                user.setPassword(password);
                App.getDaoSession().getUserBeanDao().save(user);
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
