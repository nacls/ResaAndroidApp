package ir.ceit.resa.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import java.util.Objects;

import ir.ceit.resa.R;
import ir.ceit.resa.contract.ConfigureBoardContract;
import ir.ceit.resa.model.Board;
import ir.ceit.resa.presenter.ConfigureBoardActivityPresenter;
import ir.ceit.resa.service.Constants;
import ir.ceit.resa.view.dialog.AssuranceDialog;
import ir.ceit.resa.view.util.AssuranceDialogListener;

public class ConfigureBoardActivity extends AppCompatActivity implements ConfigureBoardContract.View {

    private ConfigureBoardActivityPresenter configurePresenter;
    // Toolbar
    private Toolbar toolbar;
    // Edit Board Components
    // Id
    private EditText boardIdEt;
    // Title
    private EditText boardTitleEt;
    private LinearLayout boardTitleProblemLayout;
    private TextView boardTitleProblemTv;
    // Category
    private EditText boardCategoryEt;
    private LinearLayout boardCategoryProblemLayout;
    private TextView boardCategoryProblemTv;
    // Faculty
    private EditText boardFacultyEt;
    // General Problem
    private LinearLayout editBoardStatusLayout;
    private TextView editBoardStatusTv;
    private ImageView statusIv;
    // Submit changes
    private Button submitChangesButton;
    // Access Control Components
    private LinearLayout accessControlLayout;
    // Delete Board Components
    private Button deleteBoardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_board);

        Board board = (Board) getIntent().getSerializableExtra("board");

        configurePresenter = new ConfigureBoardActivityPresenter(this, this, board);

        configurePresenter.onCreated();
    }

    @Override
    public void setupActivityView() {
        initializeViewComponents();
        clearProblemLayouts();
        setupToolbar();
        setupConfigureBoardView();
        registerSubmitButton();
        registerAccessControlLayout();
        registerDeleteButton();
    }

    @Override
    public void disableEditBoardButton() {
        submitChangesButton.setEnabled(false);
    }

    @Override
    public void enableEditBoardButton() {
        submitChangesButton.setEnabled(true);
    }

    @Override
    public void updateBoardConfiguration() {
        setupConfigureBoardView();
    }

    @Override
    public void onBoardDeleted() {
        returnResult();
    }

    @Override
    public void showBoardTitleError(String error) {
        boardTitleProblemLayout.setVisibility(View.VISIBLE);
        boardTitleProblemTv.setText(error);
    }

    @Override
    public void showBoardCategoryError(String error) {
        boardCategoryProblemLayout.setVisibility(View.VISIBLE);
        boardCategoryProblemTv.setText(error);
    }

    @Override
    public void showBoardStatus(String status, boolean isError) {
        editBoardStatusLayout.setVisibility(View.VISIBLE);
        editBoardStatusTv.setText(status);
        int color;
        if (isError) {
            color = ResourcesCompat.getColor(getResources(), R.color.error_red, null);
            statusIv.setBackground(ContextCompat.getDrawable(this, R.drawable.error_red));
        } else {
            color = ResourcesCompat.getColor(getResources(), R.color.success_green, null);
            statusIv.setBackground(ContextCompat.getDrawable(this, R.drawable.ok));
        }
        editBoardStatusTv.setTextColor(color);
    }

    @Override
    public void clearProblemLayouts() {
        boardTitleProblemLayout.setVisibility(View.GONE);
        boardCategoryProblemLayout.setVisibility(View.GONE);
        editBoardStatusLayout.setVisibility(View.GONE);
    }

    @Override
    public void hideGeneralStatusLayout() {
        editBoardStatusLayout.setVisibility(View.GONE);
    }

    @Override
    public void showToastStatus(String status, boolean isLong) {
        if (isLong)
            Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
    }

    public void initializeViewComponents() {
        toolbar = findViewById(R.id.configure_board_toolbar);
        // Id
        boardIdEt = findViewById(R.id.board_id_edit);
        // Title
        boardTitleEt = findViewById(R.id.board_title_edit);
        boardTitleProblemLayout = findViewById(R.id.board_title_edit_layout);
        boardTitleProblemTv = findViewById(R.id.board_title_edit_description);
        // Category
        boardCategoryEt = findViewById(R.id.board_category_edit);
        boardCategoryProblemLayout = findViewById(R.id.board_category_edit_layout);
        boardCategoryProblemTv = findViewById(R.id.board_category_edit_description);
        // Faculty
        boardFacultyEt = findViewById(R.id.board_faculty_edit);
        // General status
        editBoardStatusLayout = findViewById(R.id.edit_board_status_layout);
        editBoardStatusTv = findViewById(R.id.edit_board_status_description);
        statusIv = findViewById(R.id.edit_board_status_image);
        // Buttons
        submitChangesButton = findViewById(R.id.configure_board);
        accessControlLayout = findViewById(R.id.manage_access_control_layout);
        deleteBoardButton = findViewById(R.id.delete_board);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.edit_board));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setupConfigureBoardView() {
        boardIdEt.setText(configurePresenter.getBoard().getBoardId());
        boardTitleEt.setText(configurePresenter.getBoard().getDescription());
        boardCategoryEt.setText(configurePresenter.getBoard().getCategory());
        boardFacultyEt.setText(configurePresenter.getBoard().getFaculty());
    }

    private void registerSubmitButton() {
        submitChangesButton.setOnClickListener(v -> notifyPresenterOfSubmitChanges());
    }

    private void registerDeleteButton() {
        deleteBoardButton.setOnClickListener(v -> openAssuranceDialog());
    }

    private void registerAccessControlLayout() {
        accessControlLayout.setOnClickListener(v -> configurePresenter.onOpenBoardMembersClicked());
    }

    private void openAssuranceDialog() {
        String exitQuestion = Constants.BE_SURE_TO_DELETE_BOARD;
        AssuranceDialog exitDialog = new AssuranceDialog(this, exitQuestion, new AssuranceDialogListener() {
            @Override
            public void onRejectClicked() {
                // do nothing
            }

            @Override
            public void onAcceptClicked() {
                configurePresenter.onDeletedBoardClicked();
            }
        });
        exitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        exitDialog.show();
    }

    private void notifyPresenterOfSubmitChanges() {
        String title = String.valueOf(boardTitleEt.getText());
        String category = String.valueOf(boardCategoryEt.getText());
        String faculty = String.valueOf(boardFacultyEt.getText());
        configurePresenter.onChangeBoardClicked(title, category, faculty);
    }

    @Override
    public void onBackPressed() {
        returnResult();
    }

    public void returnResult() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("board", configurePresenter.getBoard());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}