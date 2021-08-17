package ir.ceit.resa.contract;

public interface SplashContract {
    interface View {

        void startProgressThread();
    }

    interface Presenter {

        void onCreated();

        boolean isUserAlreadyLoggedIn();

        void openLoginActivity();

        void loadDashboardActivity();
    }
}
