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

import com.example.expensetrackersystem.DatabaseHandlerExpense;
import com.example.expensetrackersystem.PieChart;
import com.example.expensetrackersystem.R;
import com.example.expensetrackersystem.adapter.expenseAdapter2;
import com.example.expensetrackersystem.model.expenseModel;

import java.util.List;

public class Expense extends Fragment {
    public Expense() {
    }
    private TextView tvExpense;
    private RecyclerView rvExpense;
    private DatabaseHandlerExpense databaseHandlerExpense;

    private ImageView iv_expensePie;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense, container, false);
        init(view);
        iv_expensePie.setOnClickListener(v -> startActivity(new Intent(getContext(), PieChart.class)));
        fillExpense();
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void fillExpense() {
        List<expenseModel> expenseModelList = databaseHandlerExpense.getAllIncome();
        int total = 0;
        for (expenseModel model : expenseModelList) {
            total += Integer.parseInt(model.getAmount());
        }
        String totalExpense = String.valueOf(total);
        tvExpense.setText("৳" + totalExpense);

        expenseAdapter2 expenseAdapter = new expenseAdapter2(getContext(), expenseModelList, databaseHandlerExpense);
        rvExpense.setLayoutManager(new LinearLayoutManager(getContext()));
        rvExpense.setHasFixedSize(true);

        rvExpense.setAdapter(expenseAdapter);
    }

    private void init(View view) {
        tvExpense = view.findViewById(R.id.tvExpense);
        rvExpense = view.findViewById(R.id.rvExpense);
        iv_expensePie = view.findViewById(R.id.iv_expensePie);
        databaseHandlerExpense = new DatabaseHandlerExpense(getContext());
    }
}