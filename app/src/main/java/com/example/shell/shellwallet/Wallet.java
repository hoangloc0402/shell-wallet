package com.example.shell.shellwallet;

import java.io.Serializable;

/**
 * Created by QuocBao on 06/04/2017.
 */

public class Wallet implements Serializable{
    public int getId() {
        return id;
    }

    private int id;
    private String name;
    private String currency;

    public float getInitbalance() {
        return initbalance;
    }

    private float initbalance;
    private int icon;
    public Wallet(int id,String name, String currency,float initbalance,int icon ){
        this.id = id;
        this.name = name;
        this.currency = currency;
        this.initbalance = initbalance;
        this.icon = icon;
    }
    public String getName() {
        return name;
    }
    public int getIcon() {
        return icon;
    }
    public String getCurrency() {
        return currency;
    }
}
