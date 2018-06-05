package group.tonight.healthmanagement;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TargetProgressActivity extends BackEnableBaseActivity {

    private RecyclerView mRecyclerView;
    private List<TargetDataBean> mTargetDataBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_progress);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mRecyclerView.setAdapter(mBaseQuickAdapter);

        mTargetDataBeanList = new ArrayList<>();
        mTargetDataBeanList.add(new TargetDataBean("2015-03-19", 2100, 0, false));
        mTargetDataBeanList.add(new TargetDataBean("2015-03-17", 2300, 0, false));
        mTargetDataBeanList.add(new TargetDataBean("2015-03-15", 25, 41, true));

        mBaseQuickAdapter.setNewData(mTargetDataBeanList);
        mBaseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                //修改，删除功能
                new AlertDialog.Builder(view.getContext())
                        .setTitle("选择操作")
                        .setPositiveButton("修改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(TargetProgressActivity.this, "修改", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mTargetDataBeanList.remove(position);
                                mBaseQuickAdapter.notifyItemRemoved(position);
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_target_progress_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_setting) {
            Toast.makeText(TargetProgressActivity.this, "设置", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private BaseQuickAdapter<TargetDataBean, BaseViewHolder> mBaseQuickAdapter = new BaseQuickAdapter<TargetDataBean, BaseViewHolder>(R.layout.list_item_target_progress) {
        @Override
        protected void convert(BaseViewHolder helper, TargetDataBean item) {
            helper.setText(R.id.date, getString(R.string.date_place_holder, item.getDate()));
            helper.setText(R.id.target, getString(R.string.target_place_holder, item.getTarget()));
            helper.setText(R.id.real, item.getReal() + "");
            helper.setText(R.id.status, item.isComplete() + "");
        }
    };

    public static class TargetDataBean implements Serializable {
        private static final long serialVersionUID = -1358693082434009891L;
        private String date;
        private int target;
        private int real;
        private boolean complete;

        public TargetDataBean() {
        }

        public TargetDataBean(String date, int target, int real, boolean complete) {
            this.date = date;
            this.target = target;
            this.real = real;
            this.complete = complete;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getTarget() {
            return target;
        }

        public void setTarget(int target) {
            this.target = target;
        }

        public int getReal() {
            return real;
        }

        public void setReal(int real) {
            this.real = real;
        }

        public boolean isComplete() {
            return complete;
        }

        public void setComplete(boolean complete) {
            this.complete = complete;
        }
    }
}
