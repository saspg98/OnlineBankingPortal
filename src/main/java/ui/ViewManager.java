package ui;

import datamodel.LocalLoginAuthDataModel;
import datamodel.LocalSignUpAuthDataModel;
import datamodel.LoginAuthDataModel;
import datamodel.SignupAuthDataModel;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import misc.debug.Debug;
import org.davidmoten.rx.jdbc.Database;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import ui.controllers.LoginScreenController;
import ui.controllers.SignUpScreenController;
import viewmodel.constant.Constant;

import java.io.IOException;

public class ViewManager {
    private static ViewManager mInstance;

    private Stage mainStage;
    private Stage newStage;
    private LoginAuthDataModel loginAuthDataModel;
    private SignupAuthDataModel signupAuthDataModel;

    private Database db;

    private FXMLLoader fxmlLoader = null;

    public LoginAuthDataModel getLoginAuthDataModelInstance() {
        if (loginAuthDataModel == null) {
            loginAuthDataModel = new LocalLoginAuthDataModel();
        }
        return loginAuthDataModel;
    }

    public SignupAuthDataModel getSignUpAuthDataModelInstance() {
        if (signupAuthDataModel == null) {
            signupAuthDataModel = new LocalSignUpAuthDataModel();
        }
        return signupAuthDataModel;
    }

    private ViewManager() {

    }

    public static synchronized ViewManager getInstance() {
        if (mInstance == null) {
            mInstance = new ViewManager();
        }
        return mInstance;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public void setScene(String FXMLPATH) {

        Parent root = null;
        try {
            fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(FXMLPATH));
            root = fxmlLoader.load();
        } catch (IOException e) {
            System.out.println("Could not load scene!");
            e.printStackTrace();
        }

        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.centerOnScreen();
        mainStage.show();
        mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                ((LoginScreenController)fxmlLoader.getController()).disposeObservables();
                Debug.err("CLOSING", FXMLPATH);
                System.exit(0);
            }
        });
    }

    public <T> void setScene(String FXMLPATH, T... data) throws Exception {
        //TODO: Handle Passing of data here
        throw new Exception("Data Passing not implemented", new NotImplementedException());

    }

    public void exitScene(){
        mainStage.close();
    }

    public void createSignUp(String FXMLPATH) {

        newStage = new Stage();
        Parent signUpForm = null;
        try {
            signUpForm = FXMLLoader.load(getClass().getClassLoader().getResource(Constant.Path.SIGNUP_VIEW));
        } catch (IOException e) {
            e.printStackTrace();
            Debug.err("Unable to create alert box for sign up");
        }

        Scene scene = new Scene(signUpForm);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setScene(scene);
        newStage.centerOnScreen();
        newStage.show();
        newStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                ((SignUpScreenController)fxmlLoader.getController()).disposeObservables();
                Debug.err("CLOSING",FXMLPATH);
            }
        });
    }

    public void exitSignUp(){
        newStage.close();
    }

    public void setDatabase(Database db) {
        this.db = db;
    }

    public Database getDb() {
        return db;
    }
}
