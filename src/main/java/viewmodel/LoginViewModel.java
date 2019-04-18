package viewmodel;

import datamodel.LoginAuthDataModel;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import misc.InputValidator;
import misc.debug.Debug;
import model.LoginCredentials;
import model.changeapi.RxChangeableBase;

import java.util.Iterator;

public class LoginViewModel {

    private static final String DEFAULT_EMAIL = "DEFAULT";
    private static final String DEFAULT_PASSWORD = "DEFAULT";

    private static final String TAG = "LoginViewModel";
    private LoginCredentials mCurrLoginCredentials;

    private LoginAuthDataModel mDataModel;
    private Observable<Boolean> validationStream;
    private Iterator<Boolean> latestValidation;


    public LoginViewModel(LoginAuthDataModel mDataModel) {
        this.mDataModel = mDataModel;
        mCurrLoginCredentials = new LoginCredentials(DEFAULT_EMAIL, DEFAULT_PASSWORD);
        validationStream = RxChangeableBase.observe(mCurrLoginCredentials)
                .observeOn(Schedulers.computation())
                .map((loginCredentials) -> {
                    Debug.printThread(TAG);
                    Debug.log(TAG, "New Login creds are ", loginCredentials.getUsername(), " , "
                            , loginCredentials.getPassword());
                    boolean validity = validateFields(loginCredentials);
                    Debug.log(TAG, "Returning validity of login creds ", validity);
                    return validity;
                });
        latestValidation = validationStream.blockingMostRecent(false).iterator();
    }

    public void setUsername(String mEmail) {
        mCurrLoginCredentials.setUsername(mEmail);
    }

    public void setPassword(String mPassword) {
        mCurrLoginCredentials.setPassword(mPassword);
    }

    public boolean validateFields(LoginCredentials loginCredentials) {
        return InputValidator.validateEmail(loginCredentials.getUsername()) &&
                InputValidator.validatePassword(loginCredentials.getPassword());
    }

    public Observable<Boolean> getValidationStream() {
        return validationStream;
    }

    public Observable<Boolean> getAuthorizationStream() {
        return mDataModel.getAuthorizationStream();
    }

    public void onLogin() {
        //Check the last value in the validation stream, proceed if true
        Debug.printThread(TAG);
        System.out.println("onLogin Called!!");
        Debug.log(TAG, latestValidation.next());
        if (latestValidation.next()) {
            Debug.log(TAG, "Login was true!!");
            mDataModel.checkAuthorization(mCurrLoginCredentials);
        }
    }

    public void onSuccessfullLogin() {
        //Handle bg tasks
        Debug.printThread(TAG);
        Debug.err(TAG, "Successful login!!");
    }
}
