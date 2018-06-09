package group.tonight.healthmanagement;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import group.tonight.healthmanagement.dao.TargetDataBeanDao;
import group.tonight.healthmanagement.model.TargetDataBean;
import group.tonight.healthmanagement.model.UserBean;

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

        if (getIntent().hasExtra(LoginActivity.EXTRA_USER)) {
            UserBean userBean = (UserBean) getIntent().getSerializableExtra(LoginActivity.EXTRA_USER);
            Long id = userBean.getId();
            List<TargetDataBean> list = App.getDaoSession()
                    .getTargetDataBeanDao()
                    .queryBuilder()
                    .where(TargetDataBeanDao.Properties.Uid.eq(id))
                    .build()
                    .list();
            if (!list.isEmpty()) {
                mTargetDataBeanList.addAll(list);
                mBaseQuickAdapter.setNewData(mTargetDataBeanList);
            }
        }
//        mTargetDataBeanList.add(new TargetDataBean("2015-03-19", 2100, 0, false));
//        mTargetDataBeanList.add(new TargetDataBean("2015-03-17", 2300, 0, false));
//        mTargetDataBeanList.add(new TargetDataBean("2015-03-15", 25, 41, true));
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
                                App.getDaoSession().getTargetDataBeanDao().delete(mTargetDataBeanList.get(position));
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
            helper.setText(R.id.real, getString(R.string.real_steps_place_holder, item.getReal()));
            helper.setText(R.id.status, getString(R.string.target_steps_complete_status_place_holder, item.getComplete() ? "已完成" : "未完成"));
            helper.setTextColor(R.id.status, item.getComplete() ? ContextCompat.getColor(TargetProgressActivity.this, android.R.color.holo_green_dark) : ContextCompat.getColor(TargetProgressActivity.this, android.R.color.holo_red_dark));
        }
    };
}
