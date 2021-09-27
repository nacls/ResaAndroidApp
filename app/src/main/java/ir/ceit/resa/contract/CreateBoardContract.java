package ir.ceit.resa.contract;

public interface CreateBoardContract {

    interface View {

        void setupActivityView();
    }

    interface Presenter {

        void onCreated();
    }
}
