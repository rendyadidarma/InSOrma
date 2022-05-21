package com.example.insorma.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.insorma.DBHelper;
import com.example.insorma.FurnitureDetail;
import com.example.insorma.ListAdapter;
import com.example.insorma.Product;
import com.example.insorma.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment  {

    private final int user_id_logged;
    private String username;
    private Cursor cursor;
    private ProgressBar progressBar;
    public HomeFragment(int UserID) {
        this.user_id_logged = UserID;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cursor.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        TextView usernameView = view.findViewById(R.id.username_label);
        ListView listView = view.findViewById(R.id.list_view);
        DBHelper dbHelper = new DBHelper(getActivity());
        try (Cursor cursor = dbHelper.getUserData(user_id_logged)) {
            cursor.moveToFirst();
            username = cursor.getString(2);
            Log.wtf("Username In Fragment", cursor.getString(2));
            Log.wtf("ID in Fragment", cursor.getString(0));
        } catch (Exception e) {
            Toast.makeText(getActivity(), "No match data", Toast.LENGTH_SHORT).show();
        }

        cursor = dbHelper.getProductDataV2();
        cursor.moveToFirst();

        usernameView.setText(String.format("Hello, %s", username));
        ListAdapter listAdapter = new ListAdapter(getActivity(), cursor);
        listView.setAdapter(listAdapter);
        listView.setClickable(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent a = new Intent(getActivity(), FurnitureDetail.class);
                a.putExtra("position", id);
                startActivity(a);
            }
        });

        listView.setEmptyView(view.findViewById(R.id.empty_text));

//        cursor.close();

        return view;
    }
}