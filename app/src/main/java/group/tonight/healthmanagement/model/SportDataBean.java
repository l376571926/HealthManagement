package group.tonight.healthmanagement.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;

@Entity
public class SportDataBean implements Serializable, MultiItemEntity {

    private static final long serialVersionUID = 6333660950392137451L;
    @Id
    private Long id;
    private Long typeId;
    private String typeName;
    private boolean baseData;
    public Long uid;
    private String createDate;

    private String startTime;
    private String endTime;
    private String mileage;//里程
    private String averageSpeed;//平均速度

    private boolean hasMileage;

    @Transient
    private CharSequence args1;
    @Transient
    private CharSequence args2;
    @Transient
    private CharSequence args3;


    @Generated(hash = 2075931059)
    public SportDataBean(Long id, Long typeId, String typeName, boolean baseData,
                         Long uid, String createDate, String startTime, String endTime,
                         String mileage, String averageSpeed, boolean hasMileage) {
        this.id = id;
        this.typeId = typeId;
        this.typeName = typeName;
        this.baseData = baseData;
        this.uid = uid;
        this.createDate = createDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.mileage = mileage;
        this.averageSpeed = averageSpeed;
        this.hasMileage = hasMileage;
    }

    public SportDataBean(String typeName, boolean hasMileage, boolean baseData) {
        this.typeName = typeName;
        this.hasMileage = hasMileage;
        this.baseData = baseData;
    }

    @Generated(hash = 1154237820)
    public SportDataBean() {
    }


    @Override
    public int getItemType() {
        return TypeStringBean.VIEW_TYPE_SPORT_ADD;
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public Long getTypeId() {
        return this.typeId;
    }


    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }


    public String getTypeName() {
        return this.typeName;
    }


    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }


    public boolean getBaseData() {
        return this.baseData;
    }


    public void setBaseData(boolean baseData) {
        this.baseData = baseData;
    }


    public Long getUid() {
        return this.uid;
    }


    public void setUid(Long uid) {
        this.uid = uid;
    }


    public String getCreateDate() {
        return this.createDate;
    }


    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }


    public String getStartTime() {
        return this.startTime;
    }


    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }


    public String getEndTime() {
        return this.endTime;
    }


    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    public String getMileage() {
        return this.mileage;
    }


    public void setMileage(String mileage) {
        this.mileage = mileage;
    }


    public String getAverageSpeed() {
        return this.averageSpeed;
    }


    public void setAverageSpeed(String averageSpeed) {
        this.averageSpeed = averageSpeed;
    }


    public boolean getHasMileage() {
        return this.hasMileage;
    }


    public void setHasMileage(boolean hasMileage) {
        this.hasMileage = hasMileage;
    }

    public CharSequence getArgs1() {
        return args1;
    }

    public void setArgs1(CharSequence args1) {
        this.args1 = args1;
    }

    public CharSequence getArgs2() {
        return args2;
    }

    public void setArgs2(CharSequence args2) {
        this.args2 = args2;
    }

    public CharSequence getArgs3() {
        return args3;
    }

    public void setArgs3(CharSequence args3) {
        this.args3 = args3;
    }
}
