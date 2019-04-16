package datamodel;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import model.LoginCredentials;
import org.davidmoten.rx.jdbc.Database;
import ui.ViewManager;

public class LocalLoginAuthDataModel implements LoginAuthDataModel {

    BehaviorSubject<Boolean> mAuthorization = BehaviorSubject.create();

    @Override
    public void checkAuthorization(LoginCredentials credentials) {
        //Validate Credentials
        Database db = ViewManager.getInstance().getDb();
        db.select("select Password from Users where Username = ? and Password = ?")
                .parameter(credentials.getUsername(),credentials.getPassword())
                .getAs(String.class)
                .isEmpty().subscribe(aBoolean -> {
                    if(aBoolean){
                        mAuthorization.onNext(true);
                    }else{
                        mAuthorization.onNext(false);
                    }
                });
    }

    @Override
    public Observable<Boolean> getAuthorizationStream() {
        return mAuthorization;
    }


}
