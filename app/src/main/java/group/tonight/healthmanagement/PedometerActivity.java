package group.tonight.healthmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.socks.library.KLog;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import group.tonight.healthmanagement.dao.StepDataBeanDao;
import group.tonight.healthmanagement.dao.TargetDataBeanDao;
import group.tonight.healthmanagement.model.StepDataBean;
import group.tonight.healthmanagement.model.TargetDataBean;
import group.tonight.healthmanagement.model.UserBean;

public class PedometerActivity extends BaseActivity {
    private static final SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private static DecimalFormat mDecimalFormat = new DecimalFormat("0.0");
    private static final double BODY_WEIGHT = 50;//体重kg
    private static final double WALKING_SPEED = 1.1;//步行速度 m/s
    private double mKcalSpeed = 30 / (((400 * 1.0f) / WALKING_SPEED) / 60);//分钟/400米
    private UserBean mUser;
    private TextView mTodayStepsView;
    private TextView mTodayCostTimeView;
    private TextView mTodayCostCalorieView;
    private TextView mWeekStepsAmountView;
    private TextView mWeekAverageStepsView;

    private int mActiveSecond;
    private int mCurrentSteps;
    private StepDataBean mStepDataBean;
    private double mCostKcal;
    private int mWeekStepSum;
    private TargetDataBean mTargetDataBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedometer);

        KLog.e(mKcalSpeed);

        mTodayStepsView = (TextView) findViewById(R.id.today_steps);

        mTodayCostTimeView = (TextView) findViewById(R.id.today_cost_time);
        mTodayCostCalorieView = (TextView) findViewById(R.id.today_cost_calorie);

        mWeekStepsAmountView = (TextView) findViewById(R.id.week_steps_amount);
        mWeekAverageStepsView = (TextView) findViewById(R.id.week_average_steps);

        //初始化界面数据
        mTodayStepsView.setText(0 + "");
        mTodayCostTimeView.setText(getString(R.string.today_cost_time_place_holder, 0));
        mTodayCostCalorieView.setText(getString(R.string.today_cost_calorie_place_holder, 0 + ""));
        mWeekStepsAmountView.setText(getString(R.string.week_steps_amount_place_holder, 0));
        mWeekAverageStepsView.setText(getString(R.string.week_average_steps_place_holder, 0));

        if (getIntent().hasExtra(LoginActivity.EXTRA_USER)) {
            mUser = (UserBean) getIntent().getSerializableExtra(LoginActivity.EXTRA_USER);

            List<StepDataBean> list = getWeekStepDataBeanList(mUser.getId());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
            Calendar instance = Calendar.getInstance();
            List<TargetDataBean> targetDataBeanList = App.getDaoSession()
                    .getTargetDataBeanDao()
                    .queryBuilder()
                    .where(
                            TargetDataBeanDao.Properties.Uid.eq(mUser.getId())
                            , TargetDataBeanDao.Properties.Date.eq(dateFormat.format(instance.getTime()))
                    )
                    .build().list();
            if (!targetDataBeanList.isEmpty()) {
                mTargetDataBean = targetDataBeanList.get(0);
            }
            KLog.e();
            StepDataBean stepDataBean = null;
            for (StepDataBean bean : list) {
                long createTime = bean.getCreateTime();
                int steps = bean.getSteps();
                if (isToday(createTime)) {
                    stepDataBean = bean;
                } else {
                    mWeekStepSum = mWeekStepSum + steps;
                }
            }
            if (stepDataBean == null) {//无当日记录
                mStepDataBean = new StepDataBean();
                mStepDataBean.setUid(mUser.getId());
                mStepDataBean.setCreateTime(System.currentTimeMillis());
                mStepDataBean.setCreateDate(mDateFormat.format(System.currentTimeMillis()));
            } else {//有当日记录
                mStepDataBean = stepDataBean;
                mCurrentSteps = mStepDataBean.getSteps();
                mActiveSecond = mStepDataBean.getActiveSeconds();
                mCostKcal = mStepDataBean.getCalories();
            }
        }
    }

    private static SimpleDateFormat mDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    public static List<StepDataBean> getWeekStepDataBeanList(Long id) {
        Calendar nowCalendar = Calendar.getInstance();
        KLog.e("当前时间：" + mDateTimeFormat.format(nowCalendar.getTime()));

        Calendar weekStartCalendar = Calendar.getInstance();
        weekStartCalendar.set(Calendar.DAY_OF_WEEK, 1);
        long timeInMillis = weekStartCalendar.getTimeInMillis();
        KLog.e("本周开始时间：" + mDateTimeFormat.format(weekStartCalendar.getTime()));

        Calendar weekEndCalendar = Calendar.getInstance();
        weekEndCalendar.set(Calendar.DAY_OF_WEEK, 7);
        long timeInMillis1 = weekEndCalendar.getTimeInMillis();
        KLog.e("本周结束时间：" + mDateTimeFormat.format(weekEndCalendar.getTime()));

        //查询本周步数记录
        List<StepDataBean> list = App.getDaoSession().getStepDataBeanDao().queryBuilder()
                .where(
                        StepDataBeanDao.Properties.Uid.eq(id),
                        StepDataBeanDao.Properties.CreateTime.between(timeInMillis, timeInMillis1)
                )
                .build()
                .list();
        return list;
    }

    /**
     * 判断时间是不是今天
     *
     * @param millis
     * @return 是返回true，不是返回false
     */
    private static boolean isToday(long millis) {
        //当前时间
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(millis);

        Date now = Calendar.getInstance().getTime();

        //获取今天的日期
        String nowDay = mDateFormat.format(now);
        //对比的时间
        String day = mDateFormat.format(instance.getTime());
        boolean equals = day.equals(nowDay);
        return equals;
    }

    private Handler mHandler = new Handler();

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
//            KLog.e(mActiveSecond);
            mActiveSecond++;

            mCurrentSteps = ((int) Math.round(mActiveSecond * WALKING_SPEED));
            long stepSum = mWeekStepSum + mCurrentSteps;
            mTodayStepsView.setText(mCurrentSteps + "");

            mTodayCostTimeView.setText(getString(R.string.today_cost_time_place_holder, mActiveSecond));

            //跑步热量（kcal）＝体重（kg）×运动时间（小时）×指数K
            //指数K＝30÷速度（分钟/400米）
            //例如：某人体重60公斤，长跑1小时，速度是3分钟/400米或8公里/小时，那么他跑步过程中消耗的热量＝60×1×30/3=600kcal(千卡)
            //此种计算含盖了运动后由于基础代谢率提高所消耗的一部分热量，也就是运动后体温升高所产生的一部分热量。
            mCostKcal = BODY_WEIGHT * (mActiveSecond * 1.0f / 3600) * mKcalSpeed;
            String format = mDecimalFormat.format(mCostKcal);
            mCostKcal = Double.parseDouble(format);
            KLog.e(mCostKcal);
            mTodayCostCalorieView.setText(getString(R.string.today_cost_calorie_place_holder, format));

            mWeekStepsAmountView.setText(getString(R.string.week_steps_amount_place_holder, stepSum));
            mWeekAverageStepsView.setText(getString(R.string.week_average_steps_place_holder, ((int) (stepSum * 1.0f / 7))));

            mHandler.postDelayed(mRunnable, 1000);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.post(mRunnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunnable);

        mStepDataBean.setActiveSeconds(mActiveSecond);
        mStepDataBean.setSteps(mCurrentSteps);
        mStepDataBean.setCalories(mCostKcal);

        if (mTargetDataBean != null) {
            mTargetDataBean.setReal(mCurrentSteps);
            App.getDaoSession().getTargetDataBeanDao().save(mTargetDataBean);
        }

        App.getDaoSession().getStepDataBeanDao().save(mStepDataBean);
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
