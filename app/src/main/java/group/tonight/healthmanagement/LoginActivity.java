package group.tonight.healthmanagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import group.tonight.healthmanagement.dao.UserBeanDao;
import group.tonight.healthmanagement.model.HealthDataBean;
import group.tonight.healthmanagement.model.SportDataBean;
import group.tonight.healthmanagement.model.UserBean;

/**
 * 登录
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String PREF_USER_ID = "pref_user_id";
    public static final String EXTRA_USER = "user";
    public static final String PREF_FIRST_LAUNCHER = "pref_first_launcher";
    private EditText mUserIdView;
    private EditText mPasswordView;
    private CheckBox mRememberPasswordView;
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUserIdView = (EditText) findViewById(R.id.user_id);
        mPasswordView = (EditText) findViewById(R.id.password);

        mRememberPasswordView = (CheckBox) findViewById(R.id.remember_password);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);

        String userId = mPreferences.getString(PREF_USER_ID, null);
        if (userId != null) {
            List<UserBean> list = App.getDaoSession().getUserBeanDao().queryBuilder().where(UserBeanDao.Properties.UserId.eq(Long.parseLong(userId))).build().list();
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra(EXTRA_USER, list.get(0));
            startActivity(intent);
            finish();
            return;
        }
        findViewById(R.id.select_user).setOnClickListener(this);
        findViewById(R.id.commit).setOnClickListener(this);

        if (mPreferences.getBoolean(PREF_FIRST_LAUNCHER, true)) {
            mPreferences.edit()
                    .putBoolean(PREF_FIRST_LAUNCHER, false)
                    .apply();
            List<SportDataBean> typeBeanList = new ArrayList<>();
            typeBeanList.add(new SportDataBean("晨跑", true, true));
            typeBeanList.add(new SportDataBean("早操晨练", false, true));
            typeBeanList.add(new SportDataBean("日间行走", false, true));
            typeBeanList.add(new SportDataBean("骑行", true, true));
            typeBeanList.add(new SportDataBean("游泳", false, true));
            typeBeanList.add(new SportDataBean("球类运动", false, true));
            typeBeanList.add(new SportDataBean("晚间跑步", true, true));

            for (SportDataBean typeBean : typeBeanList) {
                App.getDaoSession().getSportDataBeanDao().save(typeBean);
            }

            List<HealthDataBean> healthTypeBeanList = new ArrayList<>();
            healthTypeBeanList.add(new HealthDataBean("身高", "单位为公分", true));
            healthTypeBeanList.add(new HealthDataBean("体重", "单位为千克", true));
            healthTypeBeanList.add(new HealthDataBean("视力", true));
            healthTypeBeanList.add(new HealthDataBean("听力", true));
            healthTypeBeanList.add(new HealthDataBean("骨质", true));
            healthTypeBeanList.add(new HealthDataBean("肾脏", true));
            healthTypeBeanList.add(new HealthDataBean("四肢", true));
            healthTypeBeanList.add(new HealthDataBean("血液", true));
            for (HealthDataBean healthTypeBean : healthTypeBeanList) {
                App.getDaoSession().getHealthDataBeanDao().save(healthTypeBean);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_register) {
            startActivity(new Intent(this, RegisterActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit:
                String userId = mUserIdView.getText().toString();
                String password = mPasswordView.getText().toString();

                if (TextUtils.isEmpty(userId)) {
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    return;
                }
                List<UserBean> list = App.getDaoSession().getUserBeanDao().queryBuilder().where(UserBeanDao.Properties.UserId.eq(Long.parseLong(userId))).build().list();
                if (list.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "账号不存在", Toast.LENGTH_SHORT).show();
                    return;
                }
                UserBean user = list.get(0);
                String userPassword = user.getPassword();
                if (!TextUtils.equals(password, userPassword)) {
                    Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mRememberPasswordView.isChecked()) {
                    mPreferences.edit()
                            .putString(PREF_USER_ID, userId)
                            .apply();
                }
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.putExtra(EXTRA_USER, user);
                startActivity(intent);
                finish();
                break;
            case R.id.select_user:
                final String[] strings = {
                        "111111",
                        "222222",
                        "333333",
                        "444444",
                };
                new AlertDialog.Builder(this)
                        .setSingleChoiceItems(
                                strings
                                , 0
                                , new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mUserIdView.setText(strings[which]);
                                        mPasswordView.setText("12345678");
                                        dialog.dismiss();
                                    }
                                })
                        .show();
                break;
            default:
                break;
        }
    }
}
