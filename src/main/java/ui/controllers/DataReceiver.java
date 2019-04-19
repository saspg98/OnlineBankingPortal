package ui.controllers;

public interface DataReceiver {
    <T extends Object> void receiveData(T ... data);
}
