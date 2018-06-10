package group.tonight.healthmanagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import group.tonight.healthmanagement.model.TargetDataBean;
import group.tonight.healthmanagement.model.UserBean;

public class TargetProgressActivity extends BackEnableBaseActivity {

    private RecyclerView mRecyclerView;
    private UserBean mUserBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_progress);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mRecyclerView.setAdapter(mBaseQuickAdapter);

        if (getIntent().hasExtra(LoginActivity.EXTRA_USER)) {
            mUserBean = (UserBean) getIntent().getSerializableExtra(LoginActivity.EXTRA_USER);

        }
        mBaseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                final TargetDataBean targetDataBean = mBaseQuickAdapter.getItem(position);
                //修改，删除功能
                new AlertDialog.Builder(view.getContext())
                        .setTitle("选择操作")
                        .setPositiveButton("修改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent targetSettingIntent = new Intent(TargetProgressActivity.this, TargetSettingActivity.class);
                                targetSettingIntent.putExtra(LoginActivity.EXTRA_USER, mUserBean);
                                targetSettingIntent.putExtra("modify",targetDataBean);
                                startActivity(targetSettingIntent);
                            }
                        })
                        .setNegativeButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                App.getDaoSession().getTargetDataBeanDao().delete(targetDataBean);
                                mBaseQuickAdapter.remove(position);
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mUserBean != null) {
            mUserBean.__setDaoSession(App.getDaoSession());
            List<TargetDataBean> targetDataBeans = mUserBean.getTargetDataBeans();
            mUserBean.resetTargetDataBeans();
            Collections.sort(targetDataBeans, new Comparator<TargetDataBean>() {
                @Override
                public int compare(TargetDataBean o1, TargetDataBean o2) {
                    long createTime = o1.getCreateTime();
                    long createTime1 = o2.getCreateTime();
                    if (createTime > createTime1) {
                        return -1;
                    } else if (createTime < createTime1) {
                        return 1;
                    }
                    return 0;
                }
            });
            if (!targetDataBeans.isEmpty()) {
                mBaseQuickAdapter.replaceData(targetDataBeans);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_target_progress_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_setting) {
            if (mUserBean == null) {
                return true;
            }
            Intent targetSettingIntent = new Intent(this, TargetSettingActivity.class);
            targetSettingIntent.putExtra(LoginActivity.EXTRA_USER, mUserBean);
            startActivity(targetSettingIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private BaseQuickAdapter<TargetDataBean, BaseViewHolder> mBaseQuickAdapter = new BaseQuickAdapter<TargetDataBean, BaseViewHolder>(R.layout.list_item_target_progress) {

        @Override
        protected void convert(BaseViewHolder helper, TargetDataBean item) {
            helper.setText(R.id.date, getString(R.string.date_place_holder, item.getDate()));
            helper.setText(R.id.target, getString(R.string.target_place_holder, item.getTarget()));
            int real = item.getReal();
            helper.setText(R.id.real, getString(R.string.real_steps_place_holder, real));
            helper.setText(R.id.status, getString(R.string.target_steps_complete_status_place_holder, item.getComplete() ? "已完成" : "未完成"));
            helper.setTextColor(R.id.status, item.getComplete() ? ContextCompat.getColor(TargetProgressActivity.this, android.R.color.holo_green_dark) : ContextCompat.getColor(TargetProgressActivity.this, android.R.color.holo_red_dark));
        }
    };
}
