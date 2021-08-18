package ir.ceit.resa.view.activity;

import android.os.Bundle;
import android.view.Menu;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

import ir.ceit.resa.R;
import ir.ceit.resa.contract.DashboardContract;
import ir.ceit.resa.model.ERole;
import ir.ceit.resa.model.NavigationMenuItem;
import ir.ceit.resa.model.UserProfile;
import ir.ceit.resa.presenter.DashboardActivityPresenter;
import ir.ceit.resa.view.adapter.NavigationMenuItemAdapter;

public class DashboardActivity extends AppCompatActivity implements DashboardContract.View {

    ArrayList<NavigationMenuItem> drawerItems = new ArrayList<>();
    private DashboardActivityPresenter dashboardPresenter;
    private Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private TextView usernameTv;
    private TextView fullNameTv;
    private UserProfile userProfile;
    private ImageView avatarIv;
    private FloatingActionButton createBoardBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Bundle data = getIntent().getExtras();
        userProfile = data.getParcelable("user_profile");

        dashboardPresenter = new DashboardActivityPresenter(this, this, userProfile);

        setupActivityView();
    }

    private void setupActivityView() {

        initializeViews();

        setVideBasedOnRole();
        setupToolbar();
        setUsernameAndFullName();
        setNavigationMenu();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawerLayout = findViewById(R.id.dashboard_layout);
        drawerLayout.setDrawerListener(mDrawerToggle);
        setupDrawerToggle();
    }

    private void initializeViews() {
        drawerLayout = findViewById(R.id.dashboard_layout);
        drawerList = findViewById(R.id.menu_items_list);
        usernameTv = findViewById(R.id.userUsernameNav);
        fullNameTv = findViewById(R.id.userNameNav);
        avatarIv = findViewById(R.id.header_avatar);
        createBoardBtn = findViewById(R.id.add_board_button);
    }

    private void setVideBasedOnRole() {
        switch (userProfile.getRole()) {
            case ROLE_USER:
                avatarIv.setImageDrawable(getResources().getDrawable(R.drawable.user_avatar));
                createBoardBtn.setVisibility(View.GONE);
                break;
            case ROLE_ADMIN:
                avatarIv.setImageDrawable(getResources().getDrawable(R.drawable.admin_avatar));
                setupCreateBoardButton();
                break;
            case ROLE_CREATOR:
                avatarIv.setImageDrawable(getResources().getDrawable(R.drawable.creator_avatar));
                setupCreateBoardButton();
                break;
            default:
                break;
        }
    }

    private void setupCreateBoardButton() {
        createBoardBtn.setOnClickListener(view -> dashboardPresenter.onCreateBoardClicked());
    }

    void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
    }

    private void setUsernameAndFullName() {
        String userUsername = "@" + userProfile.getUsername();
        usernameTv.setText(userUsername);
        String userFullName = userProfile.getFirstName() + " " + userProfile.getLastName();
        fullNameTv.setText(userFullName);
    }

    private void setNavigationMenu() {

        drawerItems.add(new NavigationMenuItem(R.drawable.profile_logo, getResources().getString(R.string.profile), 0));
        drawerItems.add(new NavigationMenuItem(R.drawable.settings_logo, getResources().getString(R.string.settings), 1));
        if (userProfile.getRole() == ERole.ROLE_ADMIN) {
            drawerItems.add(new NavigationMenuItem(R.drawable.admin_setting_logo, getResources().getString(R.string.admin_control), 3));
        }
        drawerItems.add(new NavigationMenuItem(R.drawable.logout_logo, getResources().getString(R.string.logout), 2));

        NavigationMenuItemAdapter adapter = new NavigationMenuItemAdapter(this, R.layout.navigation_list_view_item, drawerItems);
        drawerList.setAdapter(adapter);
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    void setupDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);

        mDrawerToggle.syncState();
    }

    private void selectItem(int position) {

        boolean isItemSelectedValid = true;

        NavigationMenuItem selectedItem = drawerItems.get(position);

        switch (selectedItem.getId()) {
            case 0:
                dashboardPresenter.onProfileClicked();
                break;
            case 1:
                dashboardPresenter.onSettingsCLicked();
                break;
            case 2:
                finishAffinity();
                dashboardPresenter.onLogoutClicked();
                break;
            case 3:
                dashboardPresenter.onAdminSettingsClicked();
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

        if (item.getItemId() == R.id.search_icon) {
            dashboardPresenter.onSearchClicked();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
}