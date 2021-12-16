package com.example.AEPB.smartParkingLot;

import com.example.AEPB.smartParkingLot.exception.PickUpException;
import com.example.AEPB.smartParkingLot.model.Car;
import com.example.AEPB.smartParkingLot.model.ParkingLot;
import com.example.AEPB.smartParkingLot.model.ParkingTicket;
import com.example.AEPB.smartParkingLot.service.PrimaryParkingService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class PrimaryParkingServiceTest {

    private PrimaryParkingService primaryParkingService;

    @Before
    public void setUp() {
        primaryParkingService = new PrimaryParkingService(
                List.of(ParkingLot.initParkingLot(8),ParkingLot.initParkingLot(9),ParkingLot.initParkingLot(10))
        );
    }

    @Test
    public void should_sequential_parking_when_parking_lot_has_space(){
        //given
        Car car = Car.builder().numberPlate("陕A001").build();

        //when
        ParkingTicket ticket = primaryParkingService.parking(car);

        //then

        assertEquals("01-001-陕A001",ticket.toString());
    }

    @Test
    public void should_pick_up_car_success_when_parking_lot_has_target_car(){
        //given
        ParkingTicket ticket = primaryParkingService.parking(Car.builder().numberPlate("陕A001").build());

        //when
        Car car = primaryParkingService.pickUp(ticket);

        //then
        assertEquals("陕A001",car.getNumberPlate());
    }

    @Test(expected = PickUpException.class)
    public void should_throw_exception_when_pick_up_failed() {
        primaryParkingService.pickUp(ParkingTicket.builder().build());
    }
}
