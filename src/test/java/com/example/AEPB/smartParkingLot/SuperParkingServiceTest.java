package com.example.AEPB.smartParkingLot;

import com.example.AEPB.smartParkingLot.exception.NoFreeParkingSpaceException;
import com.example.AEPB.smartParkingLot.exception.PickUpException;
import com.example.AEPB.smartParkingLot.model.Car;
import com.example.AEPB.smartParkingLot.model.ParkingLot;
import com.example.AEPB.smartParkingLot.model.ParkingTicket;
import com.example.AEPB.smartParkingLot.service.SuperParkingService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class SuperParkingServiceTest {

    private SuperParkingService superParkingService;

    @Before
    public void setUp() {
        superParkingService = new SuperParkingService(
                List.of(ParkingLot.initParkingLot(8),ParkingLot.initParkingLot(10),ParkingLot.initParkingLot(10))
        );
    }

    @Test
    public void should_park_in_the_parking_lot_with_the_highest_vacancy_rate(){
        //given
        Car car = Car.builder().numberPlate("陕A001").build();
        Car car2 = Car.builder().numberPlate("陕A002").build();

        //when
        ParkingTicket parkingTicket = superParkingService.parking(car);
        ParkingTicket parkingTicket2 = superParkingService.parking(car2);

        //then
        assertEquals("01-001-陕A001",parkingTicket.toString());
        assertEquals("02-001-陕A002",parkingTicket2.toString());
    }

    @Test(expected = NoFreeParkingSpaceException.class)
    public void should_throw_exception_when_parking_lot_no_space(){
        //given
        superParkingService = new SuperParkingService(
                List.of(ParkingLot.initParkingLot(0),ParkingLot.initParkingLot(0),ParkingLot.initParkingLot(0))
        );
        Car car = Car.builder().numberPlate("陕A001").build();

        //when
        superParkingService.parking(car);
    }

    @Test
    public void should_pick_up_car_success_when_parking_lot_has_target_car(){
        //given
        ParkingTicket ticket = superParkingService.parking(Car.builder().numberPlate("陕A001").build());

        //when
        Car car = superParkingService.pickUp(ticket);

        //then
        assertEquals("陕A001",car.getNumberPlate());
    }

    @Test(expected = PickUpException.class)
    public void should_throw_exception_when_pick_up_failed() {
        superParkingService.pickUp(ParkingTicket.builder().build());
    }
}
