package ir.ceit.resa.contract;

public interface UserSettingContract {

    interface View {

        void setupActivityView();

        void showToastStatus(String status, boolean isLong);
    }

    interface Presenter {

        void onCreated();

        void changePasswordClicked(String password);
    }
}
