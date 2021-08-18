package ir.ceit.resa.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import ir.ceit.resa.R;
import ir.ceit.resa.model.NavigationMenuItem;
import ir.ceit.resa.model.UserProfile;
import ir.ceit.resa.view.adapter.NavigationMenuItemAdapter;

public class DashboardActivity extends AppCompatActivity {

    Toolbar toolbar;
    ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private TextView usernameTv;
    private TextView fullNameTv;
    private UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Bundle data = getIntent().getExtras();
        userProfile = data.getParcelable("user_profile");

        initializeView();
    }

    private void initializeView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.dashboard_layout);
        drawerList = (ListView) findViewById(R.id.menu_items_list);
        usernameTv = findViewById(R.id.userUsernameNav);
        fullNameTv = findViewById(R.id.userNameNav);

        usernameTv.setText("@"+userProfile.getUsername());
        fullNameTv.setText(userProfile.getFirstName() + " " + userProfile.getLastName());
        setupToolbar();

        NavigationMenuItem[] drawerItem = new NavigationMenuItem[3];

        drawerItem[0] = new NavigationMenuItem(R.drawable.profile_logo, getResources().getString(R.string.profile));
        drawerItem[1] = new NavigationMenuItem(R.drawable.settings_logo, getResources().getString(R.string.settings));
        drawerItem[2] = new NavigationMenuItem(R.drawable.logout_logo, getResources().getString(R.string.logout));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        NavigationMenuItemAdapter adapter = new NavigationMenuItemAdapter(this, R.layout.navigation_list_view_item, drawerItem);
        drawerList.setAdapter(adapter);
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        drawerLayout = (DrawerLayout) findViewById(R.id.dashboard_layout);
        drawerLayout.setDrawerListener(mDrawerToggle);
        setupDrawerToggle();
    }

    private void selectItem(int position) {

        switch (position) {
            case 0:
                System.out.println("Profile");
                break;
            case 1:
                System.out.println("Settings");
                break;
            case 2:
                System.out.println("Logout");
                break;

            default:
                break;
        }

        if (true) {
            drawerList.setItemChecked(position, true);
            drawerList.setSelection(position);
            drawerLayout.closeDrawers();

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    void setupDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerToggle.syncState();
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

    }
}