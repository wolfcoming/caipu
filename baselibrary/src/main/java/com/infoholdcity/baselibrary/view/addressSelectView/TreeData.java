package com.infoholdcity.baselibrary.view.addressSelectView;

import com.infoholdcity.baselibrary.view.addressSelectView.i.ISelectAble;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class TreeData implements Serializable, ISelectAble {
    private String id;
    private String type;
    private String name;
    private Object value;
    private List<TreeData> sublistTreeData = new ArrayList<>();


    public String getId() {
        return id;
    }

    @Override
    public Object getArg() {
        return this;
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

    public List<TreeData> getSublistTreeData() {
        return sublistTreeData;
    }

    public void setSublistTreeData(List<TreeData> sublistTreeData) {
        this.sublistTreeData = sublistTreeData;
    }


    public static TreeData getAreaById(TreeData data, String id) {
        if (id == null || id == "") {
            return data;
        }
        for (TreeData treeData : data.getSublistTreeData()) {
            if (treeData.getId() != id) {
                getAreaById(treeData, id);
            } else {
                return treeData;
            }
        }
        return null;
    }
}
