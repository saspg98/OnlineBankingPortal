/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import io.reactivex.disposables.CompositeDisposable;
import javafx.fxml.Initializable;
import ui.controllers.ViewModelUser;
import viewmodel.MainScreenViewModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Pranek
 */
public class SidePanelController implements Initializable, ViewModelUser {

    private CompositeDisposable mObservables = new CompositeDisposable();



    @Override
    public void initialize(URL url, ResourceBundle rb) {

        createObservables();
    }

    @Override
    public void createObservables() {

    }

    @Override
    public void disposeObservables() {
        mObservables.clear();
    }
}
