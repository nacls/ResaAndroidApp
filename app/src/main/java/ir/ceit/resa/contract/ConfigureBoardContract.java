package ir.ceit.resa.contract;

public interface ConfigureBoardContract {

    interface View {

        void setupActivityView();

        void disableEditBoardButton();

        void updateBoardConfiguration();

        void onBoardDeleted();
    }

    interface Presenter {

        void onCreated();

        void onChangeBoardClicked();

        void onDeletedBoardClicked();

        void onOpenBoardMembersClicked();
    }
}
