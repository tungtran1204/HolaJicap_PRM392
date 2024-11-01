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
import com.example.holajicap.db.HolaJicapDatabase;
import com.example.holajicap.model.Category;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExpenditureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpenditureFragment extends Fragment {
    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private List<Category> categories;
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
        db = HolaJicapDatabase.getInstance(getContext());
        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView_expenditureType);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        categories = db.categoryDao().getCategoriesByType("Expenditure");

        // Set adapter
        adapter = new CategoryAdapter(categories, requireContext());
        recyclerView.setAdapter(adapter);

        return view;
    }
}
// Initialize data
//        expenditureTypes = new ArrayList<>();
//        expenditureTypes.add(new ExpenditureType(R.drawable.food, "Ăn uống"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.medical_insurance, "Bảo hiểm"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.other_expense, "Các chi phí khác"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.investment, "Đầu tư"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.transportation, "Di chuyển"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.car_repair, "Bảo dưỡng xe"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.family, "Gia đình"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.family_service, "Dịch vụ gia đình"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.house_repair, "Sửa & trang trí nhà"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.pet, "Vật nuôi"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.entertainment, "Giải trí"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.online_service, "Dịch vụ trực tuyến"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.leisure, "Vui chơi"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.education, "Giáo dục"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.electricity, "Hóa đơn điện"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.phone, "Hóa đơn điện thoại"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.gas, "Hóa đơn ga"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.internet, "Hóa đơn Internet"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.water, "Hóa đơn nước"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.utility_bill, "Hóa đơn tiện ích khác"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.television, "Hóa đơn TV"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.house_rent, "Thuê nhà"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.shopping, "Mua sắm"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.personal_belonging, "Đồ dùng cá nhân"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.household_appliances, "Đồ gia dụng"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.beauty, "Làm đẹp"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.gift, "Quà tặng & quyên góp"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.health, "Sức khỏe"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.health_checkup, "Khám sức khỏe"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.fitness_workout, "Thể dục thể thao"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.money_transfer_out, "Tiền chuyển đi"));
//        expenditureTypes.add(new ExpenditureType(R.drawable.interest_payment, "Trả lãi"));
// Add more types...