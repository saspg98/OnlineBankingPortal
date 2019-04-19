package ui.controllers;

public interface ViewModelUser {
    void createObservables();
    void disposeObservables();
    void onError(Throwable throwable);
}
