/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import misc.debug.Debug;
import model.BankAccount;
import ui.ViewManager;
import viewmodel.HomeViewModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Pranek
 */
public class HomeLayoutController implements Initializable, ViewModelUser{

    private CompositeDisposable mObservables = new CompositeDisposable();
    private HomeViewModel viewModel;
    private final String TAG = "HomeLayouttController";

    @FXML
    private Label LAccountNumberOutput;
    @FXML
    private Label LAccountTypeOutput;
    @FXML
    private Label LCurrentBalanceOutput;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        viewModel = new HomeViewModel(ViewManager.getInstance().getUserDataModel());
        createObservables();
    }

    @Override
    public void createObservables() {
        mObservables.add(viewModel.getSelectedAccount()
        .observeOn(JavaFxScheduler.platform())
        .subscribe(this::setView, this::onError));
    }

    private void setView(BankAccount bankAccount) {

    }

    @Override
    public void disposeObservables() {
        Debug.log(TAG,"Disposing Observables");
        mObservables.clear();
    }
}
