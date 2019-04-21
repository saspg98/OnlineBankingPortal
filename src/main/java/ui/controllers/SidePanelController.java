/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import ui.ViewManager;
import ui.controllers.ViewModelUser;
import viewmodel.MainScreenViewModel;
import viewmodel.constant.Constant;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Pranek
 */
public class SidePanelController implements Initializable, ViewModelUser {

    private CompositeDisposable mObservables = new CompositeDisposable();
    private MainScreenViewModel viewModel;

    @FXML
    private GridPane gridPane;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        viewModel = new MainScreenViewModel(ViewManager.getInstance().getUserDataModel());

//        GridPane homePane = null;
//
//        try {
//            homePane = FXMLLoader.load(getClass().getClassLoader().getResource(Constant.Path.HOME_SCREEN_VIEW));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        gridPane.add(homePane,1,0,2,8);
        
        createObservables();
        viewModel.setState(Constant.Path.HOME_SCREEN_VIEW);
    }

    void setState(String path){
        GridPane statePane = null;

        try {
            statePane = FXMLLoader.load(getClass().getClassLoader().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        gridPane.add(statePane,1,0,2,8);


    }

    @Override
    public void createObservables() {
        mObservables.add(viewModel.getStateObservable()
        .observeOn(JavaFxScheduler.platform())
        .subscribe(this::setState, this::onError));
    }

    @Override
    public void disposeObservables() {
        mObservables.clear();
    }

    @FXML
    private void onAccountClicked(ActionEvent actionEvent) {
        viewModel.setState(Constant.Path.ACCOUNT_VIEW);
    }

    @FXML
    private void onSettingClicked(ActionEvent actionEvent) {
        viewModel.setState(Constant.Path.SETTINGS_VIEW);
    }

    @FXML
    private void onHelpClicked(ActionEvent actionEvent) {
        viewModel.setState(Constant.Path.HELP_VIEW);
    }

    @FXML
    private void onTransactionClicked(ActionEvent actionEvent) {
        viewModel.setState(Constant.Path.TRANSACTION_VIEW);
    }

    @FXML
    private void onTransferClicked(ActionEvent actionEvent) {
        viewModel.setState(Constant.Path.TRANSFER_VIEW);
    }

    @FXML
    private void onLogoutClicked(ActionEvent actionEvent) {
        viewModel.onLogout();
    }

    @FXML
    private void onHomeClicked(ActionEvent actionEvent) {
        viewModel.setState(Constant.Path.HOME_SCREEN_VIEW);
    }
}
