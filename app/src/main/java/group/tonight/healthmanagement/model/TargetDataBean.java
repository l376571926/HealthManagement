package group.tonight.healthmanagement.model;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

import java.io.Serializable;

import group.tonight.healthmanagement.dao.DaoSession;
import group.tonight.healthmanagement.dao.StepDataBeanDao;
import group.tonight.healthmanagement.dao.TargetDataBeanDao;

@Entity
public class TargetDataBean implements Serializable {
    private static final long serialVersionUID = -1146447723085907232L;
    @Id
    private Long id;
    private String date;
    private int target;
    private int real;
    private boolean complete;

    private long createTime;
    private long updateTime;

    private Long uid;
    @Generated(hash = 745056080)
    public TargetDataBean(Long id, String date, int target, int real,
            boolean complete, long createTime, long updateTime, Long uid) {
        this.id = id;
        this.date = date;
        this.target = target;
        this.real = real;
        this.complete = complete;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.uid = uid;
    }

    @Generated(hash = 1513933072)
    public TargetDataBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTarget() {
        return this.target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getReal() {
        return this.real;
    }

    public void setReal(int real) {
        this.real = real;
    }

    public boolean getComplete() {
        return this.complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUid() {
        return this.uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
}
