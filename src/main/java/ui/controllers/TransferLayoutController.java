/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import misc.debug.Debug;
import model.BankAccount;
import model.Beneficiary;
import ui.ViewManager;
import viewmodel.MakeTransactionViewModel;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Pranek
 */
public class TransferLayoutController implements Initializable, ViewModelUser, DataReceiver {

    private final String TAG = "TransferLayoutController";

    @FXML
    private Label accountType;
    @FXML
    private Label accountNumber;
    @FXML
    private Label name;
    @FXML
    private Label payeeName;
    @FXML
    private Label payeeAccNo;
    @FXML
    private Label payeeBranchCode;
    @FXML
    private TextField amount;
    @FXML
    private ComboBox viewPayee;

    private MakeTransactionViewModel viewModel;
    private CompositeDisposable mObservables = new CompositeDisposable();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @Override
    public void createObservables() {
        mObservables.add(viewModel.getAmountValidityStream()
        .observeOn(JavaFxScheduler.platform())
        .subscribe(this::onAmountValid, this::onError));
        mObservables.add(viewModel.getBeneficiaries()
        .observeOn(JavaFxScheduler.platform())
        .subscribe(this::onDetailsReceived, this::onError));
        mObservables.add(viewModel.getSelectedBeneficiaryStream()
        .observeOn(JavaFxScheduler.platform())
        .subscribe(this::onBeneficiarySelected, this::onError));
        mObservables.add(viewModel.getTransacationSuccessStream()
        .observeOn(JavaFxScheduler.platform())
        .subscribe(this::onTransactionSuccess, this::onError));
        mObservables.add(viewModel.getSelectedAccountStream()
        .observeOn(JavaFxScheduler.platform())
        .subscribe(this::onAccountSelected, this::onError));
        mObservables.add(viewModel.getBankAccounts()
        .observeOn(JavaFxScheduler.platform())
        .subscribe(this::onAccountDetailsReceived, this::onError));
    }

    private void onAccountDetailsReceived(Map<String, BankAccount> bankAccountDetails) {
        //TODO: set views
        // Populate Dropdown box

        viewModel.setBankAccountData(bankAccountDetails);
    }

    private void onAccountSelected(BankAccount bankAccount) {
        accountType.setText(bankAccount.Acctype());
    }


    private void onTransactionSuccess(boolean isTransactionSuccessful) {
        //TODO: Implementation
    }

    private void onBeneficiarySelected(Beneficiary beneficiary) {
        //TODO: Set the beneficiary details in the views
    }

    private void onDetailsReceived(Map<String, Beneficiary> stringBeneficiaryMap) {
        //TODO: Implementation
        //set views here
        viewModel.setBeneficiaryData(stringBeneficiaryMap);
    }

    private void onAmountValid(boolean isValid) {
        //Handle the case when the amount the user entered is valid
        viewModel.makeTransaction();
    }

    @Override
    public void disposeObservables() {
        Debug.log(TAG,"Disposing Observables");
        mObservables.clear();
    }

    @Override
    public <T> void receiveData(T... data) {
        BankAccount bankAccount = (BankAccount) data[0];
        viewModel = new MakeTransactionViewModel(ViewManager.getInstance().getUserDataModel());
        createObservables();
    }

    @FXML
    private void onConfirmPaymentClicked(ActionEvent actionEvent) {
    }

    @FXML
    private void onViewPayeeClicked(ActionEvent actionEvent) {
    }
}
