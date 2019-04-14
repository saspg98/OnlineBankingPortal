package datamodel;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import model.LoginCredentials;

public class LocalAuthDataModel implements AuthDataModel {

    BehaviorSubject<Boolean> mAuthorization = BehaviorSubject.create();

    @Override
    public void checkAuthorization(LoginCredentials credentials) {
        //TODO: Validate Credentials Implementation
        mAuthorization.onNext(true);
    }

    @Override
    public Observable<Boolean> getAuthorizationStream() {
        return mAuthorization;
    }


}
