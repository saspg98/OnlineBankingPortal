package viewmodel;

import datamodel.SignupAuthDataModel;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import misc.debug.Debug;
import misc.validator.InputValidator;
import model.SignupCredentials;
import model.changeapi.RxChangeableBase;
import ui.ViewManager;

import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class SignupViewModel {

    private final String TAG = "SignUpViewModel";

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
                InputValidator.validatePassword(signupCredentials.getPassword(),signupCredentials.getCPassword()) &&
                InputValidator.validateAccountNumber(signupCredentials.getAccountNumber()) &&
                InputValidator.validateAdhaar(signupCredentials.getAdhaar()) &&
                InputValidator.validateBCode(signupCredentials.getBCode())&&
                InputValidator.validateEmail(signupCredentials.getEmail())&&
                InputValidator.validatePhoneNumber(signupCredentials.getPhoneNumber());
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

    public void setAdhaar(Long adhaar) {
        mCurrCredentials.setAdhaar(adhaar);
    }

    public void setIFSC(Long BCode) {
        mCurrCredentials.setBCode(BCode);
    }

    public void setAccountNumber(Long accountNumber) {
        mCurrCredentials.setAccountNumber(accountNumber);
    }

    public void setAddress(String address) {
        mCurrCredentials.setAddress(address);
    }


    public void setDOB(Date dob) {
        mCurrCredentials.setDob(dob);
    }

    public void setCPassword(String cpassword) {
        mCurrCredentials.setCPassword(cpassword);
    }

    public void setPhoneNumber(long phoneNumber) {
        mCurrCredentials.setPhoneNumber(phoneNumber);
    }


    public void setUsername(String username) {
        mCurrCredentials.setUsername(username);
    }

    public Observable<Boolean> getValidationStream() {
        return validationStream;
    }

    public Observable<Boolean> getConfirmSignUpStream() {
        return mDataModel.getConfirmSignUpStream();
    }

    public void onSuccessfullSignUp() {
        Debug.log(TAG,"Sign up successful");
        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Debug.err("Application paused");
        }
        ViewManager.getInstance().exitSignUp();
    }

    public void onSignUp() {

        System.out.println("onSignUp Called!!");
        if (latestValidation.next()) {
            Debug.log(TAG, "Sign up was true!!");
            mDataModel.checkSignUpDetails(mCurrCredentials);
        }
    }
}