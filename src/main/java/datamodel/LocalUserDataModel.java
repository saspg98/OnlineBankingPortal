package datamodel;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import misc.debug.Debug;
import model.BankAccount;
import model.Beneficiary;
import model.Transaction;
import model.User;
import ui.ViewManager;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public final class LocalUserDataModel implements UserDataModel {
    private static final String TAG = "UserDataModel";
    private long UID;
    private BehaviorSubject<Boolean> mAddBeneficiarySuccessStream = BehaviorSubject.create();
    private BehaviorSubject<Boolean> mTransactionSuccessStream = BehaviorSubject.create();

    public LocalUserDataModel(long uid) {
        UID = uid;
    }

    @Override
    public Observable<Map<User, List<Long>>> fetchUserDetails() {

        Observable<List<Long>> list = ViewManager.getInstance().getDb()
                .select("select PhNumber from PhNo where uid = ?")
                .parameter(UID)
                .getAs(Long.class)
                .toList()
                .toObservable();
        return ViewManager.getInstance().getDb()
                .select("Select * from users where uid = ?")
                .parameter(UID)
                .autoMap(User.class)
                .toObservable()
                .zipWith(list, (numlist, user) -> {
                    return
                            new HashMap.SimpleEntry<>(user, numlist);
                })
                .toList()
                .map((listOf) -> {
                    Map<User, List<Long>> map = new HashMap<>();
                    for (AbstractMap.SimpleEntry<List<Long>, User> e : listOf) {
                        map.put(e.getValue(), e.getKey());
                    }
                    return map;
                })
                .toObservable();

    }

    public Observable<Boolean> updatePassword(String oldPass, String newPass) {

        return ViewManager.getInstance().getDb()
                .update("update LoginCreds " +
                        "set password = ? " +
                        "where uid = ? and password =?")
                .parameters(newPass, UID, oldPass)
                .counts()
                .map((value) -> {
                    return value != 0;
                })
                .toObservable();

    }

    @Override
    public Observable<List<BankAccount>> getUserAccounts() {
        return ViewManager.getInstance().getDb()
                .select("select * from Accounts " +
                        "where UID = ? ")
                .parameter(UID)
                .autoMap(BankAccount.class)
                .toList()
                .toObservable();

    }

//    @Override
//    public Observable<List<Transaction>> getUserTransactions(BankAccount bankAccount) {
//        return ViewManager.getInstance().getDb()
//                .select("select * from Transactions " +
//                        "where sender = ? or receiver = ?")
//                .parameterListStream(
//                        ViewManager.getInstance().getDb()
//                                .select("select AccNo from Accounts " +
//                                        "where UID = ? ")
//                                .parameter(UID)
//                                .getAs(Long.class)
//                                .map(val -> Arrays.asList(val, val))
//                )
//                .autoMap(Transaction.class)
//                .toList()
//                .toObservable();
//    }

    @Override
    public Observable<List<Transaction>> getUserTransactions(BankAccount bankAccount) {
        return ViewManager.getInstance().getDb()
                .select("select * from Transactions " +
                        "where sender = :acc or receiver = :acc")
                .parameter("acc", bankAccount.accNo())
                .autoMap(Transaction.class)
                .toList()
                .toObservable();
    }

    @Override
    public String getFormattedAccountDetails(BankAccount account) {

        return "Account No.: " + account.accNo() + "\n" + "Branch Code: " +
                account.bcode();
    }


    @Override
    public Observable<List<Beneficiary>> getUserBeneficiaries() {
        return ViewManager.getInstance().getDb()
                .select("select * from Accounts " +
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
    public Observable<BankAccount> getPrimaryUserAccount() {
        return ViewManager.getInstance().getDb()
                .select("select * from Accounts " +
                        "where AccNo = ? ")
                .parameterStream(
                        ViewManager.getInstance().getDb()
                                .select("select AccNo from Accounts where UID = ? and AccType = 'S' ")
                                .parameter(UID)
                                .getAs(Long.class)
                )
                .autoMap(BankAccount.class)
                .toObservable();
    }


    @Override
    public void makeTransaction(BankAccount accountToUse, BigDecimal amount, Beneficiary beneficiary) {
        //Note: Possible Source of Errors!
        Debug.log(TAG, "Making Transaction");
        List<Object> l1 = Arrays.asList(accountToUse.balance().subtract(amount), accountToUse.accNo());
        List<Object> l2 = Arrays.asList(beneficiary.balance().add(amount), beneficiary.accNo());
        List<List<Object>> cumulativeList = Arrays.asList(l1, l2);
        ViewManager.getInstance().getDb()
                .update("update Accounts set balance = ? " +
                        "where AccNo = ? ")
                .parameterListStream(Flowable.fromIterable(cumulativeList))
                .counts()
                .doOnNext((data) -> {
                    Debug.log(TAG, "Updated one record");
                })
                .toList()
                .toFlowable()
                .flatMap((tx) -> {
                    return ViewManager.getInstance().getDb()
                            .update("insert into Transactions(Sender, Receiver, Time, Amount) " +
                                    "values( ?, ?, ?, ?)")
                            .parameters(accountToUse.accNo(), beneficiary.accNo(), Timestamp.valueOf(LocalDateTime.now()), amount)
                            .counts();
                })
                .doOnNext((data) -> {
                    Debug.log(TAG, "Inserted Record");
                })
                .subscribe((updateCount) ->
                        {
                            Debug.log(TAG, "Inside On Next for Transaction", "Tx value",
                                    updateCount);
                        }
                        , (err) -> {
                            Debug.err(TAG, err);
                            mTransactionSuccessStream.onNext(false);
                        }
                        , () -> {
                            Debug.log(TAG, "Completed Transaction! Possibly successfully!");
                            mTransactionSuccessStream.onNext(true);
                        });

//        NOTE: The SQL Logic is probably alright, but probably should make this return a stream

    }

    @Override
    public void addBeneficiary(long beneficiaryAccNo, BigInteger userAccount) {
        Debug.log(TAG, "Values, for user and beneficiary resp are", userAccount, beneficiaryAccNo);
        ViewManager.getInstance().getDb()
                .update("insert into Beneficiaries(Accno, Beneficiary) values( ?, ?)")
                .parameters(userAccount, beneficiaryAccNo)
                .transaction()
                .observeOn(Schedulers.computation())
                .subscribe((val) ->
                        {
                            Debug.log(TAG, "Inside On Next for beneficiary", "Tx value",
                                    val.value());
                        }
                        , (err) -> {
                            Debug.err(TAG, err);
                            mAddBeneficiarySuccessStream.onNext(false);
                        }
                        , () -> {
                            Debug.log(TAG, "Completed Addition! Possibly successfully!");
                            mAddBeneficiarySuccessStream.onNext(true);
                        });
    }

    @Override
    public Observable<Boolean> getAddBeneficiarySuccessStream() {
        return mAddBeneficiarySuccessStream;
    }

    @Override
    public Observable<Boolean> getTransactionSuccessStream() {
        return mTransactionSuccessStream;
    }

    @Override
    public Observable<User> getUserDetails() {
        return ViewManager.getInstance().getDb()
                .select("select * from users " +
                        "where uid = ?")
                .parameter(UID)
                .autoMap(User.class)
                .toObservable();
    }

    @Override
    public Observable<User> getPayeeDetails(BankAccount beneficiaryAccount) {
        return ViewManager.getInstance().getDb()
                .select("select * from users " +
                        "where uid = ?")
                .parameter(beneficiaryAccount.uid())
                .autoMap(User.class)
                .toObservable();
    }


    @Override
    public void onLogout() {

//        UID = Long.MIN_VALUE;
    }
}
