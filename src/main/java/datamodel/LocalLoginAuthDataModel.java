package datamodel;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import misc.debug.Debug;
import model.LoginCredentials;
import org.davidmoten.rx.jdbc.Database;
import ui.ViewManager;

public class LocalLoginAuthDataModel implements LoginAuthDataModel {

    BehaviorSubject<Boolean> mAuthorization = BehaviorSubject.create();
    Disposable subscription;
    private static final String TAG = "LoginDataModel";

    public LocalLoginAuthDataModel(){


    }
    @Override
    public void checkAuthorization(LoginCredentials credentials) {
        Debug.printThread(TAG);
        Debug.log(TAG, "Inside checkAuth");
        //Validate Credentials
        Database db = ViewManager.getInstance().getDb();
        subscription = db.select("select uid from Users where Username = ? and Password = ?")
                .parameters(credentials.getUsername(), credentials.getPassword())
                .getAs(Long.class)
                .doOnNext((value)->
                {
                    Debug.printThread(TAG);
                    System.out.println("Printing "+value+" on thread "+ Thread.currentThread().getName());
                })
                .isEmpty()
                .observeOn(Schedulers.computation())
                .subscribe(aBoolean -> {
                    Debug.printThread(TAG);
                    System.out.println("Surprise mofos! \nBoolean is "+ aBoolean);
                    Debug.log(TAG, mAuthorization);
                    if (aBoolean) {
                        mAuthorization.onNext(false);
                    } else {
                        mAuthorization.onNext(true);
                    }
                });
    }

    @Override
    public Observable<Boolean> getAuthorizationStream() {
        return mAuthorization;
    }

    public void onRemove(){
        subscription.dispose();
    }
}
