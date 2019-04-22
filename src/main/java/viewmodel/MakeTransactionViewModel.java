package viewmodel;

import datamodel.UserDataModel;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import misc.debug.Debug;
import model.BankAccount;
import model.Beneficiary;
import ui.ViewManager;
import viewmodel.constant.Constant;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MakeTransactionViewModel {
    private UserDataModel mDataModel;
    private BehaviorSubject<Beneficiary> mSelectedBeneficiary = BehaviorSubject.create();
    private BehaviorSubject<BankAccount>  mSelectedBankAccount = BehaviorSubject.create();
    private Map<String, BankAccount> mBankAccountData;
    private Map<String, Beneficiary> mBeneficiaryData;
    private BankAccount lastSelectedAccount;
    private Beneficiary lastSelectedBeneficiary;
    private BehaviorSubject<Boolean> mAmountValidityStream = BehaviorSubject.create();
    private BigDecimal mAmount;

    private static final String TAG = "MakeTransactionVM";


    public void setBeneficiaryData(Map<String, Beneficiary> mBeneficiaryData) {
        this.mBeneficiaryData = mBeneficiaryData;
    }

    public void setBankAccountData(Map<String, BankAccount> mBankAccountData) {
        this.mBankAccountData = mBankAccountData;
    }

    public MakeTransactionViewModel(UserDataModel userDataModel) {
        this.mDataModel = userDataModel;

    }

    public Observable<Map<String, Beneficiary>> getBeneficiaries() {
        return mDataModel.getUserBeneficiaries()
                .flatMap((oldList) -> Observable.fromIterable(oldList)
                        .toMap(mDataModel::getFormattedAccountDetails)
                        .toObservable());

    }

    public Observable<Map<String, BankAccount>> getBankAccounts(){
        return mDataModel.getUserAccounts()
                .flatMap((oldList) -> Observable.fromIterable(oldList)
                        .toMap(mDataModel::getFormattedAccountDetails)
                        .toObservable());

    }

    public Observable<Beneficiary> getSelectedBeneficiaryStream() {
        return mSelectedBeneficiary;
    }

    public Observable<BankAccount> getSelectedAccountStream() {
        return mSelectedBankAccount;
    }

    public Observable<Boolean> getAmountValidityStream() {
        return mAmountValidityStream;
    }

    public void setAmount(String amount) {
        BigDecimal d ;
        try {
            d = new BigDecimal(amount);
        } catch (NumberFormatException e) {
            mAmountValidityStream.onNext(false);
            return;
        }

        if (lastSelectedAccount.balance()
                .subtract(d).compareTo(Constant.Bank.MIN_ACCOUNT_BALANCE) >= 0) {
            mAmountValidityStream.onNext(false);
            return;
        }
        mAmountValidityStream.onNext(true);
        mAmount = d;
    }

    public void beneficiarySelected(String formattedString) {
        lastSelectedBeneficiary = mBeneficiaryData.get(formattedString);
        mSelectedBeneficiary.onNext(lastSelectedBeneficiary);

    }

    public void accountSelected(String formattedString){
        lastSelectedAccount = mBankAccountData.get(formattedString);
        mSelectedBankAccount.onNext(lastSelectedAccount);
    }

    public void requestBenefactorDetails(String formattedString) throws Exception {
        ViewManager.getInstance().setScene(Constant.Path.BENEFACTOR_DETAIL_VIEW,
                mBeneficiaryData.get(formattedString));
    }

    public Observable<Boolean> getTransacationSuccessStream() {
        return mDataModel.getTransactionSuccessStream();
    }

    public void makeTransaction() {
        if (lastSelectedBeneficiary!=null)
            mDataModel.makeTransaction(lastSelectedAccount, mAmount, lastSelectedBeneficiary);
        else
            Debug.err(TAG, "This should not have happened");
    }

}
