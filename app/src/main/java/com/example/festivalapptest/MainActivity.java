package com.example.festivalapptest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        loadFragment(new HomeFragment());
    }

    // Method to load fragments
    private boolean loadFragment(Fragment fragment){
        if (fragment != null){

            // Switches from first fragment to fragment that needs to be loaded
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;

    }

    @Override
    // Will be called whenever a menu item is tapped in the bottom navigation
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment = null;

        switch (menuItem.getItemId()){
            case R.id.title_map:
                fragment = new HomeFragment();
                break;

            case R.id.title_menu:
                fragment = new MenuFragment();
                break;

            case R.id.title_friends:
                fragment = new FriendsFragment();
                break;

        }

        return loadFragment(fragment);
    }
}
