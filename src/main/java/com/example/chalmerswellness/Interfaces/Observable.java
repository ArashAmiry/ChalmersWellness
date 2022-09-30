package com.example.chalmerswellness.Interfaces;

public interface Observable {
    void notifyObservers();
    void subscribe(Observer observer);
}
