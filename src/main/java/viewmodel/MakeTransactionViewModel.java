package viewmodel;

import datamodel.UserDataModel;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import misc.debug.Debug;
import model.BankAccount;
import model.Beneficiary;
import ui.ViewManager;
import viewmodel.constant.Constant;

import java.util.Iterator;
import java.util.Map;

public class MakeTransactionViewModel {
    private UserDataModel mDataModel;
    private BehaviorSubject<Beneficiary> mSelectedBeneficiary = BehaviorSubject.create();
    private final BankAccount mAccountToUse;
    private BehaviorSubject<Boolean> mAmountValidityStream = BehaviorSubject.create();
    private double mAmount;
    private Map<String, Beneficiary> mBeneficiaryData;
    private Iterator<Beneficiary> latestBeneficiary;
    private static final String TAG = "MakeTransactionVM";


    public void setBeneficiaryData(Map<String, Beneficiary> mBeneficiaryData) {
        this.mBeneficiaryData = mBeneficiaryData;
    }

    public MakeTransactionViewModel(UserDataModel userDataModel, BankAccount mAccountToUse) {
        this.mDataModel = userDataModel;
        this.mAccountToUse = mAccountToUse;
        latestBeneficiary = mSelectedBeneficiary.blockingMostRecent(null).iterator();
    }

    public Observable<Map<String, Beneficiary>> getBenefactors() {
        return mDataModel.getUserBeneficiaries()
                .flatMap((oldList) -> Observable.fromIterable(oldList)
                        .toMap(this::getFormattedString)
                        .toObservable());

    }

    private String getFormattedString(Beneficiary beneficiary) {
        return beneficiary.toString();
    }

    public Observable<Beneficiary> getSelectedBenefactor() {
        return mSelectedBeneficiary;
    }

    public Observable<Boolean> getAmountValidityStream() {
        return mAmountValidityStream;
    }

    public void setAmount(String amount) {
        double d = 0;
        try {
            d = Double.parseDouble(amount);
        } catch (NumberFormatException e) {
            mAmountValidityStream.onNext(false);
            return;
        }
        if (mAccountToUse.balance() > d) {
            mAmountValidityStream.onNext(false);
            return;
        }
        mAmountValidityStream.onNext(true);
        mAmount = d;
    }

    public void benefactorSelected(String formattedString) {
        mSelectedBeneficiary.onNext(mBeneficiaryData.get(formattedString));
    }

    public void requestBenefactorDetails(String formattedString) throws Exception {
        ViewManager.getInstance().setScene(Constant.Path.BENEFACTOR_DETAIL_VIEW,
                mBeneficiaryData.get(formattedString));
    }

    public Observable<Boolean> getTransacationSuccessStream() {
        return mDataModel.getTransactionSuccessStream();
    }

    public void makeTransaction() {
        if (latestBeneficiary.next() != null)
            mDataModel.makeTransaction(mAccountToUse, mAmount, latestBeneficiary.next());
        else
            Debug.err(TAG, "This should not have happened");
    }

}
