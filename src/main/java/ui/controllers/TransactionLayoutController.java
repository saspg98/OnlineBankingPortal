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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import misc.debug.Debug;
import model.BankAccount;
import model.Transaction;
import ui.ViewManager;
import viewmodel.TransactionHistoryViewModel;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.*;

/**
 * FXML Controller class
 *
 * @author Pranek
 */
public class TransactionLayoutController implements Initializable, ViewModelUser {

    private CompositeDisposable mObservables = new CompositeDisposable();
    private TransactionHistoryViewModel viewModel;
    private final String TAG = "TransactionLayoutController";
    private HashMap<String, String> accMap = new HashMap<>();

    @FXML
    TableView<Transaction> transactionTableView;
    @FXML
    ComboBox<String> accountNumberDrop;
    @FXML
    TableColumn<Transaction, BigDecimal> CAmount;
    @FXML
    TableColumn<Transaction, BigInteger> CReceiver;
    @FXML
    TableColumn<Transaction, BigInteger> CSender;
    @FXML
    TableColumn<Transaction, Long> CTid;
    @FXML
    TableColumn<Transaction, Date> CDate;
    @FXML
    private Label accType;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        viewModel = new TransactionHistoryViewModel(ViewManager.getInstance().getUserDataModel());
        accountNumberDrop.getSelectionModel().select(0);
        accMap.put("S", "Saving");
        accMap.put("C", "Current");
        createObservables();
    }

    @Override
    public void createObservables() {
        mObservables.add(viewModel.getTransactionStream()
                .observeOn(JavaFxScheduler.platform())
                .subscribe(this::setTransactionListView, this::onError));
        mObservables.add(viewModel.getBankAccountDetails()
                .observeOn(JavaFxScheduler.platform())
                .subscribe(this::setAccountViewDetails, this::onError));
        mObservables.add(viewModel.getSelectedAccountStream()
                .observeOn(JavaFxScheduler.platform())
                .subscribe(this::onAccountSelected, this::onError));
    }

    private void onAccountSelected(BankAccount account) {
        accType.setText(accMap.get(account.Acctype()));
    }

    private void setAccountViewDetails(Map<String, BankAccount> stringBankAccountMap) {
        accountNumberDrop.setItems(FXCollections.observableList(new ArrayList<>(stringBankAccountMap.keySet())));
        viewModel.setAccountData(stringBankAccountMap);
        accountNumberDrop.getSelectionModel().select(0);
    }

    private void setTransactionListView(List<Transaction> transactions) {
        CAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        CReceiver.setCellValueFactory(new PropertyValueFactory<>("Receiver"));
        CSender.setCellValueFactory(new PropertyValueFactory<>("Sender"));
        CTid.setCellValueFactory(new PropertyValueFactory<>("ID"));
        CDate.setCellValueFactory(new PropertyValueFactory<>("Time"));
        transactionTableView.getItems().setAll(transactions);
    }

    @Override
    public void disposeObservables() {
        Debug.log(TAG, "Disposing Observables");
        mObservables.clear();
        viewModel = null;
        accMap = null;
    }

    @FXML
    private void accDropdownClicked(ActionEvent actionEvent) {
        viewModel.accountSelected(accountNumberDrop.getValue());
    }
}
