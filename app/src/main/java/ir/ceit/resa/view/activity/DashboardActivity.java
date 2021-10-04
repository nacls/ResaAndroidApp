package ir.ceit.resa.view.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ir.ceit.resa.R;
import ir.ceit.resa.contract.DashboardContract;
import ir.ceit.resa.model.Board;
import ir.ceit.resa.model.ERole;
import ir.ceit.resa.model.view.NavigationMenuItem;
import ir.ceit.resa.presenter.DashboardActivityPresenter;
import ir.ceit.resa.service.Constants;
import ir.ceit.resa.view.adapter.BoardsAdapter;
import ir.ceit.resa.view.adapter.NavigationMenuItemAdapter;
import ir.ceit.resa.view.util.RecyclerViewOffsetDecoration;

public class DashboardActivity extends AppCompatActivity implements DashboardContract.View {

    ArrayList<NavigationMenuItem> drawerItems = new ArrayList<>();
    private DashboardActivityPresenter dashboardPresenter;
    // Toolbar components
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    // Profile components
    private TextView usernameTv;
    private TextView fullNameTv;
    private ImageView avatarIv;
    // For admin and creator
    private FloatingActionButton createBoardBtn;
    // Loading boards components
    private SwipeRefreshLayout swipeContainer;
    private FrameLayout contentLayout;
    private RecyclerView boardsRv;
    private LinearLayout boardsProblem;
    private TextView boardsStatusTv;
    private ProgressBar boardsProgressBar;
    private ImageView problemIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        dashboardPresenter = new DashboardActivityPresenter(this, this);

        dashboardPresenter.onCreated();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dashboardPresenter.getUserJoinedBoardsFromServer();
    }

    @Override
    public void setupActivityView() {

        initializeViewComponents();

        setViewBasedOnRole();
        setupToolbar();
        setupRecyclerView();
        setUsernameAndFullName();
        setNavigationMenu();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawerLayout.setDrawerListener(drawerToggle);
        setupDrawerToggle();
        setOnSwipe();
    }

    @Override
    public void showProgressBar() {
        boardsRv.setVisibility(View.GONE);
        boardsProblem.setVisibility(View.GONE);
        boardsProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoJoinedBoards(String status) {
        boardsProgressBar.setVisibility(View.GONE);
        boardsRv.setVisibility(View.GONE);
        boardsProblem.setVisibility(View.VISIBLE);
        boardsStatusTv.setText(status);
        boardsStatusTv.setVisibility(View.VISIBLE);
        swipeContainer.setRefreshing(false);
        if (status.equals(Constants.NO_BOARDS_TO_SHOW)) {
            problemIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.no_boards));
        } else {
            problemIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.boards_problem));
        }
    }

    @Override
    public void showJoinedBoards(List<Board> boards) {
        boardsProgressBar.setVisibility(View.GONE);
        boardsProblem.setVisibility(View.GONE);
        boardsRv.setVisibility(View.VISIBLE);
        BoardsAdapter adapter = new BoardsAdapter(boards);
        // Attach the adapter to the recyclerview to populate items
        boardsRv.setAdapter(adapter);
        swipeContainer.setRefreshing(false);
    }


    private void initializeViewComponents() {
        drawerLayout = findViewById(R.id.dashboard_layout);
        drawerList = findViewById(R.id.menu_items_list);
        usernameTv = findViewById(R.id.userUsernameNav);
        fullNameTv = findViewById(R.id.userNameNav);
        avatarIv = findViewById(R.id.header_avatar);
        createBoardBtn = findViewById(R.id.add_board_button);
        boardsRv = findViewById(R.id.boardsRv);
        boardsProblem = findViewById(R.id.container_boards_problem);
        boardsStatusTv = findViewById(R.id.boardsStatusTv);
        boardsProgressBar = findViewById(R.id.boardsProgressBar);
        contentLayout = findViewById(R.id.content_frame);
        swipeContainer = findViewById(R.id.swipe_container);
        problemIv = findViewById(R.id.boardsProblemIv);
    }

    private void setupRecyclerView() {
        // Set layout manager to position the items
        boardsRv.setLayoutManager(new LinearLayoutManager(this));
        float offsetPx = getResources().getDimension(R.dimen.board_bottom_offset_dp);
        RecyclerViewOffsetDecoration bottomOffsetDecoration = new RecyclerViewOffsetDecoration((int) offsetPx, false, false, 0, true);
        boardsRv.addItemDecoration(bottomOffsetDecoration);
    }

    private void setOnSwipe() {
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dashboardPresenter.getUserJoinedBoardsFromServer();
            }
        });
    }

    private void setViewBasedOnRole() {
        switch (dashboardPresenter.getUserProfile().getRole()) {
            case ROLE_USER:
                avatarIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.user_avatar));
                createBoardBtn.setVisibility(View.GONE);
                break;
            case ROLE_ADMIN:
                avatarIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.admin_avatar));
                setupCreateBoardButton();
                break;
            case ROLE_CREATOR:
                avatarIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.creator_avatar));
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