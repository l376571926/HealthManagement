package group.tonight.healthmanagement;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import group.tonight.healthmanagement.dao.HealthDataBeanDao;
import group.tonight.healthmanagement.model.HealthDataBean;

/**
 * 健康信息查询结果
 */
public class HealthInfoHistoryActivity extends BackEnableBaseActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_info_history);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mBaseQuickAdapter);

        if (getIntent().hasExtra("UserBeanId")) {
            Long userBeanId = getIntent().getLongExtra("UserBeanId", 0L);
            if (userBeanId != 0) {
                HealthDataBean healthDataBean = (HealthDataBean) getIntent().getSerializableExtra("bean");
                setTitle(getString(R.string.query_result_title_place_holder, healthDataBean.getTypeName()));
                List<HealthDataBean> dataBeanList = App.getDaoSession()
                        .getHealthDataBeanDao()
                        .queryBuilder()
                        .where(
                                HealthDataBeanDao.Properties.Uid.eq(userBeanId),
                                HealthDataBeanDao.Properties.BaseData.eq(false),
                                HealthDataBeanDao.Properties.TypeId.eq(healthDataBean.getId())
                        )
                        .build()
                        .list();
                mBaseQuickAdapter.setNewData(dataBeanList);
            }
        }
    }

    private BaseQuickAdapter<HealthDataBean, BaseViewHolder> mBaseQuickAdapter = new BaseQuickAdapter<HealthDataBean, BaseViewHolder>(R.layout.list_item_health_info_detail_history) {
        @Override
        protected void convert(BaseViewHolder helper, HealthDataBean item) {
            if (item.getTypeId() == 1) {
                helper.setText(R.id.value, getString(R.string.height_place_holder, item.getTypeValue()));
            } else if (item.getTypeId() == 2) {
                helper.setText(R.id.value, getString(R.string.weight_place_holder, item.getTypeValue()));
            } else {
                helper.setText(R.id.value, String.valueOf(item.getTypeValue()));
            }
            helper.setText(R.id.date, getString(R.string.record_date_place_holder, item.getCreateDate()));
        }
    };
}
