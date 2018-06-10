package group.tonight.healthmanagement;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import group.tonight.healthmanagement.dao.StepDataBeanDao;
import group.tonight.healthmanagement.dao.TargetDataBeanDao;
import group.tonight.healthmanagement.model.StepDataBean;
import group.tonight.healthmanagement.model.TargetDataBean;

public class MyDAOUtils {
    /**
     * @param userId 用户id
     * @return
     */
    public static TargetDataBean getTodaysTargetDataBean(Long userId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formatDate = dateFormat.format(Calendar.getInstance().getTime());
        return getTargetDataBean(userId, formatDate);
    }

    /**
     * @param userId 用户id
     * @return
     */
    public static StepDataBean getTodaysStepDataBean(Long userId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String formatDate = dateFormat.format(Calendar.getInstance().getTime());
        return getStepDataBean(userId, formatDate);
    }

    /**
     * @param userId 用户id
     * @param date   2018-06-07
     */
    public static TargetDataBean getTargetDataBean(Long userId, String date) {
        return App.getDaoSession()
                .getTargetDataBeanDao()
                .queryBuilder()
                .where(
                        TargetDataBeanDao.Properties.Uid.eq(userId),
                        TargetDataBeanDao.Properties.Date.eq(date)
                )
                .build()
                .unique();
    }

    /**
     * @param userId 用户id
     * @param date   20180607
     * @return
     */
    public static StepDataBean getStepDataBean(Long userId, String date) {
        return App.getDaoSession()
                .getStepDataBeanDao()
                .queryBuilder()
                .where(
                        StepDataBeanDao.Properties.Uid.eq(userId)
                        , StepDataBeanDao.Properties.CreateDate.eq(date)
                )
                .build()
                .unique();
    }
}
