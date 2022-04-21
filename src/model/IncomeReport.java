package model;

import java.util.ArrayList;

public class IncomeReport {
    private String date;
    private int numberOfOrders;
    private int numberOfSoldItem;
    private double finalCost;


    public IncomeReport(String date, int countOrderId, int numberOfSoldItem, double sumOfTotal) {
        this.date = date;
        this.numberOfOrders = countOrderId;
        this.numberOfSoldItem = numberOfSoldItem;
        this.finalCost = sumOfTotal;
    }


    public IncomeReport(String date, int numberOfSoldItem) {
        this.date = date;
        this.numberOfSoldItem = numberOfSoldItem;
    }

    public IncomeReport(String date, int numberOfOrders, int numberOfSoldItem, double finalCost, ArrayList<IncomeReport> s) {
        this.date = date;
        this.numberOfOrders = numberOfOrders;
        this.numberOfSoldItem = numberOfSoldItem;
        this.finalCost = finalCost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(int numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    public int getNumberOfSoldItem() {
        return numberOfSoldItem;
    }

    public void setNumberOfSoldItem(int numberOfSoldItem) {
        this.numberOfSoldItem = numberOfSoldItem;
    }

    public double getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(double finalCost) {
        this.finalCost = finalCost;
    }

    @Override
    public String toString() {
        return "IncomeReports{" +
                "date=" + date +
                ", numberOfOrders=" + numberOfOrders +
                ", numberOfSoldItem=" + numberOfSoldItem +
                ", finalCost=" + finalCost +
                '}';
    }
}
