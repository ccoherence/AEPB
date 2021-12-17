package com.example.AEPB.parkingLot.service;

import com.example.AEPB.parkingLot.exception.FakePlateVehiclesException;
import com.example.AEPB.parkingLot.exception.NoFreeParkingSpaceException;
import com.example.AEPB.parkingLot.exception.PickUpException;
import com.example.AEPB.parkingLot.model.Car;
import com.example.AEPB.parkingLot.model.ParkingLot;
import com.example.AEPB.parkingLot.model.ParkingTicket;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public abstract class ParkingService {

    private List<ParkingLot> parkingLots;

    public ParkingTicket parking(Car car){
        ParkingTicket.ParkingTicketBuilder parkingTicketBuilder = ParkingTicket.builder();
        getParkingLot().ifPresent(parkingLot -> {
            checkPlate(car);
            if (parkingLot.getFreeParkingSpaces() == 0) {
                throw new NoFreeParkingSpaceException("There is no free parking space");
            }
            List<Boolean> parkingSpaceStatus = parkingLot.getParkingSpaceStatus();
            for (int i = 0; i < parkingSpaceStatus.size(); i++) {
                if (Boolean.FALSE.equals(parkingSpaceStatus.get(i))) {
                    parkingLot.park(i, car, getOperator());
                    parkingTicketBuilder
                            .parkingLotNumber(parkingLots.indexOf(parkingLot))
                            .parkingSpaceNumber(i)
                            .numberPlate(car.getNumberPlate());
                    return;
                }
            }
        });

        return parkingTicketBuilder.build();
    }

    public Car pickUp(ParkingTicket parkingTicket){
        try{
            return parkingLots.get(parkingTicket.getParkingLotNumber()).pick(parkingTicket.getParkingSpaceNumber());
        } catch (Exception e){
            throw new PickUpException("Try to pick up failed: %s", e);
        }
    }

    private void checkPlate(Car car){
        for (ParkingLot parkingLot : parkingLots) {
            if(parkingLot.getCarMap().containsValue(car)){
                throw new FakePlateVehiclesException("Vehicles with cloned license plates.");
            }
        }
    }

    protected abstract Optional<ParkingLot> getParkingLot();

    protected abstract String getOperator();

    public String printParkingRecord() {
        Integer statistic = 0;
        for (ParkingLot parkingLot : parkingLots) {
            statistic += parkingLot.getParkingLotStatistic(getOperator());
        }
        return String.format("%s : %s", getOperator(),  statistic);
    }
}
