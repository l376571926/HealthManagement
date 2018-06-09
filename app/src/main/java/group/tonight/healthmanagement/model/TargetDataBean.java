package group.tonight.healthmanagement.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

@Entity
public class TargetDataBean implements Serializable {
    private static final long serialVersionUID = -1146447723085907232L;
    @Id
    private Long id;
    private String date;
    private int target;
    private int real;
    private boolean complete;

    private Long uid;

    @Generated(hash = 1735468710)
    public TargetDataBean(Long id, String date, int target, int real,
                          boolean complete, Long uid) {
        this.id = id;
        this.date = date;
        this.target = target;
        this.real = real;
        this.complete = complete;
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

    public Long getUid() {
        return this.uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

}
