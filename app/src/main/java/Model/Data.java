package Model;

public class Data {

    private String amount;
    private String date;
    private String category;
    private String parentCategory;
    private int imgid;


    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }

    public Data() {
    }


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
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

    public String getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(String parentCategory) {
        this.parentCategory = parentCategory;
    }


    public Data(String amount, String date, String category, String parentCategory) {
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.parentCategory = parentCategory;
    }
}
