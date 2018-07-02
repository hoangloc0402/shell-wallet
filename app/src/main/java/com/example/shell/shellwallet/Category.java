package com.example.shell.shellwallet;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by QuocBao on 06/04/2017.
 */

public class Category implements Serializable {
    public int getId() {
        return id;
    }

    private int id;

    public String getName() {
        return name;
    }

    private String name;

    public int getIcon() {
        return icon;
    }

    private int icon;

    public int getFlag() {
        return flag;
    }

    private int flag;
    public Category(int id,String name, int flag, int icon){
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.flag = flag;
    }
}
