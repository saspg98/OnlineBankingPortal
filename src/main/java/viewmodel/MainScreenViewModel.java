package viewmodel;

import datamodel.UserDataModel;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import javafx.scene.layout.GridPane;
import misc.debug.Debug;
import ui.ViewManager;
import viewmodel.constant.Constant;

import javax.swing.text.View;

public class MainScreenViewModel {


    private BehaviorSubject<StateInformation> statePathStream = BehaviorSubject.create();
    private UserDataModel mUserDataModel;
    private GridPane mLastGridPane;
    private StateInformation stateInformation;
    private final String TAG = Constant.Path.MAIN_SCREEN_VIEW;

    public MainScreenViewModel(UserDataModel userdm) {
        mUserDataModel = userdm;
        stateInformation = new StateInformation(Constant.Path.HOME_SCREEN_VIEW, null);
    }

    public Observable<StateInformation> getStateObservable(){
        return statePathStream;
    }

    public void setState(String state) {
        stateInformation.setNextStatePath(state);
        statePathStream.onNext(stateInformation);
    }

    public void setGridPane(GridPane mLastGridPane){
        stateInformation.setCurrentState(mLastGridPane);
    }

    public void onLogout() {

        Debug.log(TAG,"Loging out");
        mUserDataModel.onLogout();
        ViewManager.getInstance().setScene(Constant.Path.LOGIN_VIEW);
    }

    public static class StateInformation{
        String nextStatePath;
        GridPane currentState;

        private StateInformation(String nextStatePath, GridPane currentState) {
            this.nextStatePath = nextStatePath;
            this.currentState = currentState;
        }

        public String getNextStatePath() {
            return nextStatePath;
        }

        public GridPane getCurrentState() {
            return currentState;
        }

        private void setNextStatePath(String nextStatePath) {
            this.nextStatePath = nextStatePath;
        }

        private void setCurrentState(GridPane currentState) {
            this.currentState = currentState;
        }
    }
}
