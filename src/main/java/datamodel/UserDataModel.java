package datamodel;

import io.reactivex.Observable;
import model.BankAccount;
import model.Beneficiary;
import model.Transaction;
import model.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface UserDataModel {
    Observable<Map<User, List<Long>>> fetchUserDetails();

    Observable<Boolean> updatePassword(String oldPass, String newPass);

    Observable<List<BankAccount>> getUserAccounts();

    default String getFormattedAccountDetails(BankAccount account) {
        return account.accNo() + " " + account.bcode();
    }

    Observable<List<Transaction>> getUserTransactions(BankAccount bankAccount);

    Observable<List<Beneficiary>> getUserBeneficiaries();

    Observable<BankAccount> getPrimaryUserAccount();

    void makeTransaction(BankAccount accountToUse, BigDecimal amount, Beneficiary beneficiary);

    void addBeneficiary(long beneficiaryAccount, long userAccount);

    Observable<Boolean> getAddBeneficiarySuccessStream();

    Observable<Boolean> getTransactionSuccessStream();

    Observable<User> getUserDetails();

    Observable<User> getPayeeDetails(BankAccount account);

    void onLogout();
}
