package com.example.expensetrackersystem.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetrackersystem.DatabaseHandler;
import com.example.expensetrackersystem.DatabaseHandlerExpense;
import com.example.expensetrackersystem.R;
import com.example.expensetrackersystem.adapter.expenseAdapter;
import com.example.expensetrackersystem.adapter.incomeAdapter;
import com.example.expensetrackersystem.model.expenseModel;
import com.example.expensetrackersystem.model.incomeModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Dashboard extends Fragment {

    public Dashboard() {
    }

    private RecyclerView rv_income, rv_expense;
    private TextView tv_income, tv_expense;

    FloatingActionButton mAddFab, mAddIncomeFab, mAddExpenseFab;
    TextView addIncomeText, addExpenseText;
    Boolean isAllFabsVisible;

    private DatabaseHandler databaseHandler;
    private DatabaseHandlerExpense databaseHandlerExpense;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        init(view);

        databaseHandler = new DatabaseHandler(getContext());
        databaseHandlerExpense = new DatabaseHandlerExpense(getContext());
        fillIncomeModel();
        fillExpenseModel();

        mAddFab.setOnClickListener(view1 -> {
            if (!isAllFabsVisible) {
                mAddIncomeFab.show();
                mAddExpenseFab.show();
                addExpenseText.setVisibility(View.VISIBLE);
                addIncomeText.setVisibility(View.VISIBLE);

                isAllFabsVisible = true;
            } else {

                mAddIncomeFab.hide();
                mAddExpenseFab.hide();
                addExpenseText.setVisibility(View.GONE);
                addIncomeText.setVisibility(View.GONE);

                isAllFabsVisible = false;
            }
        });

        mAddIncomeFab.setOnClickListener(view12 -> showIncomeDialog());

        mAddExpenseFab.setOnClickListener(view13 -> showExpenseDialog());

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void fillExpenseModel() {
        List<expenseModel> expenseModelList = databaseHandlerExpense.getAllIncome();

        int total = 0;
        for (expenseModel model : expenseModelList) {
            total += Integer.parseInt(model.getAmount());
        }
        String totalExpense = String.valueOf(total);
        tv_expense.setText("৳" + totalExpense);

        com.example.expensetrackersystem.adapter.expenseAdapter expenseAdapter = new expenseAdapter(getContext(), expenseModelList);
        rv_expense.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv_expense.setHasFixedSize(true);

        rv_expense.setAdapter(expenseAdapter);
    }

    @SuppressLint("SetTextI18n")
    private void fillIncomeModel() {
        List<incomeModel> incomeModelList = databaseHandler.getAllIncome();

        int total = 0;
        for (incomeModel model : incomeModelList) {
            total += Integer.parseInt(model.getAmount());
        }
        String totalIncome = String.valueOf(total);
        tv_income.setText("৳" + totalIncome);

        com.example.expensetrackersystem.adapter.incomeAdapter incomeAdapter = new incomeAdapter(getContext(), incomeModelList);
        rv_income.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv_income.setHasFixedSize(true);

        rv_income.setAdapter(incomeAdapter);

    }

    private void showIncomeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        final View customLayout = getLayoutInflater().inflate(R.layout.income_add_litem, null);
        EditText et_income = customLayout.findViewById(R.id.et_incomeAmount);
        EditText et_type = customLayout.findViewById(R.id.et_incomeType);
        EditText et_note = customLayout.findViewById(R.id.et_incomeNote);

        Button btn_save = customLayout.findViewById(R.id.btn_save);
        Button btn_cancel = customLayout.findViewById(R.id.btn_cancel);

        builder.setView(customLayout);
        AlertDialog alertDialog = builder.create();

        alertDialog.show();

        btn_cancel.setOnClickListener(v -> alertDialog.dismiss());

        btn_save.setOnClickListener(v -> {
            String amount = et_income.getText().toString();
            String type = et_type.getText().toString();
            String note = et_note.getText().toString();
            long date = System.currentTimeMillis();

            if (amount.isEmpty()) {
                et_income.setError("Empty amount");
            } else if (type.isEmpty()) {
                et_type.setError("Empty Type");
            } else if (note.isEmpty()) {
                et_note.setError("Empty note");
            } else {
                databaseHandler.addData(amount, type, note, String.valueOf(date));
                alertDialog.dismiss();
                fillIncomeModel();
            }

        });

    }

    private void showExpenseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        final View customLayout = getLayoutInflater().inflate(R.layout.expense_add_item, null);
        EditText et_income = customLayout.findViewById(R.id.et_incomeAmount);
        EditText et_type = customLayout.findViewById(R.id.et_incomeType);
        EditText et_note = customLayout.findViewById(R.id.et_incomeNote);

        Button btn_save = customLayout.findViewById(R.id.btn_save);
        Button btn_cancel = customLayout.findViewById(R.id.btn_cancel);

        builder.setView(customLayout);
        AlertDialog alertDialog = builder.create();

        alertDialog.show();

        btn_cancel.setOnClickListener(v -> alertDialog.dismiss());

        btn_save.setOnClickListener(v -> {
            String amount = et_income.getText().toString();
            String type = et_type.getText().toString();
            String note = et_note.getText().toString();
            long date = System.currentTimeMillis();

            if (amount.isEmpty()) {
                et_income.setError("Empty amount");
            } else if (type.isEmpty()) {
                et_type.setError("Empty Type");
            } else if (note.isEmpty()) {
                et_note.setError("Empty note");
            } else {
                databaseHandlerExpense.addData(amount, type, note, String.valueOf(date));
                alertDialog.dismiss();
                fillExpenseModel();
            }

        });

    }

    @SuppressLint("SetTextI18n")
    private void init(View root) {
        rv_income = root.findViewById(R.id.rv_income);
        rv_expense = root.findViewById(R.id.rv_expense);

        tv_income = root.findViewById(R.id.tv_income);
        tv_expense = root.findViewById(R.id.tv_expense);

        tv_income.setText("BDT. 11000");
        tv_expense.setText("BDT. 8000");

        mAddFab = root.findViewById(R.id.add_fab);
        mAddIncomeFab = root.findViewById(R.id.add_income_fab);
        mAddExpenseFab = root.findViewById(R.id.add_expense_fab);

        addIncomeText = root.findViewById(R.id.add_income_text);
        addExpenseText = root.findViewById(R.id.add_expense_text);

        mAddIncomeFab.setVisibility(View.GONE);
        mAddExpenseFab.setVisibility(View.GONE);
        addIncomeText.setVisibility(View.GONE);
        addExpenseText.setVisibility(View.GONE);

        isAllFabsVisible = false;
    }

}