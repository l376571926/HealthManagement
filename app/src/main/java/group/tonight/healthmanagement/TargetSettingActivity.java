package group.tonight.healthmanagement;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import group.tonight.healthmanagement.model.StepDataBean;
import group.tonight.healthmanagement.model.TargetDataBean;
import group.tonight.healthmanagement.model.UserBean;

public class TargetSettingActivity extends BackEnableBaseActivity {

    private EditText mTargetStepsView;
    private Long mUserId;
    private TextView mSelectDateView;
    private TargetDataBean mTargetDataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_setting);

        mSelectDateView = (TextView) findViewById(R.id.select_date);
        mTargetStepsView = (EditText) findViewById(R.id.target_steps);

        if (getIntent().hasExtra(LoginActivity.EXTRA_USER)) {
            UserBean mUserBean = (UserBean) getIntent().getSerializableExtra(LoginActivity.EXTRA_USER);
            mUserId = mUserBean.getId();
            if (getIntent().hasExtra("modify")) {
                mTargetDataBean = (TargetDataBean) getIntent().getSerializableExtra("modify");
                String date = mTargetDataBean.getDate();
                int target = mTargetDataBean.getTarget();

                mSelectDateView.setText(date);
                mTargetStepsView.setText(target + "");
                mSelectDateView.setEnabled(false);
            }
        }
    }

    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.select_date:
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        String monthStr = (month + 1) + "";
                        if ((month + 1) < 10) {
                            monthStr = "0" + monthStr;
                        }
                        String dayOfMonthStr = dayOfMonth + "";
                        if (dayOfMonth < 10) {
                            dayOfMonthStr = "0" + dayOfMonthStr;
                        }
                        ((TextView) view).setText(year + "-" + monthStr + "-" + dayOfMonthStr);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                break;
            case R.id.commit:
                String dateStr = mSelectDateView.getText().toString();//2018-06-08
                if (dateStr.contains("请")) {
                    Toast.makeText(this, "请选择日期", Toast.LENGTH_SHORT).show();
                    return;
                }
                String targetSteps = mTargetStepsView.getText().toString();
                if (TextUtils.isEmpty(targetSteps)) {
                    Toast.makeText(this, "请输入目标步数", Toast.LENGTH_SHORT).show();
                    return;
                }
                int targetStep = Integer.parseInt(targetSteps);

                if (mTargetDataBean == null) {
                    //查找当前数据库中是否有选中日期的目标目标步数设置数据，有就更新，没有就创建
                    mTargetDataBean = MyDAOUtils.getTargetDataBean(mUserId, dateStr);
                    if (mTargetDataBean == null) {
                        mTargetDataBean = new TargetDataBean();
                        mTargetDataBean.setUid(mUserId);

                        mTargetDataBean.setDate(dateStr);
                        mTargetDataBean.setCreateTime(System.currentTimeMillis());
                    }
                }
                mTargetDataBean.setTarget(targetStep);
                mTargetDataBean.setUpdateTime(System.currentTimeMillis());
                //查找所选日期当天的步数数据，如果有，则对比目标目标步数，并设置是否已完成
                StepDataBean stepDataBean = MyDAOUtils.getStepDataBean(mUserId, dateStr.replace("-", ""));
                if (stepDataBean != null) {
                    int steps = stepDataBean.getSteps();
                    int target = mTargetDataBean.getTarget();
                    mTargetDataBean.setReal(steps);
                    mTargetDataBean.setComplete(steps >= target);
                }
                App.getDaoSession().getTargetDataBeanDao().save(mTargetDataBean);

                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
                break;
        }
    }
}
