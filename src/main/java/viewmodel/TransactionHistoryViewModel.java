package viewmodel;

import datamodel.UserDataModel;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import misc.debug.Debug;
import model.BankAccount;
import model.Transaction;

import java.util.List;
import java.util.Map;

public class TransactionHistoryViewModel {
    private static final String TAG = "TransactionHistoryViewModel";
    private UserDataModel userDataModel;
    private BehaviorSubject<BankAccount> mSelectedAccount = BehaviorSubject.create();
    private BehaviorSubject<List<Transaction>> mTransactionListStream = BehaviorSubject.create();
    private Map<String, BankAccount> mAccountData;
    private BankAccount lastSelectedAccount;

    public TransactionHistoryViewModel(UserDataModel userDataModel) {
        this.userDataModel = userDataModel;
        mSelectedAccount.subscribe(this::getTransactionData, this::onError);
    }

    private void onError(Throwable throwable) {
        Debug.err(TAG, throwable);
    }

    public void getTransactionData(BankAccount account){
        userDataModel.getUserTransactions(account)
                .subscribe((listData)->mTransactionListStream.onNext(listData), this::onError);
    }

    public Observable<List<Transaction>> getTransactionStream() {
        return mTransactionListStream;
    }

    public void setAccountData(Map<String, BankAccount> mAccountData) {
        this.mAccountData = mAccountData;
    }

    public void accountSelected(String account) {
        lastSelectedAccount = mAccountData.get(account);
        mSelectedAccount.onNext(lastSelectedAccount);


    }

    public Observable<BankAccount> getSelectedAccountStream() {
        return mSelectedAccount;
    }

    public Observable<Map<String, BankAccount>> getBankAccountDetails() {
        return userDataModel.getUserAccounts()
                .flatMap(list ->
                        Observable.fromIterable(list)
                                .toMap(userDataModel::getFormattedAccountDetails)
                                .toObservable()
                );
    }
}
