/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import misc.debug.Debug;
import model.User;
import ui.ViewManager;
import ui.controllers.ViewModelUser;
import viewmodel.SettingsViewModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Pranek
 */
public class MyAccountLayoutController implements Initializable, ViewModelUser {
    private CompositeDisposable mObservables = new CompositeDisposable();
    private SettingsViewModel viewModel;
    @FXML
    private Label LNameOutput;
    @FXML
    private Label LDate;
    @FXML
    private Label LEmailIdOutput;
    @FXML
    private Label LPhoneNumberOutput;
    @FXML
    private Label LAddressOutput;
    @FXML
    private Label LAadhaarCardOutput;
    @FXML
    private Label LGenderOutput;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        viewModel = new SettingsViewModel(ViewManager.getInstance().getUserDataModel());
        createObservables();
    }

    public void onUpdateInfo(){
        //TODO: Create a DefaultUser Object and call setUserDetails
        //User user = new DefaultUser(...);
        //viewModel.setUserDetails(user);
    }

    @Override
    public void createObservables() {
        mObservables.add(viewModel.getUserDetails()
                .observeOn(JavaFxScheduler.platform())
                .subscribe((user) -> setViews(user), this::onError ));
    }

    public void setViews(User user){
        //TODO: Implementation
    }


    @Override
    public void disposeObservables() {
        mObservables.clear();
    }

    @Override
    public void onError(Throwable throwable) {
        Debug.err("MyAccountController", throwable);
    }
}
