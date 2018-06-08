package group.tonight.healthmanagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import group.tonight.healthmanagement.dao.StepDataBeanDao;
import group.tonight.healthmanagement.model.StepDataBean;
import group.tonight.healthmanagement.model.UserBean;

/**
 * 历史记录
 */
public class WalkingHistoryActivity extends BackEnableBaseActivity {
    public static SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    private RecyclerView mRecyclerView;
    private List<WalkingDataBean> mWalkingDataBeanList;
    private UserBean mUserBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walking_history);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(mBaseQuickAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mBaseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                new AlertDialog.Builder(view.getContext())
                        .setMessage("删除？")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mWalkingDataBeanList.remove(position);
                                mBaseQuickAdapter.notifyItemRemoved(position);
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });
        if (getIntent().hasExtra(LoginActivity.EXTRA_USER)) {
            mUserBean = (UserBean) getIntent().getSerializableExtra(LoginActivity.EXTRA_USER);
            Long id = mUserBean.getId();

            List<StepDataBean> list = App.getDaoSession().getStepDataBeanDao().queryBuilder()
                    .where(
                            StepDataBeanDao.Properties.Uid.eq(id)
                    )
                    .orderDesc(StepDataBeanDao.Properties.CreateTime)
                    .build()
                    .list();
            if (!list.isEmpty()) {
                mWalkingDataBeanList = new ArrayList<>();
                Calendar calendar = Calendar.getInstance();
                for (StepDataBean stepDataBean : list) {
                    calendar.setTimeInMillis(stepDataBean.getCreateTime());
                    mWalkingDataBeanList.add(new WalkingDataBean(mDateFormat.format(calendar.getTime()), stepDataBean.getSteps(), stepDataBean.getActiveSeconds(), stepDataBean.getCalories()));
                }
//                mWalkingDataBeanList.add(new WalkingDataBean("2015-03-16", 33, 21, 0.3));
//                mWalkingDataBeanList.add(new WalkingDataBean("2015-03-15", 41, 24, 0.3));
//                mWalkingDataBeanList.add(new WalkingDataBean("2015-03-13", 250, 70, 6.0));
//                mWalkingDataBeanList.add(new WalkingDataBean("2015-03-12", 300, 80, 7.0));
//                mWalkingDataBeanList.add(new WalkingDataBean("2015-03-11", 200, 60, 4.0));
                mBaseQuickAdapter.setNewData(mWalkingDataBeanList);
            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_walking_history_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_chart) {
            if (mUserBean != null) {
                Intent intent = new Intent(this, WalkingHistoryChartActivity.class);
                intent.putExtra(LoginActivity.EXTRA_USER, mUserBean);
                startActivity(intent);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private BaseQuickAdapter<WalkingDataBean, BaseViewHolder> mBaseQuickAdapter = new BaseQuickAdapter<WalkingDataBean, BaseViewHolder>(R.layout.list_item_walking_history) {
        @Override
        protected void convert(BaseViewHolder helper, WalkingDataBean item) {
            helper.setText(R.id.date, getString(R.string.date_place_holder, item.getDate()));
            helper.setText(R.id.steps, getString(R.string.step_place_holder, item.getSteps()));
            helper.setText(R.id.time, getString(R.string.active_second_place_holder, item.getTime()));
            helper.setText(R.id.cost, getString(R.string.cost_carorie_place_holder, item.getCost() + ""));
        }
    };

    public static class WalkingDataBean implements Serializable {
        private static final long serialVersionUID = -1898686036700263915L;
        private String date;
        private long steps;
        private long time;
        private double cost;


        public WalkingDataBean(String date, long steps, long time, double cost) {
            this.date = date;
            this.steps = steps;
            this.time = time;
            this.cost = cost;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public long getSteps() {
            return steps;
        }

        public void setSteps(long steps) {
            this.steps = steps;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public double getCost() {
            return cost;
        }

        public void setCost(double cost) {
            this.cost = cost;
        }
    }

}
