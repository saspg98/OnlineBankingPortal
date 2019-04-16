package viewmodel;

import datamodel.UserDataModel;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import model.BankAccount;
import model.Beneficiary;
import ui.ViewManager;
import viewmodel.constant.Constant;

import java.util.List;

public class MakeTransactionViewModel {
    private UserDataModel mDataModel;
    private BehaviorSubject<Beneficiary> mSelectedBenefactor;
    private final BankAccount mAccountToUse;
    private BehaviorSubject<Boolean> mAmountValidityStream = BehaviorSubject.create();
    private double mAmount;



    public MakeTransactionViewModel(UserDataModel userDataModel, BankAccount mAccountToUse) {
        this.mDataModel = userDataModel;
        this.mAccountToUse = mAccountToUse;
    }

    public Observable<List<Beneficiary>> getBenefactors(){
        return mDataModel.getUserBenefactors();
    }

    public Observable<Beneficiary> getSelectedBenefactor(){
        return mSelectedBenefactor;
    }

    public Observable<Boolean> getAmountValidityStream() {
        return mAmountValidityStream;
    }

    public void setAmount(String amount) {
        double d =0;
        try{
            d = Double.parseDouble(amount);
        } catch (NumberFormatException e){
            mAmountValidityStream.onNext(false);
            return;
        }
        if(mAccountToUse.balance() > d){
            mAmountValidityStream.onNext(false);
            return;
        }
        mAmountValidityStream.onNext(true);
        mAmount = d;
    }

    public void benefactorSelected(String name) {
        mSelectedBenefactor.onNext(mDataModel.getBenefactorFromString(name));
    }

    public void requestBenefactorDetails(String name) throws Exception {
        ViewManager.getInstance().setScene(Constant.Path.BENEFACTOR_DETAIL_VIEW,
                mDataModel.getBenefactorFromString(name));
    }

    public boolean makeTransaction(){
        return mDataModel.makeTransaction(mAccountToUse, mAmount, mSelectedBenefactor.blockingLast());
    }

}
