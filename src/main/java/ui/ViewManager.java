package ui;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposables;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.changeapi.ChangeListener;
import model.changeapi.ChangeableBase;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;

public class ViewManager {
    private  static ViewManager mInstance;

    private Stage mainStage;

    private ViewManager() {

    }

    public static synchronized ViewManager getInstance(){
        if(mInstance == null){
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
            root = FXMLLoader.load(getClass().getResource(FXMLPATH));
        } catch (IOException e) {
            System.out.println("Could not load scene!");
            e.printStackTrace();
        }

        Scene scene = new Scene(root);

        mainStage.setScene(scene);
    }

    public <T> void setScene(String FXMLPATH, T ... data) throws Exception {
        //TODO: Handle Passing of data here
        throw new Exception("Data Passing not implemented", new NotImplementedException());

    }
}
