package group.tonight.healthmanagement.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;

@Entity
public class HealthDataBean implements Serializable, MultiItemEntity {
    private static final long serialVersionUID = 7982752223205795704L;

    @Id
    private Long id;
    private Long typeId;
    private String typeName;
    private boolean baseData;
    public Long uid;
    private String createDate;

    private int height;//身高
    private int weight;//体重
    private double vision;//视力
    private String hearing;//听力
    private String bone;//骨质
    private String kidney;//肾脏
    private String limb;//四肢
    private String blood;//血液

    private String typeValue;
    private String hint;

    @Transient
    private CharSequence args1;


    @Generated(hash = 1602739769)
    public HealthDataBean(Long id, Long typeId, String typeName, boolean baseData,
                          Long uid, String createDate, int height, int weight, double vision,
                          String hearing, String bone, String kidney, String limb, String blood,
                          String typeValue, String hint) {
        this.id = id;
        this.typeId = typeId;
        this.typeName = typeName;
        this.baseData = baseData;
        this.uid = uid;
        this.createDate = createDate;
        this.height = height;
        this.weight = weight;
        this.vision = vision;
        this.hearing = hearing;
        this.bone = bone;
        this.kidney = kidney;
        this.limb = limb;
        this.blood = blood;
        this.typeValue = typeValue;
        this.hint = hint;
    }

    public HealthDataBean(String typeName, String hint, boolean baseData) {
        this.typeName = typeName;
        this.hint = hint;
        this.baseData = baseData;
    }

    public HealthDataBean(String typeName, boolean baseData) {
        this.typeName = typeName;
        this.baseData = baseData;
    }

    @Generated(hash = 344396625)
    public HealthDataBean() {
    }


    @Override
    public int getItemType() {
        return TypeStringBean.VIEW_TYPE_HEALTH_ADD;
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


    public int getHeight() {
        return this.height;
    }


    public void setHeight(int height) {
        this.height = height;
    }


    public int getWeight() {
        return this.weight;
    }


    public void setWeight(int weight) {
        this.weight = weight;
    }


    public double getVision() {
        return this.vision;
    }


    public void setVision(double vision) {
        this.vision = vision;
    }


    public String getHearing() {
        return this.hearing;
    }


    public void setHearing(String hearing) {
        this.hearing = hearing;
    }


    public String getBone() {
        return this.bone;
    }


    public void setBone(String bone) {
        this.bone = bone;
    }


    public String getKidney() {
        return this.kidney;
    }


    public void setKidney(String kidney) {
        this.kidney = kidney;
    }


    public String getLimb() {
        return this.limb;
    }


    public void setLimb(String limb) {
        this.limb = limb;
    }


    public String getBlood() {
        return this.blood;
    }


    public void setBlood(String blood) {
        this.blood = blood;
    }


    public String getTypeValue() {
        return this.typeValue;
    }


    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }


    public String getHint() {
        return this.hint;
    }


    public void setHint(String hint) {
        this.hint = hint;
    }

    public CharSequence getArgs1() {
        return args1;
    }

    public void setArgs1(CharSequence args1) {
        this.args1 = args1;
    }
}

