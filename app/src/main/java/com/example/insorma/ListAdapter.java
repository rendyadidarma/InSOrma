package com.example.insorma;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListAdapter extends CursorAdapter {

    public ListAdapter(@NonNull Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//
//        Product product = getItem(position);
//
////        if(convertView == null) {
////            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
////        }
//
//        ImageView imageView = convertView.findViewById(R.id.product_image);
//        TextView productName = convertView.findViewById(R.id.product_name);
//        TextView productRating = convertView.findViewById(R.id.product_rating);
//        TextView productPrice = convertView.findViewById(R.id.product_price);
//
//
//        new DownloadImageTask(imageView).execute(product.getProductImage());
//        productName.setText(product.getProductName());
//        productPrice.setText(NumberFormat.getNumberInstance(Locale.getDefault()).format(product.getProductPrice()));
//        productRating.setText(String.format("Rating : %s", product.getProductRating()));
//
//        return convertView;
//    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView imageView = view.findViewById(R.id.product_image);
        TextView productName = view.findViewById(R.id.product_name);
        TextView productRating = view.findViewById(R.id.product_rating);
        TextView productPrice = view.findViewById(R.id.product_price);

        new DownloadImageTask(imageView).execute(cursor.getString(cursor.getColumnIndexOrThrow("ProductImage")));
        productName.setText(cursor.getString(cursor.getColumnIndexOrThrow("ProductName")));
        productRating.setText(cursor.getString(cursor.getColumnIndexOrThrow("ProductRating")));
        productPrice.setText(cursor.getString(cursor.getColumnIndexOrThrow("ProductPrice")));
    }

    public static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bmp = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bmp;
        }
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
