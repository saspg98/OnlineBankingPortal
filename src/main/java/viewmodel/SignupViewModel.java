package viewmodel;

import datamodel.SignupAuthDataModel;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import misc.InputValidator;
import model.SignupCredentials;
import model.changeapi.RxChangeableBase;

import java.util.Iterator;

public class SignupViewModel {

    private SignupCredentials mCurrCredentials = new SignupCredentials();
    private SignupAuthDataModel mDataModel;
    private Observable<Boolean> validationStream;
    private Iterator<Boolean> latestValidation;

    public SignupViewModel(SignupAuthDataModel mDataModel) {
        this.mDataModel = mDataModel;
        validationStream = RxChangeableBase.observe(mCurrCredentials)
                .observeOn(Schedulers.computation())
                .map(this::validateFields);
        latestValidation = validationStream.blockingMostRecent(false).iterator();
    }

    private boolean validateFields(SignupCredentials signupCredentials) {
        return InputValidator.validateEmail(signupCredentials.getEmail()) &&
                InputValidator.validatePassword(signupCredentials.getPassword()) &&
                InputValidator.validateAccountNumber(signupCredentials.getAccountNumber()) &&
                InputValidator.validateAdhaar(signupCredentials.getAdhaar()) &&
                InputValidator.validateIFSC(signupCredentials.getIFSC());
    }

    public void setName(String name) {
        mCurrCredentials.setName(name);
    }

    public void setEmail(String email) {
        mCurrCredentials.setEmail(email);
    }

    public void setPassword(String password) {
        mCurrCredentials.setPassword(password);
    }


    public void setAdhaar(String adhaar) {
        mCurrCredentials.setAdhaar(adhaar);
    }

    public void setIFSC(String IFSC) {
        mCurrCredentials.setIFSC(IFSC);
    }


    public void setAccountNumber(Long accountNumber) {
        mCurrCredentials.setAccountNumber(accountNumber);
    }

    public Observable<Boolean> getValidationStream() {
        return validationStream;
    }

    public Observable<Boolean> getAuthorizationStream() {
        return mDataModel.getAuthorizationStream();
    }

    public void onLogin() {
        //Check the last value in the validation stream, proceed if true
        if (latestValidation.next()) {
            mDataModel.checkAuthorization(mCurrCredentials);
        }
    }

}