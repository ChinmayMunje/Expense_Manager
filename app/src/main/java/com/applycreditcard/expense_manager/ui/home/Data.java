package com.applycreditcard.expense_manager.ui.home;

public class Data {
    private  int amount;
    private String category;
    private String date;

    public Data(int amount, String category, String date) {
        this.amount = amount;
        this.category = category;
        this.date = date;
    }
//    public  Data()
//    {
//
//    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
