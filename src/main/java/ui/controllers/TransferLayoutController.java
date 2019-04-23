/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import misc.debug.Debug;
import model.BankAccount;
import model.Beneficiary;
import model.User;
import ui.ViewManager;
import viewmodel.MakeTransactionViewModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Pranek
 */
public class TransferLayoutController implements Initializable, ViewModelUser {

    private final String TAG = "TransferLayoutController";
    private MakeTransactionViewModel viewModel;
    private CompositeDisposable mObservables = new CompositeDisposable();

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
    private ComboBox<String> viewPayee;
    @FXML
    private ComboBox<String> accountNumberDrop;
    @FXML
    private Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        viewModel = new MakeTransactionViewModel(ViewManager.getInstance().getUserDataModel());
        createObservables();
        errorLabel.setText("*Not sufficient amount in the account!");
        errorLabel.setVisible(false);
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
        mObservables.add(viewModel.getTransactionSuccessStream()
                .observeOn(JavaFxScheduler.platform())
                .subscribe(this::onTransactionSuccess, this::onError));
        mObservables.add(viewModel.getSelectedAccountStream()
                .observeOn(JavaFxScheduler.platform())
                .subscribe(this::onAccountSelected, this::onError));
        mObservables.add(viewModel.getBankAccounts()
                .observeOn(JavaFxScheduler.platform())
                .subscribe(this::onAccountDetailsReceived, this::onError));
        mObservables.add(viewModel.getUserData()
                .observeOn(JavaFxScheduler.platform())
                .subscribe(this::onUserDataReceived, this::onError));
    }

    private void onUserDataReceived(User user) {
        name.setText(user.name());
    }

    private void onAccountDetailsReceived(Map<String, BankAccount> bankAccountDetails) {
        accountNumberDrop.setItems(FXCollections.observableList(new ArrayList<>(bankAccountDetails.keySet())));
        viewModel.setBankAccountData(bankAccountDetails);
        accountNumberDrop.getSelectionModel().select(0);
    }

    private void onAccountSelected(BankAccount bankAccount) {
        //To show account type if necessary
    }

    private void onTransactionSuccess(boolean isTransactionSuccessful) {
        //TODO: Implementation
        if (isTransactionSuccessful) {
            Debug.log(TAG, "Transaction Successfully Processed!");
            errorLabel.setVisible(true);
        } else
            Debug.log(TAG, "Transaction could not be processed at this time");

    }

    private void onBeneficiarySelected(Beneficiary beneficiary) {
        payeeAccNo.setText(beneficiary.accNo().toString());
        payeeBranchCode.setText(String.valueOf(beneficiary.bcode()));

    }

    private void onDetailsReceived(Map<String, Beneficiary> stringBeneficiaryMap) {

        viewPayee.setItems(FXCollections.observableList(new ArrayList<>(stringBeneficiaryMap.keySet())));
        viewModel.setBeneficiaryData(stringBeneficiaryMap);
        viewPayee.getSelectionModel().select(0);

    }

    private void onAmountValid(boolean isValid) {

        if (!isValid) {
            Debug.log(TAG, "INVALID AMOUNT");
            errorLabel.setText("*Invalid amount entered!");
            errorLabel.setVisible(true);
            amount.setText("");
        }
    }

    @Override
    public void disposeObservables() {
        Debug.log(TAG, "Disposing Observables");
        mObservables.clear();
        viewModel = null;
    }

    @FXML
    private void onConfirmPaymentClicked(ActionEvent actionEvent) {
        Debug.log(TAG, "Confirm Button Clicked");
        String amt = amount.getText().trim();
        try {
            Long.parseLong(amt);
        }catch (Exception e){
            onAmountValid(false);
        }
        viewModel.setAmount(amt);
    }

    @FXML
    private void onAddNewPayeeClicked(ActionEvent actionEvent) {
        viewModel.onOpenAddPayee(accountNumberDrop.getValue());
    }

    @FXML
    private void onPayeeDropDownSelected(ActionEvent actionEvent) {
        viewModel.beneficiarySelected(viewPayee.getValue());
        viewModel.getPayeeDetails(viewPayee.getValue())
                .observeOn(JavaFxScheduler.platform())
                .subscribe(this::setPayeeDetails, this::onError);
        errorLabel.setVisible(false);

    }

    private void setPayeeDetails(User payee) {
        payeeName.setText(payee.name());
        //other if necessary
    }

    @FXML
    private void onAccountDropDownSelected(ActionEvent actionEvent) {
        viewModel.accountSelected(accountNumberDrop.getValue());
        errorLabel.setVisible(false);
    }

    @FXML
    private void onTextEntered(KeyEvent keyEvent) {
        errorLabel.setText("*Not sufficient amount in the account!");
        errorLabel.setVisible(false);
    }
}