package com.example.AEPB.parkingLot.service;

import com.example.AEPB.parkingLot.model.ParkingLot;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class SuperParkingService extends ParkingService{
    private static final String PARKING_DIRECTOR = "Parking Director";
    private final List<ParkingLot> parkingLots;

    public SuperParkingService(List<ParkingLot> parkingLots) {
        super(parkingLots);
        this.parkingLots = parkingLots;
    }

    @Override
    protected Optional<ParkingLot> getParkingLot() {
        return parkingLots.stream().max(Comparator.comparing(ParkingLot::getParkingLotVacancyRate));
    }

    @Override
    protected String getOperator() {
        return PARKING_DIRECTOR;
    }
}
