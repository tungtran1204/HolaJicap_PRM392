package com.example.holajicap.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holajicap.R;
import com.example.holajicap.adapter.CategoryAdapter;
import com.example.holajicap.adapter.ExpenditureTypeAdapter;
import com.example.holajicap.db.HolaJicapDatabase;
import com.example.holajicap.model.Category;
import com.example.holajicap.model.ExpenditureType;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExpenditureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpenditureFragment extends Fragment {
    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private List<ExpenditureType> expenditureTypes;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private HolaJicapDatabase db;

    public ExpenditureFragment() {
        db = HolaJicapDatabase.getInstance(getContext());
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExpenditureFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExpenditureFragment newInstance(String param1, String param2) {
        ExpenditureFragment fragment = new ExpenditureFragment();
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenditure, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView_expenditureType);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Category> categories = db.categoryDao().getCategoriesByType("Expenditure");

        // Initialize data
//        expenditureTypes = new ArrayList<>();
//        expenditureTypes.add(new ExpenditureType(R.drawable.food, "Food & Drinks"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.medical_insurance, "Insurance"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.other_expense, "Other expenses"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.investment, "Investments"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.transportation, "Transportation"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.car_repair, "Car maintenance"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.family, "Family"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.family_service, "Family services"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.house_repair, "Home repair & decor"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.pet, "Pets"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.entertainment, "Entertainment"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.online_service, "Online services"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.leisure, "Leisure"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.education, "Education"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.electricity, "Electricity bill"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.phone, "Phone bill"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.gas, "Gas bill"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.internet, "Internet bill"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.water, "Water bill"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.utility_bill, "Other utility bills"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.television, "TV bill"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.house_rent, "Rent"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.shopping, "Shopping"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.personal_belonging, "Personal items"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.household_appliances, "Household goods"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.beauty, "Beauty"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.gift, "Gifts & Donations"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.health, "Health"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.health_checkup, "Health checkups"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.fitness_workout, "Fitness"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.money_transfer_out, "Money transfer out"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.interest_payment, "Interest payment"));
        // Add more types...

        // Set adapter
        adapter = new CategoryAdapter(categories);
        recyclerView.setAdapter(adapter);

        return view;
    }
}