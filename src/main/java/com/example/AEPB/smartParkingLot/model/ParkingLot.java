package com.example.AEPB.smartParkingLot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLot {

    private Integer freeParkingSpaces;

    private List<Boolean> parkingSpaceStatus;

    private Map<Integer, Car> carMap;

   public static ParkingLot initParkingLot(Integer totalParkingSpaces){
       List<Boolean> parkingSpaceStatus=new ArrayList<>(Arrays.asList(new Boolean[totalParkingSpaces]));
       Collections.fill(parkingSpaceStatus, Boolean.FALSE);
       return ParkingLot.builder()
               .freeParkingSpaces(totalParkingSpaces)
               .parkingSpaceStatus(parkingSpaceStatus)
               .carMap(new HashMap<>(totalParkingSpaces))
               .build();
   }

    public void park(Integer parkingSpace, Car car){
        freeParkingSpaces--;
        parkingSpaceStatus.set(parkingSpace, true);
        carMap.put(parkingSpace, car);
    }

    public Car pick(Integer parkingSpace){
        freeParkingSpaces++;
        parkingSpaceStatus.set(parkingSpace, false);
        Car pickCar = carMap.get(parkingSpace);
        carMap.remove(parkingSpace);
        return pickCar;
    }

}