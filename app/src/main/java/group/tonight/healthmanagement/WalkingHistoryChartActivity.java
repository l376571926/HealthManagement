package group.tonight.healthmanagement;

import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import group.tonight.healthmanagement.model.StepDataBean;
import group.tonight.healthmanagement.model.UserBean;

public class WalkingHistoryChartActivity extends BackEnableBaseActivity {
    private LineChart mChart;
    private List<String> mXLabelList;
    private UserBean mUserBean;
    private List<StepDataBean> mStepDataBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walking_history_chart);

        mChart = (LineChart) findViewById(R.id.line_chart);
        mChart.getAxisRight().setEnabled(false);//隐藏右Y轴文字

//        mChart.getAxisLeft().setDrawGridLines(false);//隐藏左Y轴上的水平网格线

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);// 设置X轴的位置
        xAxis.setDrawGridLines(false);//隐藏X轴上的垂直网格线
        xAxis.setLabelCount(7);//设置显示的标签数
        mXLabelList = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            Calendar weekStartCalendar = Calendar.getInstance();
            weekStartCalendar.set(Calendar.DAY_OF_WEEK, i + 1);
            mXLabelList.add(WalkingHistoryActivity.mDateFormat.format(weekStartCalendar.getTime()));
        }
//        Calendar weekEndCalendar = Calendar.getInstance();
//        weekEndCalendar.set(Calendar.DAY_OF_WEEK, 7);
//        long timeInMillis1 = weekEndCalendar.getTimeInMillis();


        if (getIntent().hasExtra(LoginActivity.EXTRA_USER)) {
            mUserBean = (UserBean) getIntent().getSerializableExtra(LoginActivity.EXTRA_USER);
            Long id = mUserBean.getId();

            mStepDataBeanList = PedometerActivity.getWeekStepDataBeanList(id);
//            if (!mStepDataBeanList.isEmpty()) {
//                Calendar instance = Calendar.getInstance();
//                for (StepDataBean stepDataBean : mStepDataBeanList) {
//                    long createTime = stepDataBean.getCreateTime();
//                    instance.setTimeInMillis(createTime);
//                }
            //        mXLabelList.add("2015-03-11");
//        mXLabelList.add("2015-03-12");
//        mXLabelList.add("2015-03-13");
//        mXLabelList.add("2015-03-14");
//        mXLabelList.add("2015-03-15");
//        mXLabelList.add("2015-03-16");
//        mXLabelList.add("2015-03-17");
//        mXLabelList.add("2015-03-18");
//        mXLabelList.add("2015-03-19");
//        mXLabelList.add("2015-03-20");

//        mXLabelList.add("2015-03-21");
//        mXLabelList.add("2015-03-22");
//        mXLabelList.add("2015-03-23");
//        mXLabelList.add("2015-03-24");
//        mXLabelList.add("2015-03-25");
//        mXLabelList.add("2015-03-26");
//        mXLabelList.add("2015-03-27");
//        mXLabelList.add("2015-03-28");
//        mXLabelList.add("2015-03-29");
//        mXLabelList.add("2015-03-30");

//        mXLabelList.add("2015-03-31");

            xAxis.setValueFormatter(new MyXFormatter(mXLabelList.toArray(new String[mXLabelList.size()])));
            setData(300);
//            }

        }
    }

    public class MyXFormatter implements IAxisValueFormatter {

        private String[] mValues;

        public MyXFormatter(String[] values) {
            this.mValues = values;
        }

        private static final String TAG = "MyXFormatter";

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            // "value" represents the position of the label on the axis (x or y)
            Log.d(TAG, "----->getFormattedValue: " + value);
            return mValues[(int) value % mValues.length];
        }
    }

    private void setData(float range) {


        List<Entry> values = new ArrayList<>();
        for (int i = 0; i < mXLabelList.size(); i++) {
            if (i < mStepDataBeanList.size()) {
                StepDataBean stepDataBean = mStepDataBeanList.get(i);

                long steps = stepDataBean.getSteps();
                KLog.e(steps);
                values.add(new Entry(i, steps));
            } else {
                values.add(new Entry(i, 0));
            }
        }
        LineDataSet set1;

//        if (mChart.getData() != null &&
//                mChart.getData().getDataSetCount() > 0) {
//            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
//            set1.setValues(values);
//            mChart.getData().notifyDataChanged();
//            mChart.notifyDataSetChanged();
//        } else {
        // create a dataset and give it a type
        set1 = new LineDataSet(values, "--步数-日期");

//            set1.setDrawIcons(false);

        // set the line to be drawn like this "- - - - - -"
//            set1.enableDashedLine(10f, 5f, 0f);
//            set1.enableDashedHighlightLine(10f, 5f, 0f);
//            set1.setColor(Color.BLACK);
//            set1.setCircleColor(Color.BLACK);
//            set1.setLineWidth(1f);
//            set1.setCircleRadius(3f);
//            set1.setDrawCircleHole(false);
//            set1.setValueTextSize(9f);
//            set1.setDrawFilled(true);
//            set1.setFormLineWidth(1f);
//            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
//            set1.setFormSize(15.f);

        if (Utils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
//                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
//                set1.setFillDrawable(drawable);
        } else {
//                set1.setFillColor(Color.BLACK);
        }

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(dataSets);

        // set data
        mChart.setData(data);

//        }
    }
}
