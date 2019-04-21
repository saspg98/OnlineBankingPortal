package viewmodel;

import datamodel.UserDataModel;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import ui.ViewManager;
import viewmodel.constant.Constant;

public class MainScreenViewModel {

    private String mCurrState = Constant.Path.HOME_SCREEN_VIEW;
    private BehaviorSubject<String> statePathStream = BehaviorSubject.create();
    private UserDataModel mUserDataModel;

    public MainScreenViewModel(UserDataModel userdm) {
        mUserDataModel = userdm;
    }

    public Observable<String> getStateObservable(){
        return statePathStream;
    }

    public void setState(String state) {
        //TODO: code for discarding current view, should probably call the ViewManager
        this.mCurrState = state;
        statePathStream.onNext(mCurrState);
//        ViewManager.getInstance().setScene(mCurrState);
    }


    public void onLogout() {
        //TODO: Handle Logout Click
    }
}
