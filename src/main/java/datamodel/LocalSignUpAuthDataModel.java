package datamodel;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import misc.debug.Debug;
import model.SignupCredentials;
import org.davidmoten.rx.jdbc.Database;
import ui.ViewManager;

import java.time.Instant;

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

        db.select("select * from Users as u,Accounts as a where a.UID=u.UID and u.UID=? " +
                "and Name=? and DOB=? and Email=? and AccNo=? and Address=? and BCode=?")
                .parameters(credentials.getAdhaar(), credentials.getName(), credentials.getDob(), credentials.getEmail(),
                        credentials.getAccountNumber(), credentials.getAddress(), credentials.getBCode())
                .getAsOptional(Instant.class)
                .isEmpty()
                .toFlowable()
                .observeOn(Schedulers.computation())
//                .doOnNext((bool) -> {
//                    isPresent = bool;
//                    Debug.err("Query1",isPresent);
//                })
                .flatMap((event)->{
                    if(!event)
                    return db.select("select Username from LoginCreds where UID= ?")
                            .parameter(credentials.getAdhaar())
                            .getAsOptional(Instant.class)
//                            .doOnNext((value) ->
//                            {
//                                //Debug.printThread(TAG);
//                                System.out.println("Printing " + value + " on thread " + Thread.currentThread().getName());
//                            })
                            .isEmpty()
                            .toFlowable();
//                            .doOnNext((bool) -> {
//                                isPresent = bool;
//                                Debug.err("Query2",isPresent);
//
//                            });
                    else
                        return Flowable.just(true);
                })
                .flatMap((event)->{
                    if(event)
                    return db.select("select UID from LoginCreds where username=?")
                            .parameter(credentials.getUsername())
                            .getAsOptional(Instant.class)
                            .isEmpty()
                            .toFlowable();
//                            .doOnNext((bool) -> {
//                                isPresent = bool;
//                                Debug.err("Query3",isPresent);
//                             });
                    else
                        return Flowable.just(true);
                })
                .flatMap((event)->{

                    if(event)
                    return db.update("insert into LoginCreds values( UID=? , Username=? , Password=?"))
                            .parameters(credentials.getUsername(), credentials.getPassword(),credentials.getAdhaar())
                            .counts();
                    else
                        return Flowable.just(-1);

                })
                .subscribe((value) -> {
                    Debug.err("Value:",value.toString());
                    if(value == 1)
                        mConfirmSignUp.onNext(true);
                    else
                        mConfirmSignUp.onNext(false);
                }, (error)->{
                    Debug.err(TAG, error);
                });
    }

    @Override
    public Observable<Boolean> getConfirmSignUpStream() {
        return mConfirmSignUp;
    }
}
