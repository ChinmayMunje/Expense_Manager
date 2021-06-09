package com.applycreditcard.expense_manager.ui.home;

public class Data {
    private  String amount;
    private String category;
    private String date;

    public Data(String amount, String category, String date) {
        this.amount = amount;
        this.category = category;
        this.date = date;
    }
    public  Data()
    {

    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
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
