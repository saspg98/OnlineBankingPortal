package datamodel;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import model.BankAccount;
import model.Beneficiary;
import model.Transaction;
import model.User;
import ui.ViewManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class LocalUserDataModel implements UserDataModel{
    private long UID;

    public LocalUserDataModel(long uid) {
        UID = uid;
    }

    @Override
    public Observable<User> fetchUserDetails() {
        return ViewManager.getInstance().getDb()
                .select("Select * from users where uid = ?")
                .parameter(UID)
                .autoMap(User.class)
                .toObservable();
    }

    @Override
    public void updateUser(User u) {
        //TODO: Implementation after User is implemented
        //Implement after User is implemented!
//        ViewManager.getInstance().getDb()
//                .update("update users " +
//                        "set password = ? " +
//                        "where uid = ?")
//                .parameters(u)

    }

    @Override
    public Observable<List<BankAccount>> getUserAccounts() {
        return ViewManager.getInstance().getDb()
                .select("select AccNo, Balance, AccType, BCode from Accounts " +
                        "where UID = ? ")
                .parameter(UID)
                .autoMap(BankAccount.class)
                .toList()
                .toObservable();

    }

    @Override
    public Observable<List<Transaction>> getUserTransactions() {
        return ViewManager.getInstance().getDb()
                .select("select * from Transactions " +
                        "where sender = ? or receiver = ?")
                .parameterListStream(
                        ViewManager.getInstance().getDb()
                        .select("select AccNo from Accounts " +
                                "where UID = ? ")
                        .parameter(UID)
                        .getAs(Long.class)
                        .map(val -> Arrays.asList(val, val))
                )
                .autoMap(Transaction.class)
                .toList()
                .toObservable();
    }

    @Override
    public String getFormattedAccountDetails(BankAccount account) {
        //TODO: Implementation
        return null;
    }

    @Override
    public BankAccount getAccountFromString(String formattedString) {
        //TODO: Implementation
        return null;
    }

    @Override
    public Observable<List<Beneficiary>> getUserBenefactors() {
        return ViewManager.getInstance().getDb()
                .select("select AccNo, Balance, AccType, BCode from Accounts " +
                        "where AccNo = ? ")
                .parameterStream(
                        ViewManager.getInstance().getDb()
                        .select("select Beneficiary from Beneficiaries " +
                                "where AccNo = ?")
                        .parameterStream(
                                ViewManager.getInstance().getDb()
                                .select("select AccNo from Accounts " +
                                        "where UID = ? ")
                                .parameter(UID)
                                .getAs(Long.class)
                        )
                        .getAs(Long.class)
                )
                .autoMap(Beneficiary.class)
                .toList()
                .toObservable();

    }

    @Override
    public Beneficiary getBenefactorFromString(String name) {
        //TODO: Implementation ->Define "name"
        return null;
    }

    @Override
    public boolean makeTransaction(BankAccount accountToUse, double amount, Beneficiary beneficiary) {
        //TODO: Either change to a transactionStream or call method async with call backs
//        ViewManager.getInstance().getDb()
//                .update("insert into Transactions(Sender, Receiver, Time, Amount) " +
//                        "values( ?, ?, ?, ?)")
//                .parameters(accountToUse.accNo(), beneficiary.accNo, LocalDate.now(), amount)
//                .transaction()
//                .doOnNext((obj)->{
//                    obj.update("update Accounts set balance = ? " +
//                            "where AccNo = ? ")
//                            .parameters(accountToUse.balance()-amount, accountToUse.accNo())
//                            .transactedValuesOnly(); })
//                .observeOn(Schedulers.computation())
//                .subscribe();
        //NOTE: The SQL Logic is probably alright, but probably should make this return a stream

        return false;
    }

    @Override
    public boolean addBeneficiary(String beneficiary) {
        //TODO: Either change to additionOfBeneficiaryStream or call method async with callbacks
        //TODO: Define beneficiary
        return false;
    }

    public void onLogout(){
        UID = Long.MIN_VALUE;
    }
}
