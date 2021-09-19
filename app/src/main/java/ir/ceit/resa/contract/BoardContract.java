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
    }

    interface Presenter {

        void onCreated();

        void onInfoEditClicked();

    }
}
