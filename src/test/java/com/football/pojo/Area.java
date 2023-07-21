package com.football.pojo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Area {

    private Integer id;
    private String name;
    private String code;
    private Object flag;
    private Integer parentAreaId;
    private String parentArea;
    private List<Object> childAreas;
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public Area() {
    }

    public Area(Integer id, String name, String code, Object flag, Integer parentAreaId, String parentArea, List<Object> childAreas) {
        super();
        this.id = id;
        this.name = name;
        this.code = code;
        this.flag = flag;
        this.parentAreaId = parentAreaId;
        this.parentArea = parentArea;
        this.childAreas = childAreas;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getFlag() {
        return flag;
    }

    public void setFlag(Object flag) {
        this.flag = flag;
    }

    public Integer getParentAreaId() {
        return parentAreaId;
    }

    public void setParentAreaId(Integer parentAreaId) {
        this.parentAreaId = parentAreaId;
    }

    public String getParentArea() {
        return parentArea;
    }

    public void setParentArea(String parentArea) {
        this.parentArea = parentArea;
    }

    public List<Object> getChildAreas() {
        return childAreas;
    }

    public void setChildAreas(List<Object> childAreas) {
        this.childAreas = childAreas;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }


}
