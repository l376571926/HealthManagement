package group.tonight.healthmanagement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.util.MultiTypeDelegate;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import group.tonight.healthmanagement.dao.HealthDataBeanDao;
import group.tonight.healthmanagement.dao.SportDataBeanDao;
import group.tonight.healthmanagement.model.HealthDataBean;
import group.tonight.healthmanagement.model.SportDataBean;
import group.tonight.healthmanagement.model.TypeStringBean;
import group.tonight.healthmanagement.model.UserBean;

/**
 * 健康信息录入
 */
public class AddHealthInfoActivity extends AppCompatActivity {
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private DecimalFormat mMileageFormat = new DecimalFormat("##.0");
    private UserBean mUserBean;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_health_info);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        if (getIntent().hasExtra(LoginActivity.EXTRA_USER)) {
            mUserBean = (UserBean) getIntent().getSerializableExtra(LoginActivity.EXTRA_USER);

            List<MultiItemEntity> multiItemEntityList = new ArrayList<>();
            //添加运动数据
            multiItemEntityList.add(new TypeStringBean(TypeStringBean.VIEW_TYPE_TITLE, "运动数据"));
            multiItemEntityList.addAll(App.getDaoSession().getSportDataBeanDao().queryBuilder().where(SportDataBeanDao.Properties.BaseData.eq(true)).build().list());
            //添加健康指数
            multiItemEntityList.add(new TypeStringBean(TypeStringBean.VIEW_TYPE_TITLE, "健康指数"));
            multiItemEntityList.addAll(App.getDaoSession().getHealthDataBeanDao().queryBuilder().where(HealthDataBeanDao.Properties.BaseData.eq(true)).build().list());
            //添加提交按钮
            multiItemEntityList.add(new TypeStringBean(TypeStringBean.VIEW_TYPE_COMMIT, ""));

            CustomAdapter customAdapter = new CustomAdapter(multiItemEntityList);
            mRecyclerView.setAdapter(customAdapter);

            customAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                    List<MultiItemEntity> multiItemEntities = ((CustomAdapter) adapter).getData();
//
                    Toast.makeText(AddHealthInfoActivity.this, "录入数据成功", Toast.LENGTH_SHORT).show();
//
                    Long userBeanId = mUserBean.getId();

                    for (int i = 0; i < multiItemEntities.size(); i++) {
                        MultiItemEntity multiItemEntity = multiItemEntities.get(i);
                        if (multiItemEntity instanceof SportDataBean) {
                            SportDataBean sportTypeBean = (SportDataBean) multiItemEntity;
                            CharSequence args1 = sportTypeBean.getArgs1();
                            CharSequence args2 = sportTypeBean.getArgs2();
                            CharSequence args3 = sportTypeBean.getArgs3();

                            SportDataBean sportDataBean = new SportDataBean();
                            if (!TextUtils.isEmpty(args1) && !TextUtils.isEmpty(args2) && !TextUtils.isEmpty(args3)) {
                                sportDataBean.setUid(userBeanId);
                                sportDataBean.setTypeId(sportTypeBean.getId());
                                sportDataBean.setTypeName(sportTypeBean.getTypeName());
                                boolean hasMileage = sportTypeBean.getHasMileage();
                                sportDataBean.setHasMileage(hasMileage);
                                try {
                                    sportDataBean.setStartTime(args1.toString());
                                    sportDataBean.setEndTime(args2.toString());
                                    sportDataBean.setCreateDate(mDateFormat.format(new Date(System.currentTimeMillis())));
                                    if (hasMileage) {
                                        double averageSpeed = (Long.parseLong(args3.toString()) * 1.0f / 1000 * 1.0f) / ((mSimpleDateFormat.parse(args2.toString()).getTime() - mSimpleDateFormat.parse(args1.toString()).getTime()) * 1.0f / 1000 / 3600);
                                        String format1 = mMileageFormat.format(averageSpeed);
                                        sportDataBean.setMileage(args3.toString());
                                        sportDataBean.setAverageSpeed(format1);
                                    }
                                    App.getDaoSession().getSportDataBeanDao().save(sportDataBean);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else if (multiItemEntity instanceof HealthDataBean) {
                            HealthDataBean healthTypeBean = (HealthDataBean) multiItemEntity;
                            CharSequence args1 = healthTypeBean.getArgs1();

                            HealthDataBean healthDataBean = new HealthDataBean();
                            if (!TextUtils.isEmpty(args1)) {
                                healthDataBean.setUid(userBeanId);
                                healthDataBean.setTypeId(healthTypeBean.getId());
                                healthDataBean.setTypeName(healthTypeBean.getTypeName());
                                healthDataBean.setTypeValue(args1.toString());

                                healthDataBean.setCreateDate(mDateFormat.format(new Date(System.currentTimeMillis())));

                                App.getDaoSession().getHealthDataBeanDao().save(healthDataBean);
                            }
                        }
                    }
                    finish();
                }
            });
        }
    }

    public class CustomAdapter extends BaseQuickAdapter<MultiItemEntity, BaseViewHolder> {

        public CustomAdapter(@Nullable List<MultiItemEntity> data) {
            super(data);
            setMultiTypeDelegate(new MultiTypeDelegate<MultiItemEntity>() {
                @Override
                protected int getItemType(MultiItemEntity multiItemEntity) {
                    return multiItemEntity.getItemType();
                }
            });
            getMultiTypeDelegate()
                    .registerItemType(TypeStringBean.VIEW_TYPE_SPORT_ADD, R.layout.list_item_add_health_info_sport)
                    .registerItemType(TypeStringBean.VIEW_TYPE_TITLE, R.layout.list_item_query_health_info_title)
                    .registerItemType(TypeStringBean.VIEW_TYPE_HEALTH_ADD, R.layout.list_item_add_health_info_health)
                    .registerItemType(TypeStringBean.VIEW_TYPE_COMMIT, R.layout.list_item_add_health_info_commit);
        }

        @Override
        protected void convert(final BaseViewHolder helper, MultiItemEntity item) {
            switch (item.getItemType()) {
                //运动数据
                case TypeStringBean.VIEW_TYPE_SPORT_ADD:
                    SportDataBean sportTypeBean = (SportDataBean) item;
                    helper.setText(R.id.type, sportTypeBean.getTypeName());

                    helper.setText(R.id.start_time, sportTypeBean.getArgs1());
                    EditText startTimeView = (EditText) helper.getView(R.id.start_time);
                    startTimeView.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            SportDataBean bean = ((SportDataBean) getItem(helper.getAdapterPosition()));
                            if (bean == null) {
                                return;
                            }
                            bean.setArgs1(s.toString());
                        }
                    });
                    helper.setText(R.id.end_time, sportTypeBean.getArgs2());
                    EditText endTimeView = (EditText) helper.getView(R.id.end_time);
                    endTimeView.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            SportDataBean bean = ((SportDataBean) getItem(helper.getAdapterPosition()));
                            if (bean == null) {
                                return;
                            }
                            bean.setArgs2(s.toString());
                        }
                    });
                    helper.setText(R.id.mileage, sportTypeBean.getArgs3());
                    EditText mileageView = (EditText) helper.getView(R.id.mileage);
                    mileageView.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            SportDataBean bean = ((SportDataBean) getItem(helper.getAdapterPosition()));
                            if (bean == null) {
                                return;
                            }
                            bean.setArgs3(s.toString());
                        }
                    });

                    View view = helper.getView(R.id.mileage_vg);
                    if (sportTypeBean.getHasMileage()) {
                        view.setVisibility(View.VISIBLE);
                    } else {
                        view.setVisibility(View.GONE);
                    }
                    break;
                case TypeStringBean.VIEW_TYPE_TITLE:
                    TypeStringBean item1 = (TypeStringBean) item;
                    if (helper.getView(R.id.type) != null) {
                        helper.setText(R.id.type, item1.getName());
                    }
                    break;
                //健康指数
                case TypeStringBean.VIEW_TYPE_HEALTH_ADD:
                    HealthDataBean healthTypeBean = (HealthDataBean) item;
                    helper.setText(R.id.type, healthTypeBean.getTypeName());

                    helper.setText(R.id.contents, healthTypeBean.getArgs1());
                    EditText editText = (EditText) helper.getView(R.id.contents);
                    editText.setHint(healthTypeBean.getHint());
                    editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            HealthDataBean bean = ((HealthDataBean) getItem(helper.getAdapterPosition()));
                            if (bean == null) {
                                return;
                            }
                            bean.setArgs1(s.toString());
                        }
                    });
                    break;
                case TypeStringBean.VIEW_TYPE_COMMIT:
                    helper.addOnClickListener(R.id.commit);
                    break;
                default:
                    break;
            }
        }
    }
}
