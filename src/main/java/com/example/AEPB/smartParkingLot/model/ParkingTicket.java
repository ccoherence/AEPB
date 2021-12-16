package com.example.AEPB.smartParkingLot.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingTicket {

    private Integer parkingLotNumber;

    private Integer parkingSpaceNumber;

    private String numberPlate;

    @Override
    public String toString() {
       //依次为：停车场编号、停车位、车牌号。
        return String.format("%s-%s-%s",
                String.format("%02d", parkingLotNumber + 1),
                String.format("%03d", parkingSpaceNumber + 1),
                numberPlate);
    }
}
