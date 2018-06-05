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
import java.util.ArrayList;
import java.util.List;

import group.tonight.healthmanagement.model.UserBean;

/**
 * 历史记录
 */
public class WalkingHistoryActivity extends BackEnableBaseActivity {

    private RecyclerView mRecyclerView;
    private List<WalkingDataBean> mWalkingDataBeanList;
    private UserBean mUserBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walking_history);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        if (getIntent().hasExtra(LoginActivity.EXTRA_USER)) {
            mUserBean = (UserBean) getIntent().getSerializableExtra(LoginActivity.EXTRA_USER);
            Long id = mUserBean.getId();
        }

        mWalkingDataBeanList = new ArrayList<>();
        mWalkingDataBeanList.add(new WalkingDataBean("2015-03-16", 33, 21, 0.3));
        mWalkingDataBeanList.add(new WalkingDataBean("2015-03-15", 41, 24, 0.3));
        mWalkingDataBeanList.add(new WalkingDataBean("2015-03-13", 250, 70, 6.0));
        mWalkingDataBeanList.add(new WalkingDataBean("2015-03-12", 300, 80, 7.0));
        mWalkingDataBeanList.add(new WalkingDataBean("2015-03-11", 200, 60, 4.0));

        mRecyclerView.setAdapter(mBaseQuickAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mBaseQuickAdapter.setNewData(mWalkingDataBeanList);

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
            helper.setText(R.id.date, "日期：" + item.getDate());
            helper.setText(R.id.steps, "步数：" + item.getSteps());
            helper.setText(R.id.time, "活跃时间：" + item.getTime() + "秒");
            helper.setText(R.id.cost, "消耗能量：" + item.getCost() + "(卡)");
        }
    };

    public static class WalkingDataBean implements Serializable {
        private static final long serialVersionUID = -1898686036700263915L;
        private String date;
        private int steps;
        private long time;
        private double cost;


        public WalkingDataBean(String date, int steps, long time, double cost) {
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

        public int getSteps() {
            return steps;
        }

        public void setSteps(int steps) {
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
