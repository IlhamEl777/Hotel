package com.hotel.rest;

import com.hotel.Helper;
import com.hotel.dto.reservation.InsertReservationDTO;
import com.hotel.dto.reservation.UpdateReservationDTO;
import com.hotel.dto.utility.RestResponse;
import com.hotel.service.abstraction.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    @Autowired
    ReservationService service;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(required = false) Long Id,
                                      @RequestParam(defaultValue = "") String roomId,
                                      @RequestParam(defaultValue = "") String guestUsernameId,
                                      @RequestParam(defaultValue = "") String reservationMethod,
                                      @RequestParam(defaultValue = "1") Integer page) {

        var pageCollection = service.getGrid(Id, roomId, guestUsernameId, reservationMethod, page);

        return new ResponseEntity<>(
                new RestResponse<>(pageCollection,
                        "Data ditemukan",
                        HttpStatus.OK.value()
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable(required = true) Long id) {
        var dto = service.getUpdate(id);

        return new ResponseEntity<>(
                new RestResponse<>(dto,
                        "Data ditemukan",
                        HttpStatus.OK.value()
                ),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody InsertReservationDTO dto, BindingResult br) {
        if (br.hasErrors()) {
            var error = Helper.getErrors(br.getAllErrors());
            return new ResponseEntity<>(
                    new RestResponse<>(error,
                            "Data tidak valid",
                            HttpStatus.UNPROCESSABLE_ENTITY.value()
                    ),
                    HttpStatus.UNPROCESSABLE_ENTITY
            );
        }
        var response = service.saveItem(dto);
        return new ResponseEntity<>(
                new RestResponse<>(response,
                        "Data berhasil disimpan",
                        HttpStatus.CREATED.value()
                ),
                HttpStatus.CREATED
        );
    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpdateReservationDTO dto, BindingResult br) {
        if (br.hasErrors()) {
            var error = Helper.getErrors(br.getAllErrors());
            return new ResponseEntity<>(
                    new RestResponse<>(error,
                            "Data tidak valid",
                            HttpStatus.UNPROCESSABLE_ENTITY.value()
                    ),
                    HttpStatus.UNPROCESSABLE_ENTITY
            );
        }
        var response = service.updateItem(dto);
        return new ResponseEntity<>(
                new RestResponse<>(response,
                        "Data berhasil disimpan",
                        HttpStatus.OK.value()
                ),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) Long id) {
        var response = service.deleteOne(id);
        return new ResponseEntity<>(
                new RestResponse<>(response,
                        "Data berhasil dihapus",
                        HttpStatus.OK.value()
                ),
                HttpStatus.OK
        );
    }
}
