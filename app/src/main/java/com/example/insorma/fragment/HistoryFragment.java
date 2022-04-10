package com.example.insorma.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.insorma.DBHelper;
import com.example.insorma.HistoryAdapter;
import com.example.insorma.R;
import com.example.insorma.Transaction;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    public HistoryFragment() {
    }

    private ArrayList<Transaction> tempTransactionCurrentUser;
    private Cursor cursor;
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.wtf("OnDestroyHistory", "true");
        cursor.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        ListView listView = view.findViewById(R.id.list_view);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LOGGED_IN", Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt("ID", -1);
        Log.wtf("test1", Integer.toString(id));
        DBHelper dbHelper = new DBHelper(getActivity());
        cursor = dbHelper.getTransactionData(id);
        cursor.moveToFirst();

        HistoryAdapter historyAdapter = new HistoryAdapter(getActivity(), cursor);
        listView.setAdapter(historyAdapter);

        listView.setEmptyView(view.findViewById(R.id.empty_text));
        return view;
    }

//    public void generateHistoryListCurrentUser() {
//        int currUserID = logged_in_user.getUserID();
//
//        tempTransactionCurrentUser = new ArrayList<Transaction>();
//
//        for(Transaction item : historyList) {
//            if(item.getUserID() == currUserID)
//                tempTransactionCurrentUser.add(item);
//        }
//    }

}