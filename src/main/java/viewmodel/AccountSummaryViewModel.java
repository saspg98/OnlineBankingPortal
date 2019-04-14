package viewmodel;

import datamodel.UserDataModel;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import model.BankAccount;

import java.util.List;

public class AccountSummaryViewModel {
    private UserDataModel mDataModel;
    private BehaviorSubject<BankAccount> mSelectedAccount;

    public AccountSummaryViewModel(UserDataModel mDataModel) {
        this.mDataModel = mDataModel;
    }

    public Observable<List<String>> getRegisteredBranches() {
        return mDataModel.getUserAccounts()
                .flatMap(list ->
                        Observable.fromIterable(list)
                                .map(mDataModel::getFormattedAccountDetails)
                                .toList()
                                .toObservable()
                );
    }


    public void accountSelected(String account) {
        mSelectedAccount.onNext(mDataModel.getAccountFromString(account));
    }

    public Observable<BankAccount> getSelectedAccount() {
        return mSelectedAccount;
    }



    public Observable<String> getName(){
        return mDataModel.fetchUserDetails()
                .map(user -> user.getName());
    }
}
