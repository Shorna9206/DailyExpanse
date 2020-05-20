package com.bitm.dailyexpanse;

public class Expanse {
  private int id;
  private String expnanseType,amount,date,time;
   private byte [] image;

    public Expanse(int id, String expnanseType, String amount, String date, String time,byte [] image) {
        this.id = id;
        this.expnanseType = expnanseType;
        this.amount = amount;
        this.date = date;
        this.time = time;
       this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getExpnanseType() {
        return expnanseType;
    }

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public byte[] getImage() {
        return image;
    }
}
