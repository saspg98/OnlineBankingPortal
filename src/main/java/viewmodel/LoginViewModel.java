package viewmodel;

import datamodel.LoginAuthDataModel;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import misc.debug.Debug;
import misc.validator.InputValidator;
import model.LoginCredentials;
import model.changeapi.RxChangeableBase;
import ui.ViewManager;
import viewmodel.constant.Constant;

import java.util.Iterator;

public class LoginViewModel {

    private static final String DEFAULT_EMAIL = "DEFAULT";
    private static final String DEFAULT_PASSWORD = "DEFAULT";
    private static final String TAG = "LoginViewModel";

    private LoginCredentials mCurrLoginCredentials;
    private LoginAuthDataModel mDataModel;
    private Observable<Boolean> validationStream;
    private Iterator<Boolean> latestValidation;
    private boolean latestValue;


    public LoginViewModel(LoginAuthDataModel mDataModel) {
//        Debug.err("Making new model");
        this.mDataModel = mDataModel;
        mCurrLoginCredentials = new LoginCredentials(DEFAULT_EMAIL, DEFAULT_PASSWORD);
        validationStream = RxChangeableBase.observe(mCurrLoginCredentials)
                .observeOn(Schedulers.computation())
                .map((loginCredentials) -> {
                    //Debug.printThread(TAG);
//                    Debug.log(TAG, "New Login creds are ", loginCredentials.getUsername(), " , "
//                            , loginCredentials.getPassword());
                    boolean validity = validateFields(loginCredentials);
//                    Debug.log(TAG, "Returning validity of login creds ", validity);
                    latestValue = validity;
                    return validity;
                });
        latestValidation = validationStream.blockingLatest().iterator();
    }

    public void setUsername(String mEmail) {
        mCurrLoginCredentials.setUsername(mEmail);
    }

    public void setPassword(String mPassword) {
        mCurrLoginCredentials.setPassword(mPassword);
    }

    public boolean validateFields(LoginCredentials loginCredentials) {
        return InputValidator.validateUsername(loginCredentials.getUsername()) &&
                InputValidator.validatePassword(loginCredentials.getPassword());
    }

    public Observable<Boolean> getValidationStream() {
        return validationStream;
    }

    public Observable<Boolean> getAuthorizationStream() {
        return mDataModel.getAuthorizationStream();
    }

    public void onLogin() {
        //Debug.printThread(TAG);
        System.out.println("onLogin Called!!");
        Debug.log(TAG, latestValidation.next());
        if (latestValue) {
            Debug.log(TAG, "Login was true!!");
            mDataModel.checkAuthorization(mCurrLoginCredentials);
        }
    }

    public void onSuccessfullLogin() {
        //Debug.printThread(TAG);
        Debug.log(TAG, "Successful login!!");
        ViewManager.getInstance().setUid(mDataModel.getUidStream());
        ViewManager.getInstance().setScene(Constant.Path.SIDE_PANE);
    }

    public void onSignUp(String signUpView) {
        ViewManager.getInstance().createSignUp(signUpView);
    }
}
