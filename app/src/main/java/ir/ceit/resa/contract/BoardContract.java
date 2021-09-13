package ir.ceit.resa.contract;

public interface BoardContract {

    interface View {

        void setupActivityView();
    }

    interface Presenter {

        void onCreated();

    }
}
