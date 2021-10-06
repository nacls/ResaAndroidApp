package ir.ceit.resa.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import ir.ceit.resa.R;
import ir.ceit.resa.contract.SearchContract;
import ir.ceit.resa.model.Board;
import ir.ceit.resa.presenter.SearchBoardActivityPresenter;
import ir.ceit.resa.view.adapter.SearchResultAdapter;
import ir.ceit.resa.view.util.RecyclerViewOffsetDecoration;

public class SearchBoardActivity extends AppCompatActivity implements SearchContract.View {

    private SearchBoardActivityPresenter searchPresenter;
    // Toolbar components
    private Toolbar toolbar;
    private SearchView searchView;
    // status components
    private LinearLayout searchStatusLayout;
    private TextView searchStatusTv;
    private ImageView searchStatusIv;
    // progress component
    private ProgressBar searchProgressBar;
    // board result components
    private RecyclerView resultRv;

    private SearchResultAdapter resultAdapter;

    private String latestQuery = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_board);

        searchPresenter = new SearchBoardActivityPresenter(this, this);

        searchPresenter.onCreated();
    }

    @Override
    public void setupActivityView() {
        initializeViewComponents();
        setupToolbar();
        setupSearchView();
        setupRecyclerView();
    }

    @Override
    public void showProgress() {
        resultRv.setVisibility(View.GONE);
        searchStatusLayout.setVisibility(View.GONE);
        searchProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSearchResult(List<Board> boards) {
        searchProgressBar.setVisibility(View.GONE);
        searchStatusLayout.setVisibility(View.GONE);
        resultRv.setVisibility(View.VISIBLE);
        resultAdapter = new SearchResultAdapter(boards, latestQuery);
        resultRv.setAdapter(resultAdapter);
    }

    @Override
    public void showSearchResultProblem(String status, int statusImage) {
        resultRv.setVisibility(View.GONE);
        searchProgressBar.setVisibility(View.GONE);
        searchStatusLayout.setVisibility(View.VISIBLE);
        searchStatusTv.setText(status);
        switch (statusImage) {
            case 1: // no boards to show
                searchStatusIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.no_boards));
                break;
            case 2: // network problem
            default:
                searchStatusIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.boards_problem));
                break;
        }
    }

    private void initializeViewComponents() {
        toolbar = findViewById(R.id.search_toolbar);
        searchView = findViewById(R.id.searchView);
        searchStatusLayout = findViewById(R.id.search_status_layout);
        searchStatusIv = findViewById(R.id.searchStatusIv);
        searchStatusTv = findViewById(R.id.searchStatusTv);
        resultRv = findViewById(R.id.boardResultRv);
        searchProgressBar = findViewById(R.id.searchProgressBar);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.search_board));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                latestQuery = query;
                searchPresenter.searchButtonClicked(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchPresenter.searchButtonClicked(latestQuery);
    }

    private void setupRecyclerView() {
        resultRv.setLayoutManager(new LinearLayoutManager(this));
        float offsetPx = getResources().getDimension(R.dimen.board_bottom_offset_dp);
        RecyclerViewOffsetDecoration bottomOffsetDecoration = new RecyclerViewOffsetDecoration((int) offsetPx, false, false, 0, true);
        resultRv.addItemDecoration(bottomOffsetDecoration);
    }

}