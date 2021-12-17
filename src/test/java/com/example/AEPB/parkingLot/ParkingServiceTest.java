package com.example.AEPB.parkingLot;

import com.example.AEPB.parkingLot.exception.FakePlateVehiclesException;
import com.example.AEPB.parkingLot.exception.NoFreeParkingSpaceException;
import com.example.AEPB.parkingLot.exception.PickUpException;
import com.example.AEPB.parkingLot.model.Car;
import com.example.AEPB.parkingLot.model.ParkingLot;
import com.example.AEPB.parkingLot.model.ParkingTicket;
import com.example.AEPB.parkingLot.service.PrimaryParkingService;
import com.example.AEPB.parkingLot.service.SmartParkingService;
import com.example.AEPB.parkingLot.service.SuperParkingService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ParkingServiceTest {

    private PrimaryParkingService primaryParkingService;
    private SmartParkingService smartParkingService;
    private SuperParkingService superParkingService;

    @Before
    public void setUp() {
        ParkingLot parkingLot = ParkingLot.initParkingLot(8);
        ParkingLot parkingLot2 = ParkingLot.initParkingLot(9);
        ParkingLot parkingLot3 = ParkingLot.initParkingLot(10);
        primaryParkingService = new PrimaryParkingService(
                List.of(parkingLot,parkingLot2,parkingLot3)
        );
        smartParkingService = new SmartParkingService(
                List.of(parkingLot,parkingLot2,parkingLot3)
        );
        superParkingService = new SuperParkingService(
                List.of(parkingLot,parkingLot2,parkingLot3)
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
        assertEquals("Parking Boy : 1",primaryParkingService.printParkingRecord());
    }

    @Test
    public void should_park_in_the_parking_lot_with_the_most_vacancies(){
        //given
        Car car = Car.builder().numberPlate("陕A001").build();

        //when
        ParkingTicket ticket = smartParkingService.parking(car);

        //then
        assertEquals("03-001-陕A001",ticket.toString());
        assertEquals("Parking Manager : 1",smartParkingService.printParkingRecord());
    }

    @Test
    public void should_park_in_the_parking_lot_with_the_highest_vacancy_rate(){
        //given
        Car car = Car.builder().numberPlate("陕A001").build();
        Car car2 = Car.builder().numberPlate("陕A002").build();

        //when
        ParkingTicket parkingTicket = smartParkingService.parking(car);
        ParkingTicket parkingTicket2 = superParkingService.parking(car2);

        //then
        assertEquals("03-001-陕A001",parkingTicket.toString());
        assertEquals("01-001-陕A002",parkingTicket2.toString());
        assertEquals("Parking Director : 1",superParkingService.printParkingRecord());
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

    @Test(expected = FakePlateVehiclesException.class)
    public void should_throw_exception_when_vehicles_with_cloned_license_plates(){
        //given
        Car car = Car.builder().numberPlate("陕A001").build();
        Car clonedCar = Car.builder().numberPlate("陕A001").build();

        //when
        superParkingService.parking(car);
        primaryParkingService.parking(clonedCar);
    }

    @Test(expected = NoFreeParkingSpaceException.class)
    public void should_throw_exception_when_parking_lot_no_space(){
        //given
        smartParkingService = new SmartParkingService(
                List.of(ParkingLot.initParkingLot(0),ParkingLot.initParkingLot(0),ParkingLot.initParkingLot(0))
        );
        Car car = Car.builder().numberPlate("陕A001").build();

        //when
        smartParkingService.parking(car);
    }

    @Test(expected = PickUpException.class)
    public void should_throw_exception_when_pick_up_failed() {
        primaryParkingService.pickUp(ParkingTicket.builder().build());
    }
}

