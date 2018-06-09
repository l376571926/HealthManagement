package group.tonight.healthmanagement.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

@Entity
public class StepDataBean implements Serializable {
    private static final long serialVersionUID = 8571672091279670118L;
    @Id
    private Long id;

    private int steps;
    private int activeSeconds;
    private double calories;

    private String createDate;
    private long createTime;

    private Long uid;

    @Generated(hash = 1267610559)
    public StepDataBean(Long id, int steps, int activeSeconds, double calories,
            String createDate, long createTime, Long uid) {
        this.id = id;
        this.steps = steps;
        this.activeSeconds = activeSeconds;
        this.calories = calories;
        this.createDate = createDate;
        this.createTime = createTime;
        this.uid = uid;
    }

    @Generated(hash = 568882125)
    public StepDataBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSteps() {
        return this.steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getActiveSeconds() {
        return this.activeSeconds;
    }

    public void setActiveSeconds(int activeSeconds) {
        this.activeSeconds = activeSeconds;
    }

    public double getCalories() {
        return this.calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public String getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public Long getUid() {
        return this.uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    

}
