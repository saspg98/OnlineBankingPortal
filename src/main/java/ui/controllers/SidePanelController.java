/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import misc.debug.Debug;
import ui.ViewManager;
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
    private FXMLLoader fxmlLoader = null;
    private final String TAG = "SidePanelConstroller";

    @FXML
    private GridPane gridPane;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        viewModel = new MainScreenViewModel(ViewManager.getInstance().getUserDataModel());
        createObservables();
        viewModel.setState(Constant.Path.HOME_SCREEN_VIEW);
    }

    void setState(MainScreenViewModel.StateInformation stateInformation){
        GridPane newStatePane = null;
        String path = stateInformation.getNextStatePath();

        if(fxmlLoader != null)
            ((ViewModelUser)fxmlLoader.getController()).disposeObservables();

        try {
            Debug.log(path);
            fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(path));
            newStatePane = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        gridPane.getChildren().removeAll(stateInformation.getCurrentState());
        gridPane.add(newStatePane,1,0,2,8);
        viewModel.setGridPane(newStatePane);
    }

    @Override
    public void createObservables() {
        mObservables.add(viewModel.getStateObservable()
        .observeOn(JavaFxScheduler.platform())
        .subscribe(this::setState, this::onError));
    }

    @Override
    public void disposeObservables() {
        Debug.log(TAG,"Disposing Observables");
        mObservables.clear();
    }

    @FXML
    private void onAccountClicked(ActionEvent actionEvent) {
        viewModel.setState(Constant.Path.MY_ACCOUNT_LAYOUT);
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
