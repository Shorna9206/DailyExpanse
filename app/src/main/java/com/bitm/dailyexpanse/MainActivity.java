package com.bitm.dailyexpanse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    int check = 0;

    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        check = getIntent().getIntExtra("check", 0);


        //Toast.makeText(this, "check" + check, Toast.LENGTH_SHORT).show();
        bottomNavigationView = findViewById(R.id.bottomnav);

        if (check == 1) {
            View view = bottomNavigationView.findViewById(R.id.nav_expense);
            reloadFragment();
            view.performClick();

            check = 0;


        } else if (check == 0) {
            // Toast.makeText(this, "000"+check, Toast.LENGTH_SHORT).show();

            replaceFragment(new DashboardFragment());

        }


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {


                    case R.id.nav_expense:
                        replaceFragment(new ExpenseFragment());
                        setTitle("Expense");
                        return true;

                    case R.id.nav_dashboard:
                        replaceFragment(new DashboardFragment());

                        setTitle("Dashboard");
                        return true;
                }
                return false;

            }
        });

    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, fragment);
        setTitle("Dashboard");
        ft.commit();

    }


    public void reloadFragment() {


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, new ExpenseFragment());
        setTitle("Expense");
        ft.commit();
        bottomNavigationView.performClick();
    }

    public void onBackPressed() {
        moveTaskToBack(true);
        finish();
    }

}