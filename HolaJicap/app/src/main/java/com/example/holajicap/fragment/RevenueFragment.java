package com.example.holajicap.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holajicap.R;
import com.example.holajicap.adapter.CategoryAdapter;
import com.example.holajicap.db.HolaJicapDatabase;
import com.example.holajicap.model.Category;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RevenueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RevenueFragment extends Fragment {
    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private List<RevenueType> revenueTypes;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private HolaJicapDatabase db;
    public RevenueFragment() {
        db = HolaJicapDatabase.getInstance(getContext());
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RevenueFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RevenueFragment newInstance(String param1, String param2) {
        RevenueFragment fragment = new RevenueFragment();
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
        View view = inflater.inflate(R.layout.fragment_revenue, container, false);
        db = HolaJicapDatabase.getInstance(getContext());
        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView_revenueType);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Category> categories = db.categoryDao().getCategoriesByType("Revenue");
//        // Initialize data
//        revenueTypes = new ArrayList<>();
//        revenueTypes.add(new RevenueType(R.drawable.salary, "Lương"));
//        revenueTypes.add(new RevenueType(R.drawable.interest_income, "Thu lãi"));
//
//        revenueTypes.add(new RevenueType(R.drawable.other_income, "Thu nhập khác"));
//        revenueTypes.add(new RevenueType(R.drawable.money_transfer_in, "Tiền chuyển đến"));
//        // Add more types...
//
//        // Set adapter
        adapter = new CategoryAdapter(categories);
        recyclerView.setAdapter(adapter);

        return view;
    }
}