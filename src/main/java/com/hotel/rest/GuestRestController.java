package com.hotel.rest;

import com.hotel.Helper;
import com.hotel.dto.guest.InsertGuestDto;
import com.hotel.dto.guest.UpdateGuestDto;
import com.hotel.dto.utility.RestResponse;
import com.hotel.service.abstraction.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/guest")
public class GuestRestController {

    @Autowired
    GuestService guestService;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "") String id,
                                      @RequestParam(defaultValue = "") String fullName,
                                      @RequestParam(defaultValue = "1") Integer page) {
        var pageCollection = guestService.getGrid(id, fullName, page);
        return new ResponseEntity<>(
                new RestResponse<>(pageCollection,
                        "Data berhasil ditemukan",
                        HttpStatus.OK.value()
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable(required = true) String id) {
        var dto = guestService.getUpdate(id);
        return new ResponseEntity<>(
                new RestResponse<>(dto,
                        "Data berhasil ditemukan",
                        HttpStatus.OK.value()
                ),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody InsertGuestDto dto, BindingResult br) {
        if (br.hasErrors()) {
            var error = Helper.getErrors(br.getAllErrors());
            return new ResponseEntity<>(
                    new RestResponse<>(error,
                            "Data tidak valid",
                            HttpStatus.BAD_REQUEST.value()
                    ),
                    HttpStatus.BAD_REQUEST
            );
        }
        var result = guestService.saveOne(dto);
        return new ResponseEntity<>(
                new RestResponse<>(result,
                        "Data berhasil ditambahkan",
                        HttpStatus.OK.value()
                ),
                HttpStatus.OK
        );
    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpdateGuestDto dto, BindingResult br) {
        if (br.hasErrors()) {
            var error = Helper.getErrors(br.getAllErrors());
            return new ResponseEntity<>(
                    new RestResponse<>(error,
                            "Data tidak valid",
                            HttpStatus.BAD_REQUEST.value()
                    ),
                    HttpStatus.BAD_REQUEST
            );
        }
        var result = guestService.updateOne(dto);
        return new ResponseEntity<>(
                new RestResponse<>(result,
                        "Data berhasil ditambahkan",
                        HttpStatus.OK.value()
                ),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) String id) {
        var result = guestService.deleteOne(id);
        return new ResponseEntity<>(
                new RestResponse<>(result,
                        "Data berhasil dihapus",
                        HttpStatus.OK.value()
                ),
                HttpStatus.OK
        );
    }
}
