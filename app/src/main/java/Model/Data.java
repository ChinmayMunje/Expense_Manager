package Model;

public class Data {

    private int amount;
    private String date;
    private String category;
    private String id;

    public Data(int amount, String date, String category, String id) {
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
