package ui.controllers;

import misc.debug.Debug;

public interface ViewModelUser {
    void createObservables();

    void disposeObservables();

    default void onError(Throwable throwable) {
        Debug.err(this.getClass().getName(), throwable);
    }
}
