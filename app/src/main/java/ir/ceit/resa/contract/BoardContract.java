package ir.ceit.resa.contract;

import java.util.List;

import ir.ceit.resa.model.Announcement;
import ir.ceit.resa.model.Board;

public interface BoardContract {

    interface View {

        void setupActivityView();

        void showProgressBar();

        void showNoAnnouncements(String status);

        void showAnnouncements(List<Announcement> announcements);

        void showToastAnnouncementStatus(String status, boolean isLong);

        void updateToolbarTitle();

        void openBoardInfoDialog(Board board);

        void openConfigureBoardActivity(Board board);

        void finishActivity();
    }

    interface Presenter {

        void onCreated();

        void onInfoEditClicked();

        void setWritingMode(boolean inWritingMode);

        void addAnnouncementIvClicked(String message);

        void returnFromConfigureBoardActivity(Board board);
    }
}
