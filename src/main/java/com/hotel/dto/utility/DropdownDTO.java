package com.hotel.dto.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DropdownDTO {
    private Object value;
    private String text;

    public static List<DropdownDTO> getRoomType() {
        var roomTypes = List.of(
                new DropdownDTO("", "ALL"),
                new DropdownDTO("RS", "Regular Single Bed"),
                new DropdownDTO("RD", "Regular Double Bed"),
                new DropdownDTO("RT", "Regular Twin bed"),
                new DropdownDTO("VS", "VIP Single Bed"),
                new DropdownDTO("VD", "VIP Double Bed"),
                new DropdownDTO("VT", "VIP Twin Bed")
        );
        return roomTypes;
    }

    public static List<DropdownDTO> getRoomStatus() {
        var roomStatuses = List.of(
                new DropdownDTO("", "ALL"),
                new DropdownDTO("vacant", "Vacant"),
                new DropdownDTO("booked", "Booked")
        );
        return roomStatuses;
    }

    public static List<DropdownDTO> getReservationMethod() {
        var reservationMethods = List.of(
                new DropdownDTO("", "ALL"),
                new DropdownDTO("OW", "Official Web"),
                new DropdownDTO("OS", "On Site"),
                new DropdownDTO("AW", "Agent Web / App")

        );
        return reservationMethods;
    }

    public static List<DropdownDTO> getPaymentMethod() {
        var paymentMethods = List.of(
                new DropdownDTO("", "ALL"),
                new DropdownDTO("CC", "Credit Card"),
                new DropdownDTO("DC", "Direct Debit"),
                new DropdownDTO("VO", "Voucher"),
                new DropdownDTO("EM", "Electronic Money"),
                new DropdownDTO("CA", "Cash")
        );
        return paymentMethods;
    }

    public static List<DropdownDTO> getGender() {
        var gender = List.of(
                new DropdownDTO("", "ALL"),
                new DropdownDTO("M", "Male"),
                new DropdownDTO("F", "Female")
        );
        return gender;
    }
}
