package ir.ceit.resa.contract;

public interface CreateBoardContract {

    interface View {

        void setupActivityView();

        void showBoardIdError(String error);

        void showBoardTitleError(String error);

        void showBoardCategoryError(String error);

        void showBoardStatus(String status, boolean isError);

        void disableSubmitButton();

        void enableSubmitButton();

        void clearProblemLayouts();
    }

    interface Presenter {

        void onCreated();

        void submitBoardClicked(String boardId, String boardTitle, String boardCategory, String boardFaculty);
    }
}

