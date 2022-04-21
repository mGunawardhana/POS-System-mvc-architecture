package model;

public class cart {
    String OrderID;
    String Code;
    double Qty;
    double UnitPrice;
    double Discount;
    double TotalAmount;

    public cart(String orderID, String code, double qty, double unitPrice, double discount, double totalAmount) {
        OrderID = orderID;
        Code = code;
        Qty = qty;
        UnitPrice = unitPrice;
        Discount = discount;
        TotalAmount = totalAmount;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public double getQty() {
        return Qty;
    }

    public void setQty(double qty) {
        Qty = qty;
    }

    public double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        UnitPrice = unitPrice;
    }

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double discount) {
        Discount = discount;
    }

    public double getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        TotalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "cart{" +
                "OrderID='" + OrderID + '\'' +
                ", Code='" + Code + '\'' +
                ", Qty=" + Qty +
                ", UnitPrice=" + UnitPrice +
                ", Discount=" + Discount +
                ", TotalAmount=" + TotalAmount +
                '}';
    }
}
