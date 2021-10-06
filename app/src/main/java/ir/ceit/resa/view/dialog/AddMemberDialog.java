package ir.ceit.resa.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import ir.ceit.resa.R;
import ir.ceit.resa.service.Constants;
import ir.ceit.resa.view.util.AddMemberDialogListener;

public class AddMemberDialog extends Dialog implements
        View.OnClickListener {

    private Activity activity;
    // Dialog view components
    private EditText usernameEt;
    private CheckBox isWriterCb;
    private Button addUserButton;
    // Listener
    private AddMemberDialogListener listener;

    public AddMemberDialog(Activity activity, AddMemberDialogListener listener) {
        super(activity);
        this.activity = activity;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_member_dialog);

        initializeViewComponents();

        addUserButton.setOnClickListener(this);
    }

    private void initializeViewComponents() {
        usernameEt = findViewById(R.id.add_user_username);
        isWriterCb = findViewById(R.id.is_writer_checkbox);
        addUserButton = findViewById(R.id.add_user_button);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.add_user_button:
                addUserButtonClicked();
                return;
            default:
                break;
        }
        dismiss();

    }

    private void addUserButtonClicked() {
        String username = String.valueOf(usernameEt.getText());
        if (username.isEmpty()) {
            showToast(Constants.USERNAME_ERROR, true);
        } else {
            listener.addUserClicked(username, isWriterCb.isChecked());
            dismiss();
        }
    }

    private void showToast(String status, boolean isLong) {
        if (isLong)
            Toast.makeText(activity, status, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(activity, status, Toast.LENGTH_SHORT).show();
    }
}