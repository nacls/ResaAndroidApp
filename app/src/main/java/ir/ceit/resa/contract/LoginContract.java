package ir.ceit.resa.contract;

public interface LoginContract {

    interface View {

        void setupActivityView();

        void setStatusText(String error, boolean isError);
    }

    interface Presenter {

        void onCreated();

        void onLoginButtonClick(String username, String password);
    }
}
