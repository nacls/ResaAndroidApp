package ir.ceit.resa.view.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import java.util.Objects;

import ir.ceit.resa.R;
import ir.ceit.resa.contract.CreateBoardContract;
import ir.ceit.resa.presenter.CreateBoardActivityPresenter;
import ir.ceit.resa.view.dialog.AssuranceDialog;

public class CreateBoardActivity extends AppCompatActivity implements CreateBoardContract.View {

    private CreateBoardActivityPresenter createBoardPresenter;
    // view
    private Toolbar toolbar;
    // board id
    private EditText boardIdEt;
    private LinearLayout boardIdProblemLayout;
    private TextView boardIdProblemTv;
    // board title
    private EditText boardTitleEt;
    private LinearLayout boardTitleProblemLayout;
    private TextView boardTitleProblemTv;
    // board category
    private EditText boardCategoryEt;
    private LinearLayout boardCategoryProblemLayout;
    private TextView boardCategoryProblemTv;
    // board faculty
    private EditText boardFacultyEt;
    // board submit status
    private LinearLayout boardStatusLayout;
    private TextView statusDescriptionTv;
    private ImageView statusIv;
    // submit button
    private Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_board);

        createBoardPresenter = new CreateBoardActivityPresenter(this, this);

        createBoardPresenter.onCreated();
    }

    @Override
    public void setupActivityView() {
        initializeViewComponents();
        hideProblemLayouts();
        setupToolbar();
        registerSubmitButton();
    }

    @Override
    public void showBoardIdError(String error) {
        boardIdProblemLayout.setVisibility(View.VISIBLE);
        boardIdProblemTv.setText(error);
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
        boardStatusLayout.setVisibility(View.VISIBLE);
        statusDescriptionTv.setText(status);
        int color;
        if (isError) {
            color = ResourcesCompat.getColor(getResources(), R.color.error_red, null);
            statusIv.setBackground(ContextCompat.getDrawable(this, R.drawable.error_red));
        } else {
            color = ResourcesCompat.getColor(getResources(), R.color.success_green, null);
            statusIv.setBackground(ContextCompat.getDrawable(this, R.drawable.ok));
        }
        statusDescriptionTv.setTextColor(color);
    }

    @Override
    public void disableSubmitButton() {
        submit.setEnabled(false);
    }

    @Override
    public void enableSubmitButton() {
        submit.setEnabled(true);
    }

    @Override
    public void clearProblemLayouts() {
        hideProblemLayouts();
    }

    private void initializeViewComponents() {
        toolbar = findViewById(R.id.create_board_toolbar);
        // Board Id
        boardIdEt = findViewById(R.id.board_id_create);
        boardIdProblemLayout = findViewById(R.id.board_id_problem_layout);
        boardIdProblemTv = findViewById(R.id.board_id_problem_description);
        // Board Title
        boardTitleEt = findViewById(R.id.board_title_create);
        boardTitleProblemLayout = findViewById(R.id.board_title_problem_layout);
        boardTitleProblemTv = findViewById(R.id.board_title_problem_description);
        // Board Category
        boardCategoryEt = findViewById(R.id.board_category_create);
        boardCategoryProblemLayout = findViewById(R.id.board_category_problem_layout);
        boardCategoryProblemTv = findViewById(R.id.board_category_problem_description);
        // Board Faculty
        boardFacultyEt = findViewById(R.id.board_faculty_create);
        // Status
        boardStatusLayout = findViewById(R.id.create_board_status_layout);
        statusDescriptionTv = findViewById(R.id.create_board_status_description);
        statusIv = findViewById(R.id.create_board_status_image);
        // Submit Button
        submit = findViewById(R.id.submit_board);
    }

    private void hideProblemLayouts() {
        boardIdProblemLayout.setVisibility(View.GONE);
        boardTitleProblemLayout.setVisibility(View.GONE);
        boardCategoryProblemLayout.setVisibility(View.GONE);
        boardStatusLayout.setVisibility(View.GONE);
    }

    private void registerSubmitButton() {
        submit.setOnClickListener(v -> {
            hideProblemLayouts();
            notifyPresenterOfSubmit();
        });
    }

    private void notifyPresenterOfSubmit() {
        String boardId = String.valueOf(boardIdEt.getText());
        String boardTitle = String.valueOf(boardTitleEt.getText());
        String boardCategory = String.valueOf(boardCategoryEt.getText());
        String boardFaculty = String.valueOf(boardFacultyEt.getText());
        createBoardPresenter.submitBoardClicked(boardId, boardTitle, boardCategory, boardFaculty);
    }


    private void setupToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.create_new_board));

        toolbar.setNavigationOnClickListener(v -> openExitDialog());
    }

    @Override
    public void onBackPressed() {
        openExitDialog();
    }

    private void openExitDialog() {
        String exitQuestion = getResources().getString(R.string.exit_board_question) + "\n" +
                getResources().getString(R.string.sure_to_exit);

        AssuranceDialog exitDialog = new AssuranceDialog(this, exitQuestion);
        exitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        exitDialog.show();
    }
}