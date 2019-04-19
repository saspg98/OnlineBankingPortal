/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import javafx.fxml.Initializable;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        viewModel = new HomeViewModel(ViewManager.getInstance().getUserDataModel());
        createObservables();
    }

    @Override
    public void createObservables() {
        mObservables.add(viewModel.getSelectedAccount()
        .observeOn(Schedulers.trampoline())
        .subscribe(this::setView, this::onError));
    }

    private void setView(BankAccount bankAccount) {
        
    }

    @Override
    public void disposeObservables() {
        mObservables.clear();
    }
}
