package com.example.chalmerswellness;

public interface Observable {
    void notifyObservers();
    void subscribe(Observer observer);
}
