package com.example.insorma;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//import android.support.annotation.Nullable;

import androidx.annotation.Nullable;

import java.sql.Date;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String databaseName = "creature1.db";
    public static final int databaseVersion = 1;

    //    TABLE NAME AND COLUMN NAME
    public static final String TABLE_USER = "User";
    public static final String USER_ID = "UserID";
    public static final String USER_EMAIL_ADDRESS = "UserEmail";
    public static final String USER_USERNAME = "UserUsername";
    public static final String USER_PHONE_NUMBER = "UserPhoneNumber";
    public static final String USER_PASSWORD = "UserPassword";

    public static final String TABLE_PRODUCT = "Product";
    public static final String PRODUCT_ID = "ProductID";
    public static final String PRODUCT_NAME = "ProductName";
    public static final String PRODUCT_RATING = "ProductRating";
    public static final String PRODUCT_PRICE = "ProductPrice";
    public static final String PRODUCT_IMAGE = "ProductImage";
    public static final String PRODUCT_DESCRIPTION = "ProductDescription";

    public static final String TABLE_TRANSACTION = "Transactions";
    public static final String TRANSACTION_ID = "TransactionID";
    public static final String USER_ID_FK = "UserID";
    public static final String PRODUCT_ID_FK = "ProductID";
    public static final String TRANSACTION_DATE = "TransactionDate";
    public static final String QUANTITY = "Quantity";
    //    END TABLE NAME AND COLUMN NAME

    public static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + " (" +
            USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            USER_EMAIL_ADDRESS + " TEXT NOT NULL UNIQUE, " +
            USER_USERNAME + " TEXT NOT NULL UNIQUE, " +
            USER_PHONE_NUMBER + " TEXT NOT NULL, " +
            USER_PASSWORD + " TEXT NOT NULL)";

    public static final String CREATE_TABLE_PRODUCT = "CREATE TABLE " + TABLE_PRODUCT + " (" +
            PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PRODUCT_NAME + " TEXT NOT NULL, " +
            PRODUCT_RATING + " TEXT NOT NULL, " +
            PRODUCT_PRICE + " INTEGER NOT NULL, " +
            PRODUCT_IMAGE + " TEXT NOT NULL, " +
            PRODUCT_DESCRIPTION + " TEXT NOT NULL)";

    public static final String CREATE_TABLE_TRANSACTION = "CREATE TABLE " + TABLE_TRANSACTION + " (" +
            TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            USER_ID + " INTEGER, " +
            PRODUCT_ID + " INTEGER, " +
            TRANSACTION_DATE + " DATE NOT NULL, " +
            QUANTITY + " INTEGER NOT NULL, " +
            "FOREIGN KEY(UserID) REFERENCES User(UserID), " +
            "FOREIGN KEY(ProductID) REFERENCES Product(ProductID))";

    public DBHelper(@Nullable Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
        sqLiteDatabase.execSQL(CREATE_TABLE_PRODUCT);
        sqLiteDatabase.execSQL(CREATE_TABLE_TRANSACTION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);
        onCreate(sqLiteDatabase);
    }

    public boolean setUserData (User data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(USER_EMAIL_ADDRESS, data.getUserEmailAddress());
        content.put(USER_USERNAME, data.getUserUsername());
        content.put(USER_PHONE_NUMBER, data.getUserPhoneNumber());
        content.put(USER_PASSWORD, data.getUserPassword());
        long res = db.insertOrThrow(TABLE_USER, null, content);

        if(res == -1)
            return false;
        else
            return true;
    }

    public Cursor getUserData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USER, null);
    }

    public Cursor getUserData(int idx) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE UserID = " + idx, null);
    }



    public boolean setProductData (Product data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(PRODUCT_NAME, data.getProductName());
        content.put(PRODUCT_RATING, data.getProductRating());
        content.put(PRODUCT_PRICE, data.getProductPrice());
        content.put(PRODUCT_IMAGE, data.getProductImage());
        content.put(PRODUCT_DESCRIPTION, data.getProductDescription());

        long res = db.insertOrThrow(TABLE_PRODUCT, null, content);

        if(res == -1)
            return false;
        else
            return true;
    }

    public Cursor getProductData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PRODUCT, null);
    }

    public Cursor getProductDataV2() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT ProductID as _id, ProductName, ProductRating, ProductPrice, ProductImage, ProductDescription FROM " + TABLE_PRODUCT, null);
    }

    public Cursor getProductData(long idx) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PRODUCT + " WHERE ProductID = " + idx, null);
    }

    public boolean updateUserData(String newUsername, int idx) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_USERNAME, newUsername);

        int update = db.update(TABLE_USER, contentValues, "UserID = ?", new String[] {String.valueOf(idx)});

        if(update == 0)
            return false;
        else
            return true;
    }

    public boolean removeUserData(int idx) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_USER, USER_ID + " = ?", new String[] {String.valueOf(idx)}) > 0;
    }

    public boolean setTransactionData (Transaction data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(USER_ID_FK, data.getUserID());
        content.put(PRODUCT_ID_FK, data.getProductID());
        content.put(TRANSACTION_DATE, String.valueOf(data.getDateTransaction()));
        content.put(QUANTITY, data.getQuantity());

        long res = db.insertOrThrow(TABLE_TRANSACTION, null, content);

        if(res == -1)
            return false;
        else
            return true;
    }

    public Cursor getTransactionData(int user_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT TransactionID as _id, UserID, Transactions.ProductID, TransactionDate, Quantity, Product.ProductID, ProductName, ProductRating, ProductPrice, ProductImage, ProductDescription FROM Transactions JOIN Product ON Transactions.ProductID = Product.ProductID WHERE Transactions.UserID = " + user_id, null);
    }

}
