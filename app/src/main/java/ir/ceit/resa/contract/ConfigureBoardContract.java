package ir.ceit.resa.contract;

public interface ConfigureBoardContract {

    interface View {

        void setupActivityView();

        void disableEditBoardButton();

        void enableEditBoardButton();

        void updateBoardConfiguration();

        void onBoardDeleted();

        void showBoardTitleError(String error);

        void showBoardCategoryError(String error);

        void showBoardStatus(String status, boolean isError);

        void clearProblemLayouts();

        void hideGeneralStatusLayout();

        void showToastStatus(String status, boolean isLong);
    }

    interface Presenter {

        void onCreated();

        void onChangeBoardClicked(String title, String category, String faculty);

        void onDeletedBoardClicked();

        void onOpenBoardMembersClicked();
    }
}
