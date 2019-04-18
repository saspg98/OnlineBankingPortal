package datamodel;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import misc.debug.Debug;
import model.SignupCredentials;
import org.davidmoten.rx.jdbc.Database;
import ui.ViewManager;

public class LocalSignUpAuthDataModel implements SignupAuthDataModel {
    private BehaviorSubject<Boolean> mAuthorization = BehaviorSubject.create();

    private static final String TAG = "SignupDataModel";

    public LocalSignUpAuthDataModel() {
    }


    @Override
    public void checkAuthorization(SignupCredentials credentials) {
        //Debug.printThread(TAG);
        Debug.log(TAG, "Inside checkAuth");
        //Validate Credentials
        Database db = ViewManager.getInstance().getDb();
        db.select("select uid from Users where Username = ?")
                .parameter(credentials.getUsername())
                .getAs(Long.class)
                .doOnNext((value) ->
                {
                    //Somehow never called :(
                    //Debug.printThread(TAG);
                    System.out.println("Printing " + value + " on thread " + Thread.currentThread().getName());
                })
                .isEmpty()
                .observeOn(Schedulers.computation())
                .subscribe(isNotPresent -> {
                    //Debug.printThread(TAG);
                    System.out.println("Surprise mofos! \nBoolean is " + isNotPresent);
                    Debug.log(TAG, mAuthorization);
                    if (isNotPresent) {
                        mAuthorization.onNext(true);
                    } else {
                        mAuthorization.onNext(false);
                    }
                });
    }

    @Override
    public Observable<Boolean> getAuthorizationStream() {
        return mAuthorization;
    }


}
