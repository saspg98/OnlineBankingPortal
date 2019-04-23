package viewmodel;

import datamodel.UserDataModel;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import misc.debug.Debug;
import misc.validator.InputValidator;

import java.math.BigInteger;

public class AddBeneficiaryViewModel {
    private UserDataModel mUserDataModel;
    private BehaviorSubject<Boolean> validationStream = BehaviorSubject.create();
    private long mBeneficiaryAccountNumber = -1;
    private final BigInteger mUserAccount;

    public AddBeneficiaryViewModel(UserDataModel mUserDataModel, BigInteger mUserAccount) {
        this.mUserDataModel = mUserDataModel;
        this.mUserAccount = mUserAccount;
        //TODO: Change ALL blockingMostRecent calls

    }

    public Observable<Boolean> getValidationStream() {
        return validationStream.doOnNext((valid) -> {
            Debug.log("AddBenVM", "valid value:", valid);
            if (valid) {
                addBeneficiary();
            }
        });
    }

    public Observable<Boolean> getSuccessListenerStream() {
        return mUserDataModel.getAddBeneficiarySuccessStream();
    }

    public void setBeneficiaryAccNo(String accNo) {
        long acc;
        try {
            acc = Long.parseLong(accNo);
            if (validateAccNo(acc)) {
                mBeneficiaryAccountNumber = acc;
                validationStream.onNext(true);

            }
        } catch (Exception e) {
            validationStream.onNext(false);
        }
    }

    private boolean validateAccNo(long accNo) {
        return InputValidator.validateAccountNumber(accNo);
    }

    private void addBeneficiary() {

        mUserDataModel.addBeneficiary(mBeneficiaryAccountNumber, mUserAccount);
    }
}
