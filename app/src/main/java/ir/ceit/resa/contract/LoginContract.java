package ir.ceit.resa.contract;

import org.jetbrains.annotations.Contract;

public interface LoginContract{

    interface View {

        void setStatusText(String error, boolean isError);
    }

    interface Presenter {

        void onButtonClick(String username, String password);
    }
}
