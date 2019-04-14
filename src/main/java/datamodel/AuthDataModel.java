package datamodel;

import io.reactivex.*;
import model.LoginCredentials;

public interface AuthDataModel {
    //Validate credentials from the database
    void checkAuthorization(LoginCredentials credentials);
    Observable<Boolean> getAuthorizationStream();
}
