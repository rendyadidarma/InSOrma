package com.example.insorma;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends CursorAdapter {

    public HistoryAdapter(@NonNull Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//
//        Transaction transaction = getItem(position);
//
//        if(convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_history_item, parent, false);
//        }
//
//
//
//        historyID.setText(Integer.toString(transaction.getTransactionID()));
//
//        historyName.setText(productList.get(getProductIdx(transaction.getProductID())).getProductName());
//
//        DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//        historyDate.setText(dFormat.format(transaction.getDateTransaction()));
//        int tempQ = transaction.getQuantity();
//
//        long result = (long) productList.get(getProductIdx(transaction.getProductID())).getProductPrice() * tempQ;
//        historyPrice.setText(NumberFormat.getNumberInstance(Locale.getDefault()).format(result));
//        historyQuantity.setText(tempQ + "x");
//
//        return convertView;
//    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_history_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView historyID = view.findViewById(R.id.history_id);
        TextView historyName = view.findViewById(R.id.history_name);
        TextView historyDate = view.findViewById(R.id.history_date);
        TextView historyPrice = view.findViewById(R.id.history_price);
        TextView historyQuantity = view.findViewById(R.id.history_quantity);

        String ID = Integer.toString(cursor.getPosition()+1);
        Log.wtf("History Adapter" , Integer.toString(cursor.getPosition()));
        String ProductName = cursor.getString(cursor.getColumnIndexOrThrow("ProductName"));
        String Date = cursor.getString(cursor.getColumnIndexOrThrow("TransactionDate"));
        int Quantity = cursor.getInt(cursor.getColumnIndexOrThrow("Quantity"));

        int ProductPrice = cursor.getInt(cursor.getColumnIndexOrThrow("ProductPrice"));

        historyDate.setText(Date);
        historyID.setText(ID);
        historyName.setText(ProductName);
        historyQuantity.setText(String.format("%sx", Quantity));
        long calculation = (long) Quantity * ProductPrice;
        historyPrice.setText(String.valueOf(calculation));
    }
    
}
