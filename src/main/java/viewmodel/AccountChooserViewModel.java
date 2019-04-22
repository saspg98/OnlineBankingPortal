package viewmodel;

import datamodel.UserDataModel;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import model.BankAccount;
import ui.ViewManager;
import viewmodel.constant.Constant;

import java.util.Map;

//Used for opening the MakeTransaction Screen
public class AccountChooserViewModel {
    private UserDataModel mDataModel;
    private BehaviorSubject<BankAccount> mSelectedAccount = BehaviorSubject.create();
    private Map<String, BankAccount> mAccountData;

    public void setAccountData(Map<String, BankAccount> mAccountData) {
        this.mAccountData = mAccountData;
    }

    public AccountChooserViewModel(UserDataModel mDataModel) {
        this.mDataModel = mDataModel;
    }

    public void accountSelected(String account) {
        mSelectedAccount.onNext(mAccountData.get(account));
    }

    public Observable<BankAccount> getSelectedAccount() {
        return mSelectedAccount;
    }

    public Observable<Map<String, BankAccount>> getBankAccountDetails() {
        return mDataModel.getUserAccounts()
                .flatMap(list ->
                        Observable.fromIterable(list)
                                .toMap(mDataModel::getFormattedAccountDetails)
                                .toObservable()
                );
    }


}
