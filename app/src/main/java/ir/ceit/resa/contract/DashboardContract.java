package ir.ceit.resa.contract;

import java.util.List;

import ir.ceit.resa.model.Board;

public interface DashboardContract {

    interface View {

        void setupActivityView();

        void showProgressBar();

        void showProgressRefresh();

        void showNoJoinedBoards(String status);

        void showJoinedBoards(List<Board> boards);
    }

    interface Presenter {

        void onCreated();

        void onLogoutClicked();

        void onProfileClicked();

        void onSettingsCLicked();

        void onSearchClicked();

        void onCreateBoardClicked();

        void onAdminSettingsClicked();

        void onRefresh();
    }
}
