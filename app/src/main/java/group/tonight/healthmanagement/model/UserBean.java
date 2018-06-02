package group.tonight.healthmanagement.model;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.io.Serializable;
import java.util.List;

import group.tonight.healthmanagement.dao.DaoSession;
import group.tonight.healthmanagement.dao.HealthDataBeanDao;
import group.tonight.healthmanagement.dao.SportDataBeanDao;
import group.tonight.healthmanagement.dao.UserBeanDao;

@Entity
public class UserBean implements Serializable {
    private static final long serialVersionUID = -3911532749783028626L;
    @Id
    private Long id;
    private Long userId;
    private String userName;
    private String gender;
    private String birthday;
    private String password;

    @ToMany(referencedJoinProperty = "uid")
    private List<HealthDataBean> healthDataBeans;

    @ToMany(referencedJoinProperty = "uid")
    private List<SportDataBean> sportDataBeans;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 83707551)
    private transient UserBeanDao myDao;

    @Generated(hash = 1542243779)
    public UserBean(Long id, Long userId, String userName, String gender,
            String birthday, String password) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.gender = gender;
        this.birthday = birthday;
        this.password = password;
    }

    @Generated(hash = 1203313951)
    public UserBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 91548952)
    public List<HealthDataBean> getHealthDataBeans() {
        if (healthDataBeans == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            HealthDataBeanDao targetDao = daoSession.getHealthDataBeanDao();
            List<HealthDataBean> healthDataBeansNew = targetDao
                    ._queryUserBean_HealthDataBeans(id);
            synchronized (this) {
                if (healthDataBeans == null) {
                    healthDataBeans = healthDataBeansNew;
                }
            }
        }
        return healthDataBeans;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1311127142)
    public synchronized void resetHealthDataBeans() {
        healthDataBeans = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1184788552)
    public List<SportDataBean> getSportDataBeans() {
        if (sportDataBeans == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            SportDataBeanDao targetDao = daoSession.getSportDataBeanDao();
            List<SportDataBean> sportDataBeansNew = targetDao
                    ._queryUserBean_SportDataBeans(id);
            synchronized (this) {
                if (sportDataBeans == null) {
                    sportDataBeans = sportDataBeansNew;
                }
            }
        }
        return sportDataBeans;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1231188277)
    public synchronized void resetSportDataBeans() {
        sportDataBeans = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1491512534)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserBeanDao() : null;
    }

}
