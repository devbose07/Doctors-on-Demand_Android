package com.globussoft.readydoctors.doctor.models;

/**
 * Created by GLB-217 on 10/10/2015.
 */
public class SymtomsModel {

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String Type;
    String name;
}
