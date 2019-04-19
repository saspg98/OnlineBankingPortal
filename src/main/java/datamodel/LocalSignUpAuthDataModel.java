package datamodel;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import misc.debug.Debug;
import model.SignupCredentials;
import org.davidmoten.rx.jdbc.Database;
import ui.ViewManager;

public class LocalSignUpAuthDataModel implements SignupAuthDataModel {

    private BehaviorSubject<Boolean> mConfirmSignUp = BehaviorSubject.create();
    private static final String TAG = "SignupDataModel";

    public LocalSignUpAuthDataModel() {
    }

    @Override
    public void checkSignUpDetails(SignupCredentials credentials) {
        //Debug.printThread(TAG);
        Debug.log(TAG, "Inside checkSignUpDetails");

        Database db = ViewManager.getInstance().getDb();
        db.select("select Password from Users where Username = ?")
                .parameter(credentials.getUsername())
                .getAs(String.class)
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
                    Debug.log(TAG, mConfirmSignUp);
                    if (isNotPresent) {
                        db.select("select uid from Users as u,Accounts as a where a.UID=u.UID and UID=? " +
                                "and Name=? and DOB=? and Email=? and ");
                    } else {
                        mConfirmSignUp.onNext(false);
                    }
                });
    }

    @Override
    public Observable<Boolean> getConfirmSignUpStream() {
        return mConfirmSignUp;
    }


}
