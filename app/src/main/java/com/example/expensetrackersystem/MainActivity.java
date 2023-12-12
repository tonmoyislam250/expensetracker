package com.example.expensetrackersystem;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.expensetrackersystem.fragments.Dashboard;
import com.example.expensetrackersystem.fragments.Expense;
import com.example.expensetrackersystem.fragments.Income;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/** @noinspection ALL*/
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.dashboard);

    }

    Dashboard dashboardFragment = new Dashboard();
    Expense expenseFragment = new Expense();
    Income incomeFragment = new Income();

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.income:
                getSupportFragmentManager().beginTransaction().replace(R.id.FillFragment, incomeFragment).commit();
                return true;

            case R.id.dashboard:
                getSupportFragmentManager().beginTransaction().replace(R.id.FillFragment, dashboardFragment).commit();
                return true;

            case R.id.expense:
                getSupportFragmentManager().beginTransaction().replace(R.id.FillFragment, expenseFragment).commit();
                return true;
        }
        return false;
    }

}