package controller;

import model.Order;
import model.OrderDetails;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderCrudController {
    public boolean saveOrder(Order order) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO `Order` VALUES(?,?,?,?)",
                order.getOrderId(), order.getCustomerID(), order.getDate(), order.getTime());
    }

    public boolean saveOrderDetails(ArrayList<OrderDetails> details) throws SQLException, ClassNotFoundException {
        for (OrderDetails det : details
        ) {

            boolean isDetailsSaved = CrudUtil.execute("INSERT INTO `Order Details` VALUES(?,?,?,?,?)",
                    det.getOrderId(), det.getItemCode(), det.getOrderQty(), det.getDiscount(), det.getPrice());
            if (isDetailsSaved) {
                if (!updateQty(det.getItemCode(), (int) det.getOrderQty())) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean updateQty(String itemCode, int qty) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE ITEM SET QtyOnHand=QtyOnHand-? WHERE ItemCode = ?", qty, itemCode);
    }

    public String getOrderId() throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.execute("SELECT OrderID FROM  `Order` ORDER BY OrderID DESC LIMIT 1");
        if (set.next()) {
            int tempId = Integer.parseInt(set.getString(1).split("-")[1]);

            tempId = tempId + 1;
            if (tempId <= 9) {
                return "O-00" + tempId;
            } else if (tempId <= 99) {
                return "O-0" + tempId;
            } else {
                return "O-" + tempId;
            }
        } else {
            return "O-001";
        }

    }
}