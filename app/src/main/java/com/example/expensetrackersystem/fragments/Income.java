package com.example.expensetrackersystem.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetrackersystem.DatabaseHandler;
import com.example.expensetrackersystem.PieChartIncome;
import com.example.expensetrackersystem.R;
import com.example.expensetrackersystem.adapter.incomeAdapter2;
import com.example.expensetrackersystem.model.incomeModel;

import java.util.List;

public class Income extends Fragment {
    public Income() {
    }
    private TextView tvIncome;
    private RecyclerView rvIncome;
    private DatabaseHandler databaseHandler;

    private ImageView iv_expensePie;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income, container, false);
        init(view);
        iv_expensePie.setOnClickListener(v -> startActivity(new Intent(getContext(), PieChartIncome.class)));
        fillExpense();
        return view;
    }

    @SuppressLint("SetTextI18n")
    public void fillExpense() {
        List<incomeModel> incomeModelList = databaseHandler.getAllIncome();
        int total = 0;
        for (incomeModel model : incomeModelList) {
            total += Integer.parseInt(model.getAmount());
        }
        String totalIncome = String.valueOf(total);
        tvIncome.setText("৳" + totalIncome);
        incomeAdapter2 expenseAdapter = new incomeAdapter2(getContext(), incomeModelList, databaseHandler);
        rvIncome.setLayoutManager(new LinearLayoutManager(getContext()));
        rvIncome.setHasFixedSize(true);
        rvIncome.setAdapter(expenseAdapter);
    }

    private void init(View view) {
        tvIncome = view.findViewById(R.id.tv_income);
        rvIncome = view.findViewById(R.id.rv_income);
        iv_expensePie = view.findViewById(R.id.iv_expensePie);
        databaseHandler = new DatabaseHandler(getContext());
    }

}