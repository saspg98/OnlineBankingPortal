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
import javafx.scene.control.TableView;
import misc.debug.Debug;
import model.BankAccount;
import model.Transaction;
import ui.ViewManager;
import viewmodel.TransactionHistoryViewModel;

import java.net.URL;
import java.util.*;

/**
 * FXML Controller class
 *
 * @author Pranek
 */
public class TransactionLayoutController implements Initializable, ViewModelUser{

    private CompositeDisposable mObservables = new CompositeDisposable();
    private TransactionHistoryViewModel viewModel;
    private final String TAG = "TransactionLayoutController";

    @FXML
    private Label LAccountTypeOutput;
    @FXML
    private Label LAccountNumberOutput;
    @FXML
    private TableView<Transaction> transactionTableView;
    @FXML
    private ComboBox<String> accountNumberDrop;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        viewModel = new TransactionHistoryViewModel(ViewManager.getInstance().getUserDataModel());

        createObservables();
    }

    @Override
    public void createObservables() {
        mObservables.add(viewModel.getTransactionStream()
        .observeOn(JavaFxScheduler.platform())
        .subscribe(this::setTransactionListView, this::onError));
        mObservables.add(viewModel.getBankAccountDetails()
        .observeOn(JavaFxScheduler.platform())
        .subscribe(this::setAccountViewDetails,this::onError));
        mObservables.add(viewModel.getSelectedAccountStream()
        .observeOn(JavaFxScheduler.platform())
        .subscribe(this::onAccountSelected, this::onError));

    }

    private void onAccountSelected(BankAccount account) {
        LAccountTypeOutput.setText(account.Acctype());
    }

    private void setAccountViewDetails(Map<String, BankAccount> stringBankAccountMap) {
        //set drop down list values
        accountNumberDrop.setItems(FXCollections.observableList(new ArrayList<>(stringBankAccountMap.keySet())));
        viewModel.setAccountData(stringBankAccountMap);
    }

    private void setTransactionListView(List<Transaction> transactions) {

        transactionTableView.setItems(FXCollections.observableList(transactions));

    }

    @Override
    public void disposeObservables() {
        Debug.log(TAG,"Disposing Observables");
        mObservables.clear();
    }

    @FXML
    private void accDropdownClicked(ActionEvent actionEvent) {
        viewModel.accountSelected(accountNumberDrop.getValue());

    }
}
