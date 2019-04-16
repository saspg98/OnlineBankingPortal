package viewmodel;

import datamodel.UserDataModel;
import ui.ViewManager;
import viewmodel.constant.Constant;

public class MainScreenViewModel {

    private String mCurrState = Constant.Path.HOME_SCREEN_VIEW;
    private UserDataModel mUserDataModel;

    public MainScreenViewModel(UserDataModel userdm){
        mUserDataModel = userdm;
    }

    public void setState(String state) {
        //TODO: code for discarding current view, should probably call the ViewManager
        this.mCurrState = state;
        ViewManager.getInstance().setScene(mCurrState);
    }



}
