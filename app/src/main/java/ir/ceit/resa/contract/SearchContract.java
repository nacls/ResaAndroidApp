package ir.ceit.resa.contract;

public interface SearchContract {

    interface View {

        void setupActivityView();
    }

    interface Presenter {

        void onCreated();
    }
}
