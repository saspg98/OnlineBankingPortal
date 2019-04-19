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
                .toObservable()
                .doOnNext((aBoolean) -> {

                    System.out.println("Surprise mofos! \nBoolean is " + aBoolean);

                    Debug.log(TAG, mConfirmSignUp);
                    if (aBoolean) {
                        db.select("select distinct BCode from Users as u,Accounts as a where a.UID=u.UID and UID=? " +
                                "and Name=? and DOB=? and Email=? and AccNo=? and ")
                                .parameters(credentials.getAdhaar(),credentials.getName(),credentials.getDob(),credentials.getEmail(),
                                        credentials.getAccountNumber())
                                .getAs(Long.class)
                                .map((value) -> verifyBCode(value,credentials))
                                .subscribe((value)->mConfirmSignUp.onNext(value));

                    } else {
                        mConfirmSignUp.onNext(false);
                    }
                })
                .observeOn(Schedulers.computation())
                .subscribe();
    }

    private boolean verifyBCode(Long value, SignupCredentials credentials) {

        if(value == credentials.getBCode())
            return true;
        return false;
    }

    @Override
    public Observable<Boolean> getConfirmSignUpStream() {
        return mConfirmSignUp;
    }
}
