package group.tonight.healthmanagement;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class TargetSettingActivity extends AppCompatActivity {

    private EditText mTargetStepsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_setting);

        mTargetStepsView = (EditText) findViewById(R.id.target_steps);
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
                String string = mTargetStepsView.getText().toString();
                if (TextUtils.isEmpty(string)) {
                    return;
                }
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
                break;
        }
    }
}
