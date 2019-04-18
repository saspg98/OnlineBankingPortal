package viewmodel;

import datamodel.SignupAuthDataModel;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import misc.debug.Debug;
import misc.validator.InputValidator;
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
        return InputValidator.validateUsername(signupCredentials.getEmail()) &&
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

    public void setAadhaar(String adhaar) {
        mCurrCredentials.setAdhaar(adhaar);
    }

    public void setIFSC(String IFSC) {
        mCurrCredentials.setIFSC(IFSC);
    }

    public void setAccountNumber(Long accountNumber) {
        mCurrCredentials.setAccountNumber(accountNumber);
    }

    public void setAddress(String trim) {
    }

    public void setCity(String trim) {
    }

    public void setDOB(String trim) {
    }

    public void setCPassword(String trim) {
    }

    public void setPhoneNumber(long parseLong) {
    }

    public void setPinCode(int parseInt) {
    }

    public void setUsername(String trim) {
    }

    public Observable<Boolean> getValidationStream() {
        return validationStream;
    }

    public Observable<Boolean> getConfirmSignUpStream() {
        return mDataModel.getAuthorizationStream();
    }

    public void onSuccessfullSignUp() {
    }

    public void onSignUp() {

        System.out.println("onSignUp Called!!");
        if (latestValidation.next()) {
            Debug.log("SignUpView", "Sign up was true!!");
            mDataModel.checkAuthorization(mCurrCredentials);
        }
    }
}