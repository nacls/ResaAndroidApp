package ir.ceit.resa.presenter;

import android.content.Context;

import ir.ceit.resa.contract.ConfigureBoardContract;
import ir.ceit.resa.contract.CreateBoardContract;

public class CreateBoardActivityPresenter implements CreateBoardContract.Presenter {

    private CreateBoardContract.View view;
    private Context context;

    public CreateBoardActivityPresenter(CreateBoardContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void onCreated() {
        view.setupActivityView();
    }
}