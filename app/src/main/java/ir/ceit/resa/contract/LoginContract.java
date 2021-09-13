package ir.ceit.resa.contract;

import org.jetbrains.annotations.Contract;

public interface LoginContract{

    interface View {

        void setupActivityView();

        void setStatusText(String error, boolean isError);
    }

    interface Presenter {

        void onCreated();

        void onLoginButtonClick(String username, String password);
    }
}
