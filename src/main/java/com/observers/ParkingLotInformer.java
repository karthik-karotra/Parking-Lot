package com.observers;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotInformer {

    private List<ParkingLotObservers> observersList;
    static ParkingLotInformer parkingLotInformer;

    public static ParkingLotInformer getObjectOfParkingLotInformer() {
        if (parkingLotInformer == null)
            parkingLotInformer = new ParkingLotInformer();
        return parkingLotInformer;
    }

    private ParkingLotInformer() {
        observersList = new ArrayList<>();
    }

    public void notifyParkingFull() {
        for (ParkingLotObservers observers : observersList)
            observers.slotsFull();
    }

    public void notifyParkingAvailable() {
        for (ParkingLotObservers element : observersList)
            element.slotsEmpty();
    }

    public void registerObserver(ParkingLotObservers observer) {
        observersList.add(observer);
    }
}
