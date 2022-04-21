DROP DATABASE IF EXISTS fruitmarket;
CREATE DATABASE IF NOT EXISTS fruitmarket;
SHOW DATABASES ;
USE fruitmarket;

#--------------------------------------------- ITEM TABLE -----------------------------------------------------------

DROP TABLE IF EXISTS Item;
CREATE TABLE IF NOT EXISTS Item(
    ItemCode        VARCHAR (6),
    Description     VARCHAR (50),
    PackSize        VARCHAR (20),
    UnitPrice       DOUBLE DEFAULT 0.00,
    QtyOnHand       DOUBLE DEFAULT 0.00,
    ItemDate        DATE,
    CONSTRAINT      PRIMARY KEY (ItemCode)
    );

INSERT INTO Item VALUES('I-001','Mango','small/medium',60.0,1000,'2022-03-19');
INSERT INTO Item VALUES('I-002','Orange','small/medium',80.0,1000,'2022-03-19');
INSERT INTO Item VALUES('I-003','pearce','small/medium',100.0,1000,'2022-03-19');
INSERT INTO Item VALUES('I-004','Strawberry','small/medium',20.0,1000,'2022-03-19');
INSERT INTO Item VALUES('I-005','Berry','small/medium',120.0,1000,'2022-03-19');



SHOW TABLES ;
DESCRIBE Item;

#------------------- CUSTOMER TABLE ------------------------------------------------------------------------------------

DROP TABLE IF EXISTS Customer;
CREATE TABLE Customer
(
    CustomerID VARCHAR (6),
    CustomerTitle VARCHAR (5),
    CustomerName VARCHAR (30),
    CustomerAddress VARCHAR (30),
    City VARCHAR (20),
    Province VARCHAR (20),
    PostCode VARCHAR (9),
    CustomerDate DATE,
    CustomerTime TIME,
    CONSTRAINT PRIMARY KEY (CustomerID)

);

INSERT INTO Customer VALUES('C001','mr','Danapala','Panadura','Panadura','SP',8000,'2021-09-10','21:12:12');
INSERT INTO Customer VALUES('C002','mr','SugathaPala','Mahava','Mahawa','WP',8000,'2021-09-10','21:12:12');



SHOW TABLES ;
DESCRIBE Customer;

#---------------------------------- EMPLOYEE TABLE ---------------------------------------------------------------------

DROP TABLE IF EXISTS Cashier;
CREATE TABLE Cashier
(
    CashierID          VARCHAR(6),
    CashierName        VARCHAR(30),
    CashierPsw         VARCHAR(15),
    CashierCNumber	   VARCHAR(15),
    CashierNIC         VARCHAR(15),
    CashierSalary      DOUBLE,
    CONSTRAINT PRIMARY KEY (CashierId)
);

INSERT INTO Cashier VALUES('C001','Stefan','1234','0719043372','200010501934',50000);
INSERT INTO Cashier VALUES('C002','Verona','1234','0779035642','200010462738',50000);

SHOW TABLES;
DESCRIBE Cashier;




#-------------------------- ORDER TABLE --------------------------------------------------------------------------------

DROP TABLE IF EXISTS `Order`;
CREATE TABLE IF NOT EXISTS `Order`(
    OrderID VARCHAR (6),
    CustomerID VARCHAR (6),
    OrderDate DATE ,
    OrderTime TIME,
    CONSTRAINT PRIMARY KEY (OrderID),
    CONSTRAINT FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID) ON DELETE CASCADE ON UPDATE CASCADE
    );

SHOW TABLES ;
DESCRIBE `Order`;

#-------------------------- ORDER DETAILS ------------------------------------------------------------------------------

DROP TABLE IF EXISTS `Order Details`;
CREATE TABLE IF NOT EXISTS `Order Details`(
    OrderID VARCHAR(8),
    ItemCode VARCHAR(6),
    OrderQty DOUBLE ,
    Discount DOUBLE,
    Price DOUBLE,
    CONSTRAINT PRIMARY KEY (OrderID,ItemCode),
    CONSTRAINT FOREIGN KEY (ItemCode) REFERENCES Item(ItemCode) ON DELETE CASCADE ON UPDATE CASCADE ,
    CONSTRAINT FOREIGN KEY (OrderID) REFERENCES `Order`(OrderID) ON DELETE CASCADE ON UPDATE CASCADE
    );

SHOW TABLES ;
DESCRIBE `Order Details`;

#------------------------------- LOGIN TABLE ---------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS Login(
    UserName VARCHAR (10),
    Password VARCHAR(10),
    CONSTRAINT PRIMARY KEY(UserName)
    );


INSERT INTO Login (UserName,PassWord)VALUES ('Admin',1234);

SHOW TABLES;
DESCRIBE Login;

SELECT Price FROM `Order Details`;
