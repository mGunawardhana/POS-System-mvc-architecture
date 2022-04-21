package model;

public class customer {
    private String CustomerID;
    private String Title;
    private String C_Name;
    private String C_address;
    private String C_City;
    private String C_Province;
    private String C_Postal_Code;
    private String CustomerDate;
    private String CustomerTime;

    public customer() {
    }

    public customer(String customerID, String title, String c_Name, String c_address, String c_City, String c_Province, String c_Postal_Code, String customerDate, String customerTime) {
        CustomerID = customerID;
        Title = title;
        C_Name = c_Name;
        C_address = c_address;
        C_City = c_City;
        C_Province = c_Province;
        C_Postal_Code = c_Postal_Code;
        CustomerDate = customerDate;
        CustomerTime = customerTime;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getC_Name() {
        return C_Name;
    }

    public void setC_Name(String c_Name) {
        C_Name = c_Name;
    }

    public String getC_address() {
        return C_address;
    }

    public void setC_address(String c_address) {
        C_address = c_address;
    }

    public String getC_City() {
        return C_City;
    }

    public void setC_City(String c_City) {
        C_City = c_City;
    }

    public String getC_Province() {
        return C_Province;
    }

    public void setC_Province(String c_Province) {
        C_Province = c_Province;
    }

    public String getC_Postal_Code() {
        return C_Postal_Code;
    }

    public void setC_Postal_Code(String c_Postal_Code) {
        C_Postal_Code = c_Postal_Code;
    }

    public String getCustomerDate() {
        return CustomerDate;
    }

    public void setCustomerDate(String customerDate) {
        CustomerDate = customerDate;
    }

    public String getCustomerTime() {
        return CustomerTime;
    }

    public void setCustomerTime(String customerTime) {
        CustomerTime = customerTime;
    }

    @Override
    public String toString() {
        return "customer{" +
                "CustomerID='" + CustomerID + '\'' +
                ", Title='" + Title + '\'' +
                ", C_Name='" + C_Name + '\'' +
                ", C_address='" + C_address + '\'' +
                ", C_City='" + C_City + '\'' +
                ", C_Province='" + C_Province + '\'' +
                ", C_Postal_Code='" + C_Postal_Code + '\'' +
                ", CustomerDate='" + CustomerDate + '\'' +
                ", CustomerTime='" + CustomerTime + '\'' +
                '}';
    }
}
