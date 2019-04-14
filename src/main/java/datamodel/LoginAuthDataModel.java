package datamodel;

import io.reactivex.*;
import model.LoginCredentials;

public interface LoginAuthDataModel {
    //Validate credentials from the database
    void checkAuthorization(LoginCredentials credentials);
    Observable<Boolean> getAuthorizationStream();
}