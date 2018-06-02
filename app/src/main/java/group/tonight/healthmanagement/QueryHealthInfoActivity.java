package group.tonight.healthmanagement;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import group.tonight.healthmanagement.dao.HealthDataBeanDao;
import group.tonight.healthmanagement.dao.SportDataBeanDao;
import group.tonight.healthmanagement.model.HealthDataBean;
import group.tonight.healthmanagement.model.SportDataBean;
import group.tonight.healthmanagement.model.TypeStringBean;
import group.tonight.healthmanagement.model.UserBean;

/**
 * 健康信息查询
 */
public class QueryHealthInfoActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CustomAdapter mCustomAdapter;
    private Long mUserBeanId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_health_info);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        List<MultiItemEntity> typeStringBeanList = new ArrayList<>();

        typeStringBeanList.add(new TypeStringBean(TypeStringBean.VIEW_TYPE_TITLE, "运动数据"));
        typeStringBeanList.addAll(App.getDaoSession().getSportDataBeanDao().queryBuilder().where(SportDataBeanDao.Properties.BaseData.eq(true)).build().list());

        typeStringBeanList.add(new TypeStringBean(TypeStringBean.VIEW_TYPE_TITLE, "健康指数"));
        typeStringBeanList.addAll(App.getDaoSession().getHealthDataBeanDao().queryBuilder().where(HealthDataBeanDao.Properties.BaseData.eq(true)).build().list());

        mCustomAdapter = new CustomAdapter();
        mCustomAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                CustomAdapter customAdapter = (CustomAdapter) adapter;
                MultiItemEntity multiItemEntity = customAdapter.getItem(position);
                if (multiItemEntity instanceof SportDataBean) {
                    SportDataBean sportDataBean = (SportDataBean) multiItemEntity;
                    final Long id = sportDataBean.getId();
                    DatePickerDialog datePickerDialog = new DatePickerDialog(QueryHealthInfoActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            KLog.e(year + " " + month + " " + dayOfMonth);
                            String monthStr = (month + 1) + "";
                            if (monthStr.length() == 1) {
                                monthStr = "0" + (month + 1);
                            }
                            String dayOfMonthStr = dayOfMonth + "";
                            if (dayOfMonthStr.length() == 1) {
                                dayOfMonthStr = "0" + dayOfMonth;
                            }
                            String createDate = year + "" + monthStr + "" + dayOfMonthStr;

                            List<SportDataBean> list = App.getDaoSession()
                                    .getSportDataBeanDao()
                                    .queryBuilder()
                                    .where(
                                            SportDataBeanDao.Properties.Uid.eq(mUserBeanId),
                                            SportDataBeanDao.Properties.BaseData.eq(false),

                                            SportDataBeanDao.Properties.CreateDate.eq(createDate),
                                            SportDataBeanDao.Properties.TypeId.eq(id)
                                    )
                                    .build()
                                    .list();
                            if (list == null || list.isEmpty()) {
                                Toast.makeText(QueryHealthInfoActivity.this, "没有相关信息", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            SportDataBean sportDataBean = list.get(0);

                            List<String> stringList = new ArrayList<>();
                            String eee = sportDataBean.getTypeName();
                            String aaa = "开始时间：" + sportDataBean.getStartTime();
                            String bbb = "结束时间：" + sportDataBean.getEndTime();
                            stringList.add(eee);
                            stringList.add(aaa);
                            stringList.add(bbb);
                            if (sportDataBean.getHasMileage()) {
                                String ccc = "里程：" + sportDataBean.getMileage() + "m";
                                String ddd = "平均速度：" + sportDataBean.getAverageSpeed() + "km/h";
                                stringList.add(ccc);
                                stringList.add(ddd);
                            }


                            RecyclerView recyclerView = new RecyclerView(QueryHealthInfoActivity.this);
                            recyclerView.setLayoutManager(new LinearLayoutManager(QueryHealthInfoActivity.this));
                            recyclerView.addItemDecoration(new DividerItemDecoration(QueryHealthInfoActivity.this, DividerItemDecoration.VERTICAL));
                            BaseQuickAdapter<String, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(android.R.layout.simple_list_item_1) {
                                @Override
                                protected void convert(BaseViewHolder helper, String item) {
                                    helper.setText(android.R.id.text1, item);
                                }
                            };
                            recyclerView.setAdapter(baseQuickAdapter);
                            baseQuickAdapter.setNewData(stringList);
                            new AlertDialog
                                    .Builder(QueryHealthInfoActivity.this)
                                    .setTitle("查询结果信息")
                                    .setView(recyclerView)
                                    .show();

                        }
                    }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.show();
                } else if (multiItemEntity instanceof HealthDataBean) {
                    HealthDataBean healthDataBean = (HealthDataBean) multiItemEntity;
                    List<HealthDataBean> list = App.getDaoSession()
                            .getHealthDataBeanDao()
                            .queryBuilder()
                            .where(
                                    HealthDataBeanDao.Properties.Uid.eq(mUserBeanId),
                                    HealthDataBeanDao.Properties.BaseData.eq(false),

                                    HealthDataBeanDao.Properties.TypeId.eq(healthDataBean.getId())
                            )
                            .build()
                            .list();
                    if (list == null || list.isEmpty()) {
                        Toast.makeText(QueryHealthInfoActivity.this, "没有相关信息", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent = new Intent(QueryHealthInfoActivity.this, HealthInfoHistoryActivity.class);
                    intent.putExtra("bean", healthDataBean);
                    startActivity(intent);
                }
            }
        });
        mRecyclerView.setAdapter(mCustomAdapter);
        mCustomAdapter.setNewData(typeStringBeanList);

        if (getIntent().hasExtra(LoginActivity.EXTRA_USER)) {
            UserBean userBean = (UserBean) getIntent().getSerializableExtra(LoginActivity.EXTRA_USER);
            mUserBeanId = userBean.getId();
        }
    }

    public class CustomAdapter extends BaseQuickAdapter<MultiItemEntity, BaseViewHolder> {
        public CustomAdapter() {
            super(null);
            setMultiTypeDelegate(new MultiTypeDelegate<MultiItemEntity>() {
                @Override
                protected int getItemType(MultiItemEntity multiItemEntity) {
                    return multiItemEntity.getItemType();
                }
            });
            getMultiTypeDelegate()
                    .registerItemType(TypeStringBean.VIEW_TYPE_SPORT_ADD, R.layout.list_item_query_health_info)
                    .registerItemType(TypeStringBean.VIEW_TYPE_HEALTH_ADD, R.layout.list_item_query_health_info)
                    .registerItemType(TypeStringBean.VIEW_TYPE_TITLE, R.layout.list_item_query_health_info_title);
        }

        @Override
        protected void convert(BaseViewHolder helper, MultiItemEntity item) {
            switch (item.getItemType()) {
                case TypeStringBean.VIEW_TYPE_SPORT_ADD:
                    helper.setText(R.id.type, ((SportDataBean) item).getTypeName());
                    helper.addOnClickListener(R.id.query);
                    break;
                case TypeStringBean.VIEW_TYPE_HEALTH_ADD:
                    helper.setText(R.id.type, ((HealthDataBean) item).getTypeName());
                    helper.addOnClickListener(R.id.query);
                    break;
                case TypeStringBean.VIEW_TYPE_TITLE:
                    helper.setText(R.id.type, ((TypeStringBean) item).getName());
                    break;
                default:
                    break;
            }
        }
    }
}
