package viewmodel;

import datamodel.UserDataModel;
import io.reactivex.Observable;
import model.Transaction;

import java.util.List;

public class TransactionHistoryViewModel {
    private UserDataModel userDataModel;

    public TransactionHistoryViewModel(UserDataModel userDataModel) {
        this.userDataModel = userDataModel;
    }

    public Observable<List<Transaction>> getTransactions() {
        return userDataModel.getUserTransactions();
    }
}
