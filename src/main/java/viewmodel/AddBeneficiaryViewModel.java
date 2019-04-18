package viewmodel;

import datamodel.UserDataModel;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import misc.validator.InputValidator;

import java.util.Iterator;

public class AddBeneficiaryViewModel {
    private UserDataModel mUserDataModel;
    private BehaviorSubject<Boolean> validationStream = BehaviorSubject.create();
    private Iterator<Boolean> latestValidation;
    private long mBeneficiaryAccountNumber = -1;
    private long mUserAccount;

    public AddBeneficiaryViewModel(UserDataModel mUserDataModel, long mUserAccount) {
        this.mUserDataModel = mUserDataModel;
        latestValidation = validationStream.blockingMostRecent(false).iterator();
    }

    public Observable<Boolean> getValidationStream() {
        return validationStream;
    }

    public Observable<Boolean> getSuccessListenerStream() {
        return mUserDataModel.getAddBeneficiarySuccessStream();
    }

    public void setBeneficiaryAccNo(String accNo) {
        long acc;
        try {
            acc = Long.parseLong(accNo);
            if (validateAccNo(acc)) {
                validationStream.onNext(true);
                mBeneficiaryAccountNumber = acc;
            }
        } catch (Exception e) {
            validationStream.onNext(false);
        }
    }

    private boolean validateAccNo(long accNo) {
        return InputValidator.validateAccountNumber(accNo);
    }

    public void addBeneficiary() {
        if (latestValidation.next())
            mUserDataModel.addBeneficiary(mBeneficiaryAccountNumber, mUserAccount);
    }
}
