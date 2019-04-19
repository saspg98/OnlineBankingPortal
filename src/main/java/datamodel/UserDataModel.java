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

    default String getFormattedAccountDetails(BankAccount account) {
        return account.accNo() + " " + account.bcode();
    }

    Observable<List<Transaction>> getUserTransactions(BankAccount bankAccount);

    Observable<List<Beneficiary>> getUserBeneficiaries();

    Observable<BankAccount> getPrimaryUserAccount();

    void makeTransaction(BankAccount accountToUse, double amount, Beneficiary beneficiary);

    void addBeneficiary(long beneficiaryAccount, long userAccount);

    Observable<Boolean> getAddBeneficiarySuccessStream();

    Observable<Boolean> getTransactionSuccessStream();
}
