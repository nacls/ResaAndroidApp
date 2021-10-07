package ir.ceit.resa.contract;

public interface UserSettingContract {

    interface View {

        void setupActivityView();

        void showOldPassError(String error);

        void showNewPassError(String error);

        void showChangePassStatus(String status, boolean isError);

        void disableSubmitPassButton();

        void enableSubmitPassButton();

        void clearProblemLayouts();

        void showToastStatus(String status, boolean isLong);
    }

    interface Presenter {

        void onCreated();

        void changePasswordClicked(String oldPassword, String newPass, String newPassRepeat);
    }
}
