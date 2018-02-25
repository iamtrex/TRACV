package com.tracv.observerpattern;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {

    List<Observer> observers;

    public Observable(){
        observers = new ArrayList<>();

    }

    public void addObserver(Observer o){
        observers.add(o);
    }

    public void notifyObservers(String msg){
        for(Observer o : observers){
            o.update(this, msg);
        }
    }
}

