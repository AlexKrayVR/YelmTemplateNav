package yelm.io.template.fragments.stock.new_items;

public class NewItem {

    int imageUrl;
    String name;
    String description;
    String price;

    public NewItem(int imageUrl, String name, String description, String price) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
