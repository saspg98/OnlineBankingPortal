package datamodel;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import misc.debug.Debug;
import model.LoginCredentials;
import org.davidmoten.rx.jdbc.Database;
import ui.ViewManager;

public class LocalLoginAuthDataModel implements LoginAuthDataModel {

    private BehaviorSubject<Boolean> mAuthorization = BehaviorSubject.create();
    private BehaviorSubject<Long> mUidStream = BehaviorSubject.create();
    private static final String TAG = "LoginDataModel";

    public LocalLoginAuthDataModel() {
    }

    @Override
    public void checkAuthorization(LoginCredentials credentials) {
        //Debug.printThread(TAG);
        Debug.log(TAG, "Inside checkAuth","Login Creds:", credentials.getUsername(), credentials.getPassword());
        //Validate Credentials
        Database db = ViewManager.getInstance().getDb();
        db.select("select uid from LoginCreds where Username = ? and Password = ?")
                .parameters(credentials.getUsername(), credentials.getPassword())
                .getAs(Long.class)
                .doOnNext((value) ->
                {
                    //Debug.printThread(TAG);
                    System.out.println("Printing " + value + " on thread " + Thread.currentThread().getName());
                })
//                .isEmpty()
                .observeOn(Schedulers.computation())
                .subscribe(uid -> {
                    //Debug.printThread(TAG);
                    System.out.println("Surprise mofos! \nUID is " + uid);
                    Debug.log(TAG, mAuthorization);
                    mAuthorization.onNext(true);
                    mUidStream.onNext(uid);
                }, this::onError, ()-> {mAuthorization.onNext(false);});
    }

    private void onError(Throwable throwable) {
        Debug.err(TAG, throwable);
    }

    @Override
    public Observable<Boolean> getAuthorizationStream() {
        return mAuthorization;
    }

    @Override
    public Observable<Long> getUidStream() {return mUidStream;};


}
