package com.example.AEPB.smartParkingLot.service;

import com.example.AEPB.smartParkingLot.exception.NoFreeParkingSpaceException;
import com.example.AEPB.smartParkingLot.exception.PickUpException;
import com.example.AEPB.smartParkingLot.model.Car;
import com.example.AEPB.smartParkingLot.model.ParkingLot;
import com.example.AEPB.smartParkingLot.model.ParkingTicket;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public abstract class ParkingService {

    private List<ParkingLot> parkingLots;

    public ParkingTicket parking(Car car){
        Optional<ParkingLot> parkingLot = getParkingLot();
        if (parkingLot.isPresent() && !parkingLot.get().getCarMap().containsValue(car)) {
            if(parkingLot.get().getFreeParkingSpaces() <= 0){
                throw new NoFreeParkingSpaceException("There is no free parking space");
            }
            List<Boolean> parkingSpaceStatus = parkingLot.get().getParkingSpaceStatus();
            for (int i = 0; i < parkingSpaceStatus.size(); i++) {
                if (Boolean.FALSE.equals(parkingSpaceStatus.get(i))) {
                    parkingLot.get().park(i, car);
                    return ParkingTicket.builder()
                            .parkingLotNumber(parkingLots.indexOf(parkingLot.get()))
                            .parkingSpaceNumber(i)
                            .numberPlate(car.getNumberPlate()).build();
                }
            }
        }
        return ParkingTicket.builder().build();
    }

    public Car pickUp(ParkingTicket parkingTicket){
        try{
            return parkingLots.get(parkingTicket.getParkingLotNumber()).pick(parkingTicket.getParkingSpaceNumber());
        } catch (Exception e){
            throw new PickUpException(String.format("Try to pick up failed: %s", e));
        }
    }

    protected abstract Optional<ParkingLot> getParkingLot();
}
