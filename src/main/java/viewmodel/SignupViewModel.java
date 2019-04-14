package viewmodel;

import datamodel.SignupAuthDataModel;
import io.reactivex.Observable;
import misc.InputValidator;
import model.SignupCredentials;
import model.changeapi.RxChangeableBase;

public class SignupViewModel {

    private SignupCredentials mCurrCredentials = new SignupCredentials();
    private SignupAuthDataModel mDataModel;
    private Observable<Boolean> validationStream;

    public SignupViewModel(SignupCredentials mCurrCredentials) {
        this.mCurrCredentials = mCurrCredentials;
        validationStream = RxChangeableBase.observe(mCurrCredentials)
                .map((signupCredentials)-> validateFields(signupCredentials));
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

    public Observable<Boolean> getValidationStream(){
        return validationStream;
    }

    public Observable<Boolean> getAuthorizationStream(){
        return mDataModel.getAuthorizationStream();
    }

    public void onLogin(){
        //Check the last value in the validation stream, proceed if true
        if(validationStream.blockingLast()){
            mDataModel.checkAuthorization(mCurrCredentials);
        }
    }

}