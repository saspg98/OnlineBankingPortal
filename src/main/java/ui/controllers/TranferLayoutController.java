/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import javafx.fxml.Initializable;
import misc.debug.Debug;
import model.BankAccount;
import model.Beneficiary;
import ui.ViewManager;
import ui.controllers.DataReceiver;
import ui.controllers.ViewModelUser;
import viewmodel.MakeTransactionViewModel;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Pranek
 */
public class TranferLayoutController implements Initializable, ViewModelUser, DataReceiver {

    private MakeTransactionViewModel viewModel;
    private BankAccount bankAccount;
    private CompositeDisposable mObservables = new CompositeDisposable();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        viewModel = new MakeTransactionViewModel(ViewManager.getInstance().getUserDataModel(), bankAccount);
        createObservables();
    }

    @Override
    public void createObservables() {
        mObservables.add(viewModel.getAmountValidityStream()
                        .observeOn(Schedulers.trampoline())
                        .subscribe(this::onAmountValid, this::onError));
        mObservables.add(viewModel.getBenefactors()
        .observeOn(Schedulers.trampoline())
        .subscribe(this::onDetailsReceived, this::onError));
        mObservables.add(viewModel.getSelectedBenefactor()
        .observeOn(Schedulers.trampoline())
        .subscribe(this::onBeneficiarySelected, this::onError));
        mObservables.add(viewModel.getTransacationSuccessStream()
        .observeOn(Schedulers.trampoline())
        .subscribe(this::onTransactionSuccess, this::onError));
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

    private void onAmountValid(boolean aBoolean) {
        //Handle the case when the amount the user entered is valid
        viewModel.makeTransaction();
    }

    @Override
    public void disposeObservables() {
        mObservables.clear();
    }

    @Override
    public <T> void receiveData(T... data) {
        bankAccount = (BankAccount) data[0];
    }
}
