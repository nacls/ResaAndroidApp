package ir.ceit.resa.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import ir.ceit.resa.R;

public class ExitDialog extends Dialog implements
        android.view.View.OnClickListener {

    private final String question;
    private Activity activity;
    private TextView exitQuestionTv;
    private Button yesButton;
    private Button noButton;

    public ExitDialog(Activity activity, String question) {
        super(activity);
        this.activity = activity;
        this.question = question;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.exit_dialog);
        initializeViewComponents();
        setupView();
        yesButton.setOnClickListener(this);
        noButton.setOnClickListener(this);
    }

    private void initializeViewComponents() {
        exitQuestionTv = findViewById(R.id.exit_question_tv);
        yesButton = findViewById(R.id.btn_yes);
        noButton = findViewById(R.id.btn_no);
    }

    private void setupView() {
        if (exitQuestionTv != null)
            exitQuestionTv.setText(question);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                activity.finish();
                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}