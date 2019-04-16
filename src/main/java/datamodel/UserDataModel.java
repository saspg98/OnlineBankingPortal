package datamodel;

import io.reactivex.Observable;
import model.BankAccount;
import model.Beneficiary;
import model.Transaction;
import model.User;

import java.util.List;

public interface UserDataModel {
    Observable<User> fetchUserDetails();

    void updateUser(User u);

    Observable<List<BankAccount>> getUserAccounts();

    default  String getFormattedAccountDetails(BankAccount account){
        return account.accNo() + " " + account.bcode() ;
    }

    Observable<List<Transaction>> getUserTransactions();
    BankAccount getAccountFromString(String formattedString);


    Observable<List<Beneficiary>> getUserBenefactors();

    Beneficiary getBenefactorFromString(String name);


    boolean makeTransaction(BankAccount accountToUse, double amount, Beneficiary benefactor);

    boolean addBeneficiary(String beneficiary);
}
