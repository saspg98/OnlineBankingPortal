package viewmodel;

import datamodel.UserDataModel;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import misc.debug.Debug;
import model.BankAccount;
import model.Beneficiary;
import model.User;
import ui.ViewManager;
import ui.controllers.DataReceiver;
import ui.controllers.ViewModelUser;
import viewmodel.constant.Constant;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

public class MakeTransactionViewModel {
    private FXMLLoader fxmlLoader;
    private UserDataModel mDataModel;
    private BehaviorSubject<Beneficiary> mSelectedBeneficiary = BehaviorSubject.create();
    private BehaviorSubject<BankAccount> mSelectedBankAccount = BehaviorSubject.create();
    private Map<String, BankAccount> mBankAccountData;
    private Map<String, Beneficiary> mBeneficiaryData;
    private BankAccount lastSelectedAccount;
    private Beneficiary lastSelectedBeneficiary;
    private BehaviorSubject<Boolean> mAmountValidityStream = BehaviorSubject.create();
    private BigDecimal mAmount;

    private static final String TAG = "MakeTransactionVM";


    public void setBeneficiaryData(Map<String, Beneficiary> mBeneficiaryData) {
        this.mBeneficiaryData = mBeneficiaryData;
    }

    public void setBankAccountData(Map<String, BankAccount> mBankAccountData) {
        this.mBankAccountData = mBankAccountData;
    }

    public MakeTransactionViewModel(UserDataModel userDataModel) {
        this.mDataModel = userDataModel;

    }


    public Observable<User> getUserData() {
        return mDataModel.getUserDetails();
    }

    public Observable<User> getPayeeDetails(String accountString) {
        return mDataModel.getPayeeDetails(mBeneficiaryData.get(accountString));
    }


    public Observable<Map<String, Beneficiary>> getBeneficiaries() {
        return mDataModel.getUserBeneficiaries()
                .flatMap((oldList) -> Observable.fromIterable(oldList)
                        .toMap(mDataModel::getFormattedAccountDetails)
                        .toObservable());

    }

    public Observable<Map<String, BankAccount>> getBankAccounts() {
        return mDataModel.getUserAccounts()
                .flatMap((oldList) -> Observable.fromIterable(oldList)
                        .toMap(mDataModel::getFormattedAccountDetails)
                        .toObservable());

    }

    public Observable<Beneficiary> getSelectedBeneficiaryStream() {
        return mSelectedBeneficiary;
    }

    public Observable<BankAccount> getSelectedAccountStream() {
        return mSelectedBankAccount;
    }

    public Observable<Boolean> getAmountValidityStream() {
        return mAmountValidityStream;
    }

    public void setAmount(String amount) {
        BigDecimal d;
        try {
            d = new BigDecimal(amount);
        } catch (NumberFormatException e) {
            Debug.log(TAG, "NumberFormatException");
            mAmountValidityStream.onNext(false);
            return;
        }

        if (lastSelectedAccount.balance()
                .subtract(d).compareTo(Constant.Bank.MIN_ACCOUNT_BALANCE) < 0) {
            mAmountValidityStream.onNext(false);
            return;
        }
        mAmountValidityStream.onNext(true);
        mAmount = d;
        makeTransaction();
    }

    public void beneficiarySelected(String formattedString) {
        lastSelectedBeneficiary = mBeneficiaryData.get(formattedString);
        mSelectedBeneficiary.onNext(lastSelectedBeneficiary);

    }

    public void accountSelected(String formattedString) {
        lastSelectedAccount = mBankAccountData.get(formattedString);
        mSelectedBankAccount.onNext(lastSelectedAccount);
    }

    public void requestBenefactorDetails(String formattedString) throws Exception {
        ViewManager.getInstance().setScene(Constant.Path.BENEFACTOR_DETAIL_VIEW,
                mBeneficiaryData.get(formattedString));
    }

    public Observable<Boolean> getTransactionSuccessStream() {
        return mDataModel.getTransactionSuccessStream();
    }

    private void makeTransaction() {
        if (lastSelectedBeneficiary != null)
            mDataModel.makeTransaction(lastSelectedAccount, mAmount, lastSelectedBeneficiary);
        else
            Debug.err(TAG, "This should not have happened");
    }

    public void onOpenAddPayee(String accName) {

        Stage newStage = new Stage();
        Parent addPayee = null;
        try {
            fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(Constant.Path.ADD_PAYEE));
            addPayee = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            Debug.err("Unable to create alert box for sign up");
        }

        ((DataReceiver) fxmlLoader.getController()).receiveData(mBankAccountData.get(accName).accNo());
        Scene scene = new Scene(addPayee);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setScene(scene);
        newStage.centerOnScreen();
        newStage.show();
        newStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                ((ViewModelUser) fxmlLoader.getController()).disposeObservables();
                Debug.log("CLOSING", "Add payee pop up!");
            }
        });

    }

}
