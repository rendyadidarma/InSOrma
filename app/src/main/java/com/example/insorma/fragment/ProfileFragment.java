package com.example.insorma.fragment;

import static com.example.insorma.LoginActivity.tempDataUser;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.insorma.DBHelper;
import com.example.insorma.R;
import com.example.insorma.Transaction;
import com.example.insorma.User;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    private final int user_id_logged;
    public ProfileFragment(int user_id_logged) {
        // Required empty public constructor
        this.user_id_logged = user_id_logged;
    }

    TextView email, label_username, username, phone;
    EditText usernameValue;
    Button editBtn, deleteAcc, logout, saveBtn;
    SharedPreferences sharedPreferences;
    private DBHelper dbHelper;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        init();
        return view;
    }

    public void init() {
        sharedPreferences = getContext().getSharedPreferences("LOGGED_IN", Context.MODE_PRIVATE);

        email = view.findViewById(R.id.profile_email);
        label_username = view.findViewById(R.id.username_label);
        username = view.findViewById(R.id.profile_username);
        phone = view.findViewById(R.id.profile_phone);

        usernameValue = view.findViewById(R.id.edit_username);
        editBtn = view.findViewById(R.id.edit_btn);
        deleteAcc = view.findViewById(R.id.delete_acc_btn);
        logout = view.findViewById(R.id.logout_btn);
        saveBtn = view.findViewById(R.id.save_btn);
        dbHelper = new DBHelper(getActivity());
        Cursor cursor = dbHelper.getUserData(user_id_logged);
        cursor.moveToFirst();

        email.setText(cursor.getString(cursor.getColumnIndexOrThrow("UserEmail")));
        username.setText(cursor.getString(cursor.getColumnIndexOrThrow("UserUsername")));
        phone.setText(cursor.getString(cursor.getColumnIndexOrThrow("UserPhoneNumber")));

        cursor.close();

        editBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        logout.setOnClickListener(this);
        deleteAcc.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dbHelper.close();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.edit_btn) {
            // edit button
            label_username.setVisibility(View.GONE);
            username.setVisibility(View.GONE);
            editBtn.setVisibility(View.GONE);

            usernameValue.setVisibility(View.VISIBLE);
            saveBtn.setVisibility(View.VISIBLE);
        } else if( v.getId() == R.id.save_btn) {
            String curr_username = usernameValue.getText().toString();
            try {
                if(curr_username.equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Failed to Update username, USERNAME CANNOT BE EMPTY", Toast.LENGTH_SHORT).show();
                } else {
                    boolean uniqueCheck = dbHelper.updateUserData(curr_username, user_id_logged);
                    if(!uniqueCheck) {
                        Toast.makeText(getActivity(), "Failed to Update, Username must be Unique", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Data Update Successful", Toast.LENGTH_SHORT).show();
                    }
                }

            } catch (Exception e) {
                Toast.makeText(getActivity(), "Failed to Update username, USERNAME MUST BE UNIQUE", Toast.LENGTH_SHORT).show();
            }

            label_username.setVisibility(View.VISIBLE);
            username.setVisibility(View.VISIBLE);
            editBtn.setVisibility(View.VISIBLE);

            usernameValue.setVisibility(View.GONE);
            saveBtn.setVisibility(View.GONE);
            init();
        } else if( v.getId() == R.id.delete_acc_btn) {
            boolean checkRemove = dbHelper.removeUserData(user_id_logged);
            if(checkRemove) {
                Toast.makeText(getActivity(), "Account Deleted Success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Account Deleted Failed", Toast.LENGTH_SHORT).show();
            }
            sharedPreferences.edit().clear().commit();
            getActivity().finish();
        } else if( v.getId() == R.id.logout_btn) {
            sharedPreferences.edit().clear().commit();
            getActivity().finish();
        }
    }

//    private void deleteTheHistory(int id) {
//        for(int i = 0; i < historyList.size(); i++) {
//            if(historyList.get(i).getUserID() == id) {
//                historyList.get(i).setUserID(-1);
//            }
//        }
//    }

    public boolean checkUsername(String username) {
        boolean flag = true;
        for(int i = 0; i < tempDataUser.size(); i++) {
            if(tempDataUser.get(i).getUserUsername().equalsIgnoreCase(username)){
                flag = false;
                break;
            }
        }
        return flag;
    }

    public int getUserIndex(int id) {
        int idx = -1;

        for(int i = 0; i < tempDataUser.size(); i++) {
            if(id == tempDataUser.get(i).getUserID()) {
                idx = i;
                break;
            }
        }

        return idx;
    }

    public int getUser_id_logged() {
        return user_id_logged;
    }
}