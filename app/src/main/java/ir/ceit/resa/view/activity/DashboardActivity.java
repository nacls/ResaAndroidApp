package ir.ceit.resa.view.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import ir.ceit.resa.R;
import ir.ceit.resa.contract.DashboardContract;
import ir.ceit.resa.model.NavigationMenuItem;
import ir.ceit.resa.model.UserProfile;
import ir.ceit.resa.presenter.DashboardActivityPresenter;
import ir.ceit.resa.view.adapter.NavigationMenuItemAdapter;

public class DashboardActivity extends AppCompatActivity implements DashboardContract.View {

    private DashboardActivityPresenter dashboardPresenter;

    private Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private TextView usernameTv;
    private TextView fullNameTv;
    private UserProfile userProfile;
    private ImageView avatarIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Bundle data = getIntent().getExtras();
        userProfile = data.getParcelable("user_profile");

        dashboardPresenter = new DashboardActivityPresenter(this, this, userProfile);

        initializeView();
    }

    private void initializeView() {
        drawerLayout = findViewById(R.id.dashboard_layout);
        drawerList = findViewById(R.id.menu_items_list);
        usernameTv = findViewById(R.id.userUsernameNav);
        fullNameTv = findViewById(R.id.userNameNav);
        avatarIv = findViewById(R.id.header_avatar);

        setAvatarImage();
        setupToolbar();
        setUsernameAndFullName();
        setNavigationMenu();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawerLayout = findViewById(R.id.dashboard_layout);
        drawerLayout.setDrawerListener(mDrawerToggle);
        setupDrawerToggle();
    }

    private void setUsernameAndFullName() {
        String userUsername = "@" + userProfile.getUsername();
        usernameTv.setText(userUsername);
        String userFullName = userProfile.getFirstName() + " " + userProfile.getLastName();
        fullNameTv.setText(userFullName);
    }

    private void setNavigationMenu() {
        NavigationMenuItem[] drawerItem = new NavigationMenuItem[3];
        drawerItem[0] = new NavigationMenuItem(R.drawable.profile_logo, getResources().getString(R.string.profile));
        drawerItem[1] = new NavigationMenuItem(R.drawable.settings_logo, getResources().getString(R.string.settings));
        drawerItem[2] = new NavigationMenuItem(R.drawable.logout_logo, getResources().getString(R.string.logout));

        NavigationMenuItemAdapter adapter = new NavigationMenuItemAdapter(this, R.layout.navigation_list_view_item, drawerItem);
        drawerList.setAdapter(adapter);
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    private void setAvatarImage() {
        switch (userProfile.getRole()) {
            case ROLE_USER:
                avatarIv.setImageDrawable(getResources().getDrawable(R.drawable.user_avatar));
                break;
            case ROLE_ADMIN:
                avatarIv.setImageDrawable(getResources().getDrawable(R.drawable.admin_avatar));
                break;
            case ROLE_CREATOR:
                avatarIv.setImageDrawable(getResources().getDrawable(R.drawable.creator_avatar));
                break;
            default:
                break;
        }
    }

    private void selectItem(int position) {

        boolean isItemSelectedValid = true;

        switch (position) {
            case 0:
                dashboardPresenter.onProfileClicked();
                break;
            case 1:
                dashboardPresenter.onSettingsCLicked();
                break;
            case 2:
                dashboardPresenter.onLogoutClicked();
                break;

            default:
                isItemSelectedValid = false;
                break;
        }

        if (isItemSelectedValid) {
            drawerList.setItemChecked(position, true);
            drawerList.setSelection(position);
            drawerLayout.closeDrawers();
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
        toolbar = findViewById(R.id.toolbar);
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