package com.example.insorma;

import java.util.Date;

public class Transaction {

    private int transactionID, userID, productID, quantity;
    Date dateTransaction;

    public Transaction(int transactionID, int userID, int productID, int quantity, Date dateTransaction) {
        this.transactionID = transactionID;
        this.userID = userID;
        this.productID = productID;
        this.quantity = quantity;
        this.dateTransaction = dateTransaction;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(Date dateTransaction) {
        this.dateTransaction = dateTransaction;
    }
}
