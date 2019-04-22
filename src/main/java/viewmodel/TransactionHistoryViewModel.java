package viewmodel;

import datamodel.UserDataModel;
import io.reactivex.Observable;
import model.BankAccount;
import model.Transaction;

import java.util.List;

public class TransactionHistoryViewModel {
    private UserDataModel userDataModel;
    private BankAccount bankAccount;

    public TransactionHistoryViewModel(UserDataModel userDataModel, BankAccount bankAccount) {
        this.userDataModel = userDataModel;
        this.bankAccount = bankAccount;
    }

    public Observable<List<Transaction>> getTransactions() {
        return userDataModel.getUserTransactions(bankAccount);
    }

    public BankAccount getBankAccount(){
        return bankAccount;
    }
}
