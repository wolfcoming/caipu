package com.infoholdcity.baselibrary.view.addressSelectView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2017/9/5.
 */

public class Area implements Serializable {
    private String id;
    private String type;
    private String name;
    private Object value;
    private List<Area> sublistArea = new ArrayList<>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public List<Area> getSublistArea() {
        return sublistArea;
    }

    public void setSublistArea(List<Area> sublistArea) {
        this.sublistArea = sublistArea;
    }
}
