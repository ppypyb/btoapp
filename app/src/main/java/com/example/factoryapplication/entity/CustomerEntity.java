package com.example.factoryapplication.entity;

import java.util.List;

public class CustomerEntity {
    private List<CustomerEntityItem> data ;

    public List<CustomerEntityItem> getData() {
        return data;
    }

    public void setData(List<CustomerEntityItem> data) {
        this.data = data;
    }
}
