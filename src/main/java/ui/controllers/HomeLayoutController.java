/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import misc.debug.Debug;
import model.BankAccount;
import ui.ViewManager;
import viewmodel.AccountChooserViewModel;
import viewmodel.HomeViewModel;

import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Pranek
 */
public class HomeLayoutController implements Initializable, ViewModelUser{

    private CompositeDisposable mObservables = new CompositeDisposable();
    private AccountChooserViewModel viewModel;
    private final String TAG = "HomeLayoutController";
    private HashMap<String,String> accType = new HashMap<>();

    @FXML
    private Label LAccountTypeOutput;
    @FXML
    private Label LCurrentBalanceOutput;
    @FXML
    private ComboBox<String> accountNumberDrop;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        viewModel = new AccountChooserViewModel(ViewManager.getInstance().getUserDataModel());
        createObservables();
        accType.put("S","Saving");
        accType.put("C","Current");

    }

    @Override
    public void createObservables() {
        mObservables.add(viewModel.getSelectedAccount()
        .observeOn(JavaFxScheduler.platform())
        .subscribe(this::setView, this::onError));
        mObservables.add(viewModel.getBankAccountDetails()
        .observeOn(JavaFxScheduler.platform())
        .subscribe(this::setAccountDetails, this::onError));

    }

    private void setAccountDetails(Map<String, BankAccount> stringBankAccountMap) {
        accountNumberDrop.setItems(FXCollections.observableList(new ArrayList<>(stringBankAccountMap.keySet())));
        viewModel.setAccountData(stringBankAccountMap);
        accountNumberDrop.getSelectionModel().select(0);
    }

    private void setView(BankAccount bankAccount) {

        LAccountTypeOutput.setText(accType.get(bankAccount.Acctype()));
        LCurrentBalanceOutput.setText(bankAccount.balance().toString());
    }

    @Override
    public void disposeObservables() {
        Debug.log(TAG,"Disposing Observables");
        mObservables.clear();
    }

    @FXML
    private void onAccDropdownClicked(ActionEvent actionEvent) {
        viewModel.accountSelected(accountNumberDrop.getValue());
    }
}
