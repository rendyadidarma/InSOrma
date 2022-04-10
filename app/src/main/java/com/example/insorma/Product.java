package com.example.insorma;

import java.util.ArrayList;

public class Product {

    private int ProductID, ProductPrice;
    private String ProductRating, ProductImage;
    private String ProductName, ProductDescription;

    public Product(int productID, int productPrice, String productRating, String productName, String productImage, String productDescription) {
        ProductID = productID;
        ProductPrice = productPrice;
        ProductRating = productRating;
        ProductName = productName;
        ProductImage = productImage;
        ProductDescription = productDescription;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public int getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(int productPrice) {
        ProductPrice = productPrice;
    }

    public String getProductRating() {
        return ProductRating;
    }

    public void setProductRating(String productRating) {
        ProductRating = productRating;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    @Override
    public String toString() {
        return "Product{" +
                "ProductID=" + ProductID +
                ", ProductPrice=" + ProductPrice +
                ", ProductRating='" + ProductRating + '\'' +
                ", ProductImage='" + ProductImage + '\'' +
                ", ProductName='" + ProductName + '\'' +
                ", ProductDescription='" + ProductDescription + '\'' +
                '}';
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }
}
