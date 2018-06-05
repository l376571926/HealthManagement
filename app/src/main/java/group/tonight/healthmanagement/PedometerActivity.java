package group.tonight.healthmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.socks.library.KLog;

import org.greenrobot.greendao.query.Query;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import group.tonight.healthmanagement.dao.StepDataBeanDao;
import group.tonight.healthmanagement.model.StepDataBean;
import group.tonight.healthmanagement.model.UserBean;

public class PedometerActivity extends BaseActivity {

    private UserBean mUser;
    private TextView mTodayStepsView;
    private TextView mTodayCostTimeView;
    private TextView mTodayCostCalorieView;
    private TextView mWeekStepsAmountView;
    private TextView mWeekAverageStepsView;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1000) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mHandler.sendEmptyMessage(1000);
                    }
                }, 1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedometer);
        mTodayStepsView = (TextView) findViewById(R.id.today_steps);

        mTodayCostTimeView = (TextView) findViewById(R.id.today_cost_time);
        mTodayCostCalorieView = (TextView) findViewById(R.id.today_cost_calorie);

        mWeekStepsAmountView = (TextView) findViewById(R.id.week_steps_amount);
        mWeekAverageStepsView = (TextView) findViewById(R.id.week_average_steps);

        //初始化界面数据
        mTodayStepsView.setText(0 + "");
        mTodayCostTimeView.setText(getString(R.string.today_cost_time_place_holder, 0 + ""));
        mTodayCostCalorieView.setText(getString(R.string.today_cost_calorie_place_holder, 0 + ""));
        mWeekStepsAmountView.setText(getString(R.string.week_steps_amount_place_holder, 0));
        mWeekAverageStepsView.setText(getString(R.string.week_average_steps_place_holder, 0));

        if (getIntent().hasExtra(LoginActivity.EXTRA_USER)) {
            mUser = (UserBean) getIntent().getSerializableExtra(LoginActivity.EXTRA_USER);

            Date time = Calendar.getInstance().getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
            String format = dateFormat.format(time);

            Query<StepDataBean> beanQuery = App.getDaoSession().getStepDataBeanDao().queryBuilder()
                    .where(
                            StepDataBeanDao.Properties.Uid.eq(mUser.getId())
                            , StepDataBeanDao.Properties.CreateDate.eq(format)
                    )
                    .build();
            List<StepDataBean> list = beanQuery
                    .list();
            KLog.e();

        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View inflate = LayoutInflater.from(this).inflate(R.layout.layout_pedometer_activity_bottom_menu, null);
        dialog.setContentView(inflate);
        dialog.show();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.insert:
                if (mUser == null) {
                    return;
                }
                Intent addIntent = new Intent(this, AddHealthInfoActivity.class);
                addIntent.putExtra(LoginActivity.EXTRA_USER, mUser);
                startActivity(addIntent);
                break;
            case R.id.query:
                if (mUser == null) {
                    return;
                }
                Intent queryIntent = new Intent(this, QueryHealthInfoActivity.class);
                queryIntent.putExtra(LoginActivity.EXTRA_USER, mUser);
                startActivity(queryIntent);
                break;
            case R.id.pedometer:
                if (mUser == null) {
                    return;
                }
                Intent pedometerIntent = new Intent(this, PedometerActivity.class);
                pedometerIntent.putExtra(LoginActivity.EXTRA_USER, mUser);
                startActivity(pedometerIntent);
                break;
            case R.id.history:
                if (mUser == null) {
                    return;
                }
                Intent walkingHistoryIntent = new Intent(this, WalkingHistoryActivity.class);
                walkingHistoryIntent.putExtra(LoginActivity.EXTRA_USER, mUser);
                startActivity(walkingHistoryIntent);
                break;
            case R.id.setting:
                if (mUser == null) {
                    return;
                }
                Intent targetSettingIntent = new Intent(this, TargetSettingActivity.class);
                targetSettingIntent.putExtra(LoginActivity.EXTRA_USER, mUser);
                startActivity(targetSettingIntent);
                break;
            case R.id.progress:
                if (mUser == null) {
                    return;
                }
                Intent targetProgressIntent = new Intent(this, TargetProgressActivity.class);
                targetProgressIntent.putExtra(LoginActivity.EXTRA_USER, mUser);
                startActivity(targetProgressIntent);
                break;
            case R.id.exit:
                finish();
                break;
            default:
                break;
        }
    }
}
