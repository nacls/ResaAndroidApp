package ir.ceit.resa.contract;

public interface DashboardContract {

    interface View {

        void setupActivityView();

        void showProgressBar();

        void showNoJoinedBoards();

        void showJoinedBoards();
    }

    interface Presenter {

        void onCreated();

        void onLogoutClicked();

        void onProfileClicked();

        void onSettingsCLicked();

        void onSearchClicked();

        void onCreateBoardClicked();

        void onAdminSettingsClicked();
    }
}
