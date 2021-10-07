package ir.ceit.resa.contract;

import ir.ceit.resa.model.ERole;

public interface AdminSettingContract {

    interface View {

        void setupActivityView();

        void showUsernameError(String error);

        void showNameError(String error);

        void showFamilyNameError(String error);

        void showPasswordError(String error);

        void showEmailError(String error);

        void showStatus(String status, boolean isError);

        void disableSubmitPassButton();

        void enableSubmitPassButton();

        void clearProblemLayouts();

        void showToastStatus(String status, boolean isLong);
    }

    interface Presenter {

        void onCreated();

        void onSubmitUserClicked(String username,
                                 String name, String familyName,
                                 String password,
                                 String email,
                                 ERole role,
                                 String faculty);
    }
}
