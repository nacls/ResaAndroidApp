package ir.ceit.resa.contract;

public interface DashboardContract {

    interface View {

    }

    interface Model{

    }

    interface Presenter {

        void onLogoutClicked();

        void onProfileClicked();

        void onSettingsCLicked();
    }
}
