package model;

public class item {
    String code;
    String Discription;
    String pack;
    double price;
    double qtyOnHand;
    String ItemDate;

    public item() {
    }

    public item(String code, String discription, String pack, double price, double qtyOnHand, String itemDate) {
        this.code = code;
        Discription = discription;
        this.pack = pack;
        this.price = price;
        this.qtyOnHand = qtyOnHand;
        ItemDate = itemDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDiscription() {
        return Discription;
    }

    public void setDiscription(String discription) {
        Discription = discription;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(double qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public String getItemDate() {
        return ItemDate;
    }

    public void setItemDate(String itemDate) {
        ItemDate = itemDate;
    }

    @Override
    public String toString() {
        return "item{" +
                "code='" + code + '\'' +
                ", Discription='" + Discription + '\'' +
                ", pack='" + pack + '\'' +
                ", price=" + price +
                ", qtyOnHand=" + qtyOnHand +
                ", ItemDate='" + ItemDate + '\'' +
                '}';
    }
}
