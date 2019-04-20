/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import io.reactivex.disposables.CompositeDisposable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
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
    @FXML
    private GridPane gridPane;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        GridPane homePane = null;

        try {
            homePane = FXMLLoader.load(getClass().getClassLoader().getResource(Constant.Path.HOME_SCREEN_VIEW));
        } catch (IOException e) {
            e.printStackTrace();
        }

        gridPane.add(homePane,1,0,2,8);
        
        //createObservables();
    }

    @Override
    public void createObservables() {

    }

    @Override
    public void disposeObservables() {
        mObservables.clear();
    }

    @FXML
    private void onAccountClicked(ActionEvent actionEvent) {
    }

    @FXML
    private void onSettingClicked(ActionEvent actionEvent) {
    }

    @FXML
    private void onHelpClicked(ActionEvent actionEvent) {
    }

    @FXML
    private void onTransactionClicked(ActionEvent actionEvent) {
    }

    @FXML
    private void onTransferClicked(ActionEvent actionEvent) {
    }

    @FXML
    private void onLogoutClicked(ActionEvent actionEvent) {
    }

    @FXML
    private void onHomeClicked(ActionEvent actionEvent) {
    }
}
