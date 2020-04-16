package com.cxp.tmall.database;

public class PropertyValueDo {
    private Integer id;

    private Integer pid;

    private Integer ptid;

    private String value;

    private PropertyDo propertyDo;

    public PropertyDo getPropertyDo ( ) {
        return propertyDo;
    }

    public void setPropertyDo (PropertyDo propertyDo) {
        this.propertyDo = propertyDo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getPtid() {
        return ptid;
    }

    public void setPtid(Integer ptid) {
        this.ptid = ptid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }
}