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
    private int activeTime;
    private int calories;

    private int createDate;

    private Long uid;

    @Generated(hash = 920173518)
    public StepDataBean(Long id, int steps, int activeTime, int calories,
            int createDate, Long uid) {
        this.id = id;
        this.steps = steps;
        this.activeTime = activeTime;
        this.calories = calories;
        this.createDate = createDate;
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

    public int getActiveTime() {
        return this.activeTime;
    }

    public void setActiveTime(int activeTime) {
        this.activeTime = activeTime;
    }

    public int getCalories() {
        return this.calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(int createDate) {
        this.createDate = createDate;
    }

    public Long getUid() {
        return this.uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

}
