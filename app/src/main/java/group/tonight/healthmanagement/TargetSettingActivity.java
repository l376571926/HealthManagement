package group.tonight.healthmanagement;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import group.tonight.healthmanagement.dao.TargetDataBeanDao;
import group.tonight.healthmanagement.model.TargetDataBean;
import group.tonight.healthmanagement.model.UserBean;

public class TargetSettingActivity extends BackEnableBaseActivity {

    private EditText mTargetStepsView;
    private UserBean mUserBean;
    private Long mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_setting);

        mTargetStepsView = (EditText) findViewById(R.id.target_steps);

        if (getIntent().hasExtra(LoginActivity.EXTRA_USER)) {
            mUserBean = (UserBean) getIntent().getSerializableExtra(LoginActivity.EXTRA_USER);
            mUserId = mUserBean.getId();
//            List<TargetDataBean> targetDataBeans = mUserBean.getTargetDataBeans();
            // TODO: 2018/6/9 这里getList会报异常
//            if (targetDataBeans != null) {
//                KLog.e(targetDataBeans.size());
//            }
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
                String targetSteps = mTargetStepsView.getText().toString();
                if (TextUtils.isEmpty(targetSteps)) {
                    return;
                }
                Calendar instance = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String formatDate = dateFormat.format(instance.getTime());
                List<TargetDataBean> list = App.getDaoSession()
                        .getTargetDataBeanDao()
                        .queryBuilder()
                        .where(
                                TargetDataBeanDao.Properties.Uid.eq(mUserId),
                                TargetDataBeanDao.Properties.Date.eq(formatDate)
                        )
                        .build()
                        .list();
                TargetDataBean targetDataBean;
                if (list.isEmpty()) {
                    targetDataBean = new TargetDataBean();
                    targetDataBean.setUid(mUserId);

                    targetDataBean.setDate(formatDate);
                    targetDataBean.setTarget(Integer.parseInt(targetSteps));
                } else {
                    targetDataBean = list.get(0);
                    targetDataBean.setTarget(Integer.parseInt(targetSteps));

                }
                App.getDaoSession().getTargetDataBeanDao().save(targetDataBean);

                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
                break;
        }
    }
}
