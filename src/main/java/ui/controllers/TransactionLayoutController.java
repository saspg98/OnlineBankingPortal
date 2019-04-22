/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import misc.debug.Debug;
import model.BankAccount;
import model.Transaction;
import ui.ViewManager;
import viewmodel.MakeTransactionViewModel;
import viewmodel.TransactionHistoryViewModel;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Pranek
 */
public class TransactionLayoutController implements Initializable, ViewModelUser, DataReceiver {

    private CompositeDisposable mObservables = new CompositeDisposable();
    private TransactionHistoryViewModel viewModel;
    private final String TAG = "TransactionLayoutController";

    @FXML
    private Label LAccountTypeOutput;
    @FXML
    private Label LAccountNumberOutput;
    @FXML
    private TableView transactionTableView;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @Override
    public <T> void receiveData(T... data) {
        BankAccount bankAccount = (BankAccount) data[0];
        viewModel = new TransactionHistoryViewModel(ViewManager.getInstance().getUserDataModel(), bankAccount);
        createObservables();
    }

    @Override
    public void createObservables() {
        mObservables.add(viewModel.getTransactions()
        .observeOn(JavaFxScheduler.platform())
        .subscribe(this::setViews, this::onError));
    }

    private void setViews(List<Transaction> transactions) {

    }

    @Override
    public void disposeObservables() {
        Debug.log(TAG,"Disposing Observables");
        mObservables.clear();
    }

}
