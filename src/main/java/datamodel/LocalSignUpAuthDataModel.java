package datamodel;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import misc.debug.Debug;
import model.SignupCredentials;
import org.davidmoten.rx.jdbc.Database;
import ui.ViewManager;

import java.time.Instant;

public class LocalSignUpAuthDataModel implements SignupAuthDataModel {

    private BehaviorSubject<Boolean> mConfirmSignUp = BehaviorSubject.create();
    private static final String TAG = "SignupDataModel";
    boolean isPresent;

    public LocalSignUpAuthDataModel() {
    }

    @Override
    public void checkSignUpDetails(SignupCredentials credentials) {
        //Debug.printThread(TAG);
        Debug.log(TAG, "Inside checkSignUpDetails");

        Flowable<Boolean> aBoolean;
        Flowable<Boolean> aBoolean1 = null;

        Database db = ViewManager.getInstance().getDb();

        aBoolean = db.select("select * from Users as u,Accounts as a where a.UID=u.UID and UID=? " +
                "and Name=? and DOB=? and Email=? and AccNo=? and Address=? and BCode=?")
                .parameters(credentials.getAdhaar(), credentials.getName(), credentials.getDob(), credentials.getEmail(),
                        credentials.getAccountNumber(), credentials.getAddress(), credentials.getBCode())
                .getAsOptional(Instant.class)
                .isEmpty()
                .toFlowable()
                .doOnNext((bool) -> {
                    isPresent = bool;
                    Debug.err("Query1",isPresent);
                });

        if (isPresent) {
            aBoolean1 = db.select("select Username from Users where UID= ?")
                    .dependsOn(aBoolean)
                    .parameter(credentials.getAdhaar())
                    .getAsOptional(Instant.class)
                    .doOnNext((value) ->
                    {
                        //Debug.printThread(TAG);
                        System.out.println("Printing " + value + " on thread " + Thread.currentThread().getName());
                    })
                    .isEmpty()
                    .toFlowable()
                    .doOnNext((bool) -> {
                        isPresent = bool;
                        Debug.err("Query2",isPresent);

                    });
        } else {
            mConfirmSignUp.onNext(false);
        }

        if (!isPresent) {
            aBoolean = db.select("select UID from Users where username=?")
                    .dependsOn(aBoolean1)
                    .parameter(credentials.getUsername())
                    .getAsOptional(Instant.class)
                    .isEmpty()
                    .toFlowable()
                    .doOnNext((bool) -> {
                        isPresent = bool;
                        Debug.err("Query3",isPresent);

                    });
        } else {
            mConfirmSignUp.onNext(false);
        }

        if (!isPresent) {

            db.update("update Users set Username=? , Password=? where UID=?")
                    .dependsOn(aBoolean)
                    .parameters(credentials.getUsername(), credentials.getPassword(),credentials.getAdhaar())
                    .counts()
                    .subscribe((value) -> mConfirmSignUp.onNext(true));

        } else {
            mConfirmSignUp.onNext(false);
        }
    }

    @Override
    public Observable<Boolean> getConfirmSignUpStream() {
        return mConfirmSignUp;
    }
}
