package ir.ceit.resa.contract;

public interface SplashContract {

    interface View {

        void startProgressThread();

        void setupActivityView();
    }

    interface Presenter {

        void onCreated();

        boolean isUserAlreadyLoggedIn();

        void openLoginActivity();

        void loadDashboardActivity();
    }
}
