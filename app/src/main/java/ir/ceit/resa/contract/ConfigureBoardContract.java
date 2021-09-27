package ir.ceit.resa.contract;

public interface ConfigureBoardContract {

    interface View {

        void setupActivityView();
    }

    interface Presenter {

        void onCreated();
    }
}
