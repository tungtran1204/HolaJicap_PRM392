package com.example.holajicap.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.holajicap.AddTransactionActivity;
import com.example.holajicap.ChangePasswordAccountActivity;
import com.example.holajicap.ChangePasswordActivity;
import com.example.holajicap.MainActivity;
import com.example.holajicap.R;
import com.example.holajicap.db.HolaJicapDatabase;
import com.example.holajicap.model.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {
    private HolaJicapDatabase db;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);

        View view = inflater.inflate(R.layout.fragment_account, container, false);
        db = HolaJicapDatabase.getInstance(getContext());

        TextView title = view.findViewById(R.id.account_toolbar_title);
        title.setText("TÀI KHOẢN");

        User user = db.userDao().getUserById(userId);
        TextView email = view.findViewById(R.id.tv_account_email);
        if (user != null) {
            email.setText(user.getEmail());
        } else {
            email.setText("Không tìm thấy email");
            Log.e("AccountFragment", "User not found or userId is invalid");
        }
        Button logOutBtn = view.findViewById(R.id.logOutBtn);
        Button chnpassBtn = view.findViewById(R.id.btn_Changepassword);
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("userId");
                editor.clear();
                editor.apply();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        chnpassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePasswordAccountActivity.class);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return view;

    }

}
