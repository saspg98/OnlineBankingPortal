package datamodel;

import io.reactivex.Observable;
import model.BankAccount;
import model.Benefactor;
import model.Transaction;
import model.User;

import java.util.List;

public interface UserDataModel {
    Observable<User> fetchUserDetails();

    void updateUser(User u);

    Observable<List<BankAccount>> getUserAccounts();

    default  String getFormattedAccountDetails(BankAccount account){
        return account.getAccountNumber() + " " + account.getBranch() + " " + account.getAccountType();
    }

    Observable<List<Transaction>> getUserTransactions();
    BankAccount getAccountFromString(String formattedString);


    Observable<List<Benefactor>> getUserBenefactors();

    Benefactor getBenefactorFromString(String name);


    boolean makeTransaction(BankAccount accountToUse, double amount, Benefactor benefactor);

    boolean addBeneficiary(String beneficiary);
}
