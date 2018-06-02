package group.tonight.healthmanagement.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class TypeStringBean implements MultiItemEntity {
    public static final int VIEW_TYPE_TITLE = 1;
    public static final int VIEW_TYPE_INFO = 0;
    public static final int VIEW_TYPE_HEALTH_ADD = 2;
    public static final int VIEW_TYPE_COMMIT = 3;
    public static final int VIEW_TYPE_SPORT_ADD = 4;

    private int viewType;
    private String name;
    private int type;
    private CharSequence args1;
    private CharSequence args2;
    private CharSequence args3;

    public TypeStringBean(int viewType, String name) {
        this.viewType = viewType;
        this.name = name;
    }

    public TypeStringBean(int viewType, String name, int type) {
        this.viewType = viewType;
        this.name = name;
        this.type = type;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    @Override
    public int getItemType() {
        return viewType;
    }
}