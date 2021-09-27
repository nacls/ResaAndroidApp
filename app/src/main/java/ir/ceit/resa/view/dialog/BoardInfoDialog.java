package ir.ceit.resa.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import ir.ceit.resa.R;
import ir.ceit.resa.model.Board;

public class BoardInfoDialog extends Dialog implements
        android.view.View.OnClickListener {

    private final Board board;
    // view components
    private ImageView closeIv;
    private TextView boardId;
    private TextView boardTitle;
    private TextView boardCreator;
    private TextView boardCategory;
    private TextView boardFaculty;

    public BoardInfoDialog(Activity activity, Board board) {
        super(activity);
        this.board = board;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.board_info_dialog);
        initializeViewComponents();
        setupViewTexts();

        closeIv.setOnClickListener(this);
    }

    private void initializeViewComponents() {
        boardId = findViewById(R.id.board_id);
        boardTitle = findViewById(R.id.board_title);
        boardCreator = findViewById(R.id.board_creator);
        boardCategory = findViewById(R.id.board_category);
        boardFaculty = findViewById(R.id.board_faculty);
        closeIv = findViewById(R.id.close);
    }

    private void setupViewTexts() {
        String boardIdValue = board.getBoardId();
        String boardTitleValue = board.getDescription();
        String boardCreatorValue = board.getCreatorUsername();
        String boardCategoryValue = board.getCategory();
        String boardFacultyValue = board.getFaculty();

        boardId.setText(boardIdValue);
        boardTitle.setText(boardTitleValue);
        boardCreator.setText(boardCreatorValue);
        boardCategory.setText(boardCategoryValue);
        boardFaculty.setText(boardFacultyValue);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.close) {
            dismiss();
        }
    }
}
