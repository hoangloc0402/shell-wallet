package com.example.shell.shellwallet;

import java.io.Serializable;

/**
 * Created by QuocBao on 07/04/2017.
 */

public class History implements Serializable{
    int id;
    float amount;
    String note;
    int walletId;
    int categoryId;
    int day;
    int month;
    int year;
    public History(int id,float amount, String note, int walletId, int categoryId,int day,int month,int year){
        this.id = id;
        this.amount= amount;
        this.note = note;
        this.walletId = walletId;
        this.categoryId = categoryId;
        this.day = day;
        this.month= month;
        this.year= year;
    }
    public int getId() {
        return id;
    }
    public float getAmount() {
        return amount;
    }

    public String getNote() {
        return note;
    }

    public int getWalletId() {
        return walletId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}
