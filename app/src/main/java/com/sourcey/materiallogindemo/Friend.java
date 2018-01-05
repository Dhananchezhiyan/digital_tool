package com.sourcey.materiallogindemo;

/**
 * Created by Ajay on 28-Nov-17.
 */

public class Friend {
    private String name;
    private int _id;
    String fyn;
    public Friend(String name,int _id,String fyn) {
        // TODO Auto-generated constructor stub
        this.name=name;
        this._id=_id;
        this.fyn = fyn;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int get_id() {
        return _id;
    }
    public void set_id(int _id) {
        this._id = _id;
    }
    public String getFyn() {
        return fyn;
    }
    public void setFyn(String fyn) {
        this.fyn = fyn;
    }
}