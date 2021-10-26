package com.example.factoryapplication.entity.scdj;

import java.util.List;

public class JjdGzdEntity {
    private List<JjdItemEntity> jjdhList ;

    private List<GzdItemEntity> gzdhList ;

    public List<JjdItemEntity> getJjdhList() {
        return jjdhList;
    }

    public void setJjdhList(List<JjdItemEntity> jjdhList) {
        this.jjdhList = jjdhList;
    }

    public List<GzdItemEntity> getGzdhList() {
        return gzdhList;
    }

    public void setGzdhList(List<GzdItemEntity> gzdhList) {
        this.gzdhList = gzdhList;
    }
}
