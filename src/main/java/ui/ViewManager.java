package ui;

import datamodel.*;
import io.reactivex.Observable;
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
import ui.controllers.ViewModelUser;
import viewmodel.constant.Constant;

import java.io.IOException;

public class ViewManager {
    private static ViewManager mInstance;

    private Stage loginStage;
    private Stage signUpStage;
    private Stage mainStage;
    private LoginAuthDataModel loginAuthDataModel;
    private SignupAuthDataModel signupAuthDataModel;
    private UserDataModel userDataModel;
    private String TAG = "ViewManager";

    private Database db;
    private long UID;

    private FXMLLoader loginViewFxmlLoader = null;
    private FXMLLoader signUpViewFxmlLoader = null;
    private FXMLLoader mainViewFxmlLoader = null;

    public LoginAuthDataModel getLoginAuthDataModelInstance() {
        if (loginAuthDataModel == null) {
            loginAuthDataModel = new LocalLoginAuthDataModel();
        }
        return loginAuthDataModel;
    }

    public UserDataModel getUserDataModel() {
        if (userDataModel == null) {
            userDataModel = new LocalUserDataModel(UID);
        }
        return userDataModel;
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

    public void setLoginStage(Stage loginStage) {
        this.loginStage = loginStage;
    }

    public void setLoginScene(String FXMLPATH) {
        Debug.log(TAG, FXMLPATH);
        Parent root = null;
        try {
            loginViewFxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(FXMLPATH));
            root = loginViewFxmlLoader.load();
        } catch (IOException e) {
            System.err.println("Could not load login scene!");
            e.printStackTrace();
        }

        Scene scene = new Scene(root);
        loginStage.setScene(scene);
        loginStage.centerOnScreen();
        loginStage.show();
        loginStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                ((ViewModelUser) loginViewFxmlLoader.getController()).disposeObservables();
                Debug.log("CLOSING:" + FXMLPATH);
                System.exit(0);
            }
        });
    }

    public void exitLoginIn() {
        loginStage.close();
        loginAuthDataModel = null;
        setScene(Constant.Path.SIDE_PANE);
    }

    public void setScene(String FXMLPATH) {

        mainStage = new Stage();
        Debug.log(TAG, FXMLPATH);
        Parent root = null;
        Debug.log(TAG, "Printing Location", FXMLPATH);
        mainViewFxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(FXMLPATH));
        try {
            root = mainViewFxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Could not load main scene!");
        }

        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.centerOnScreen();
        mainStage.show();
        mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                ((ViewModelUser) mainViewFxmlLoader.getController()).disposeObservables();
                Debug.log("CLOSING:" + FXMLPATH);
                System.exit(0);
            }
        });
    }

    public <T> void setScene(String FXMLPATH, T... data) throws Exception {
        //TODO: Handle Passing of data here
        throw new Exception("Data Passing not implemented", new NotImplementedException());

        //(DataReceiver).... .re

    }

    public void exitMainScreen() {
        mainStage.close();
        userDataModel = null;
        setLoginScene(Constant.Path.LOGIN_VIEW);
    }

    public void createSignUp(String FXMLPATH) {

        signUpStage = new Stage();
        Parent signUpForm = null;
        try {
            signUpViewFxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(Constant.Path.SIGNUP_VIEW));
            signUpForm = signUpViewFxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            Debug.err("Unable to create alert box for sign up");
        }

        Scene scene = new Scene(signUpForm);
        signUpStage.initModality(Modality.APPLICATION_MODAL);
        signUpStage.setScene(scene);
        signUpStage.centerOnScreen();
        signUpStage.show();
        signUpStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                ((ViewModelUser) signUpViewFxmlLoader.getController()).disposeObservables();
                Debug.err("CLOSING", FXMLPATH);
            }
        });
    }

    public void exitSignUp() {
        signupAuthDataModel=null;
        signUpStage.close();
    }

    public void setDatabase(Database db) {
        this.db = db;
    }

    public Database getDb() {
        return db;
    }

    public void setUid(Observable<Long> uidStream) {
        uidStream.subscribe((uid) -> {
            this.UID = uid;
            Debug.log(TAG, "UID is " + UID);
        }, this::onError);
    }

    private void onError(Throwable throwable) {
        Debug.err(TAG, throwable);
    }
}
