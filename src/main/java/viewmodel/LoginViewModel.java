package viewmodel;

import datamodel.LoginAuthDataModel;
import io.reactivex.Observable;
import misc.InputValidator;
import model.LoginCredentials;
import model.changeapi.RxChangeableBase;

public class LoginViewModel {

    private static final String DEFAULT_EMAIL = "DEFAULT";
    private static final String DEFAULT_PASSWORD = "DEFAULT";

    private LoginCredentials mCurrLoginCredentials;

    private LoginAuthDataModel mDataModel;
    private Observable<Boolean> validationStream;


    public LoginViewModel(LoginAuthDataModel mDataModel) {
        this.mDataModel = mDataModel;
        mCurrLoginCredentials = new LoginCredentials(DEFAULT_EMAIL, DEFAULT_PASSWORD);
        validationStream = RxChangeableBase.observe(mCurrLoginCredentials)
                .map((loginCredentials) -> validateFields(loginCredentials));
    }

    public void setUsername(String mEmail) {
        mCurrLoginCredentials.setUsername(mEmail);
    }

    public void setPassword(String mPassword) {
        mCurrLoginCredentials.setUsername(mPassword);
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
        if (validationStream.blockingLast()) {
            mDataModel.checkAuthorization(mCurrLoginCredentials);
        }
    }

    public void onSuccessfullLogin() {
        //Handle bg tasks
    }
}
