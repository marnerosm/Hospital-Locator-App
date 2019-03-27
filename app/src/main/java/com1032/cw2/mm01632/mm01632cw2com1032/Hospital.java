package com1032.cw2.mm01632.mm01632cw2com1032;


import android.database.Cursor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marne on 18/05/2018.
 */

public class Hospital implements Serializable{
    private String name;
    private String city;
    private String type;


    /**
     * Constructor
     * @param name
     * @param city
     * @param type
     */
    public Hospital(String name,String city,String type){
        super();
        this.name=name;
        this.city=city;
        this.type=type;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }








}
