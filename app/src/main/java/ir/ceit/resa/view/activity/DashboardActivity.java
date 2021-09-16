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
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

import ir.ceit.resa.R;
import ir.ceit.resa.contract.DashboardContract;
import ir.ceit.resa.model.ERole;
import ir.ceit.resa.model.view.NavigationMenuItem;
import ir.ceit.resa.presenter.DashboardActivityPresenter;
import ir.ceit.resa.view.adapter.NavigationMenuItemAdapter;

public class DashboardActivity extends AppCompatActivity implements DashboardContract.View {

    private DashboardActivityPresenter dashboardPresenter;
    // Toolbar components
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    ArrayList<NavigationMenuItem> drawerItems = new ArrayList<>();
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    // Profile components
    private TextView usernameTv;
    private TextView fullNameTv;
    private ImageView avatarIv;
    // For admin and creator
    private FloatingActionButton createBoardBtn;
    // Loading boards components
    private RecyclerView boardsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        dashboardPresenter = new DashboardActivityPresenter(this,
                this);

        dashboardPresenter.onCreated();
    }

    @Override
    public void setupActivityView() {

        initializeViewComponents();

        setViewBasedOnRole();
        setupToolbar();
        setUsernameAndFullName();
        setNavigationMenu();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawerLayout = findViewById(R.id.dashboard_layout);
        drawerLayout.setDrawerListener(drawerToggle);
        setupDrawerToggle();
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void showNoJoinedBoards() {

    }

    @Override
    public void showJoinedBoards() {

    }

    private void initializeViewComponents() {
        drawerLayout = findViewById(R.id.dashboard_layout);
        drawerList = findViewById(R.id.menu_items_list);
        usernameTv = findViewById(R.id.userUsernameNav);
        fullNameTv = findViewById(R.id.userNameNav);
        avatarIv = findViewById(R.id.header_avatar);
        createBoardBtn = findViewById(R.id.add_board_button);
    }

    private void setViewBasedOnRole() {
        switch (dashboardPresenter.getUserProfile().getRole()) {
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
        String userUsername = "@" + dashboardPresenter.getUserProfile().getUsername();
        usernameTv.setText(userUsername);
        String userFullName = dashboardPresenter.getUserProfile().getFirstName()
                + " " + dashboardPresenter.getUserProfile().getLastName();
        fullNameTv.setText(userFullName);
    }

    private void setNavigationMenu() {
        drawerItems.add(new NavigationMenuItem(R.drawable.profile_logo, getResources().getString(R.string.profile), 0));
        drawerItems.add(new NavigationMenuItem(R.drawable.settings_logo, getResources().getString(R.string.settings), 1));
        if (dashboardPresenter.getUserProfile().getRole() == ERole.ROLE_ADMIN) {
            drawerItems.add(new NavigationMenuItem(R.drawable.admin_setting_logo, getResources().getString(R.string.admin_control), 3));
        }
        drawerItems.add(new NavigationMenuItem(R.drawable.logout_logo, getResources().getString(R.string.logout), 2));

        NavigationMenuItemAdapter adapter = new NavigationMenuItemAdapter(this, R.layout.navigation_list_view_item, drawerItems);
        drawerList.setAdapter(adapter);
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    void setupDrawerToggle() {
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);

        drawerToggle.syncState();
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
        if (drawerToggle.onOptionsItemSelected(item)) {
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
        drawerToggle.syncState();
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