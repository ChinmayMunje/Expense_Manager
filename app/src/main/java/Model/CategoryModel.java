package Model;

public class CategoryModel {
    private int imageUrl;
    private String categoryName;

    public CategoryModel(int imageUrl, String categoryName) {
        this.imageUrl = imageUrl;
        this.categoryName = categoryName;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
