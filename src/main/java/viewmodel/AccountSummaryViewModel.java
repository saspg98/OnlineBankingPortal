package viewmodel;

import datamodel.UserDataModel;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import model.BankAccount;
import model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class AccountSummaryViewModel {
    private UserDataModel mDataModel;
    private BehaviorSubject<BankAccount> mSelectedAccount = BehaviorSubject.create();
    private Map<String, BankAccount> mAccountData;

    public void setAccountData(Map<String, BankAccount> mAccountData) {
        this.mAccountData = mAccountData;
    }

    public AccountSummaryViewModel(UserDataModel mDataModel) {
        this.mDataModel = mDataModel;
    }

    public Observable<Map<String, BankAccount>> getRegisteredBranches() {
        return mDataModel.getUserAccounts()
                .flatMap(list ->
                        Observable.fromIterable(list)
                                .toMap(mDataModel::getFormattedAccountDetails)
                                .toObservable()
                );
    }


    public void accountSelected(String account) {
        mSelectedAccount.onNext(mAccountData.get(account));
    }

    public Observable<BankAccount> getSelectedAccount() {
        return mSelectedAccount;
    }


    public Observable<String> getName() {
        return mDataModel.fetchUserDetails()
                .flatMapIterable(userListMap -> Arrays.asList(userListMap.keySet().toArray()))
                .map(user-> ((User) user).name());
    }
}
