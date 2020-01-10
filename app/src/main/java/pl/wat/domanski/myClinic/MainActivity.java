package pl.wat.domanski.myClinic;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import pl.wat.domanski.myClinic.fragments.OnBackPressedListener;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {
    public Toolbar toolbar;

    public DrawerLayout drawerLayout;

    public NavController navController;

    public NavigationView navigationView;

    protected OnBackPressedListener onBackPressedListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupNavigation();

    }

    // Setting Up One Time Navigation
    private void setupNavigation() {
        Toast.makeText(this, "setupNavigation", Toast.LENGTH_SHORT).show();


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.navigationView);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);

        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onSupportNavigateUp() {
        Toast.makeText(this, "onSupportNavigateUp", Toast.LENGTH_SHORT).show();

        return NavigationUI.navigateUp(drawerLayout, Navigation.findNavController(this, R.id.nav_host_fragment));
    }

    @Override
    public void onBackPressed() {
        if (onBackPressedListener != null){
            onBackPressedListener.doBack();
            this.getSupportFragmentManager().popBackStack();
        }
        else{
            super.onBackPressed();
            this.getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Toast.makeText(this, "onNavigationItemSelected", Toast.LENGTH_SHORT).show();

        menuItem.setChecked(true);

        drawerLayout.closeDrawers();

        int id = menuItem.getItemId();

        switch (id) {
            case R.id.nav_home:
                navController.navigate(R.id.defaultFragment);
                break;
            case R.id.nav_visits:
                navController.navigate(R.id.visitsFragment);
                break;
            case R.id.nav_patients:
                navController.navigate(R.id.patientsFragment);
                break;

            case R.id.nav_doctors:
                navController.navigate(R.id.doctorsFragment);
                break;

            case R.id.nav_backup:
                navController.navigate(R.id.backupFragment);
                break;

            case R.id.nav_settings:
                navController.navigate(R.id.settingsFragment);
                break;


        }
        return true;

    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

}
