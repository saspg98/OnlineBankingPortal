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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import misc.debug.Debug;
import model.BankAccount;
import model.Beneficiary;
import model.User;
import ui.ViewManager;
import viewmodel.MakeTransactionViewModel;
import viewmodel.constant.Constant;

import java.io.IOException;
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
    private FXMLLoader fxmlLoader;
    private MakeTransactionViewModel viewModel;
    private CompositeDisposable mObservables = new CompositeDisposable();

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
    private ComboBox<String> viewPayee;
    @FXML
    private ComboBox<String> accountNumberDrop;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        viewModel = new MakeTransactionViewModel(ViewManager.getInstance().getUserDataModel());
        createObservables();
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
        accountType.setText(bankAccount.Acctype());

    }

    private void onTransactionSuccess(boolean isTransactionSuccessful) {
        //TODO: Implementation
        if(isTransactionSuccessful)
            Debug.log(TAG, "Transaction Successfully Processed!");
        else
            Debug.log(TAG,"Transaction could not be processed at this time");
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
        //Handle the case when the amount the user entered is invalid
        if(!isValid){
            Debug.log(TAG,"INVALID AMOUNT");
        }
    }

    @Override
    public void disposeObservables() {
        Debug.log(TAG,"Disposing Observables");
        mObservables.clear();
    }

    @FXML
    private void onConfirmPaymentClicked(ActionEvent actionEvent) {
        Debug.log(TAG,"Confirm Button Clicked");
        viewModel.setAmount(amount.getText().trim());
    }

    @FXML
    private void onAddNewPayeeClicked(ActionEvent actionEvent) {

        Stage newStage = new Stage();
        Parent addPayee = null;
        try {
            fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(Constant.Path.ADD_PAYEE));
            addPayee = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            Debug.err("Unable to create alert box for sign up");
        }

        Scene scene = new Scene(addPayee);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setScene(scene);
        newStage.centerOnScreen();
        newStage.show();
        newStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                ((ViewModelUser)fxmlLoader.getController()).disposeObservables();
                Debug.log("CLOSING","Add payee pop up!");
            }
        });
    }

    @FXML
    private void onPayeeDropDownSelected(ActionEvent actionEvent) {
        viewModel.beneficiarySelected(viewPayee.getValue());
        viewModel.getPayeeDetails(viewPayee.getValue())
                .observeOn(JavaFxScheduler.platform())
                .subscribe(this::setPayeeDetails, this::onError);

    }

    private void setPayeeDetails(User payee) {
        payeeName.setText(payee.name());
        //other if necessary
    }

    @FXML
    private void onAccountDropDownSelected(ActionEvent actionEvent) {
        viewModel.accountSelected(accountNumberDrop.getValue());
    }
}
