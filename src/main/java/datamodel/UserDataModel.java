package datamodel;

import io.reactivex.Observable;
import model.BankAccount;
import model.User;

import java.util.List;

public interface UserDataModel {
    Observable<User> fetchUserDetails();

    void updateUser(User u);

    Observable<List<BankAccount>> getUserAccounts();

    default  String getFormattedAccountDetails(BankAccount account){
        return account.getAccountNumber() + " " + account.getBranch() + " " + account.getAccountType();
    }

    BankAccount getAccountFromString(String formattedString);



}
