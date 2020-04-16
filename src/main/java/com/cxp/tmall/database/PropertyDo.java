package com.cxp.tmall.database;

public class PropertyDo {
    private Integer id;

    private Integer cid;

    private String name;

    private CategoryDo category;

    public CategoryDo getCategory ( ) {
        return category;
    }

    public void setCategory (CategoryDo category) {
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}