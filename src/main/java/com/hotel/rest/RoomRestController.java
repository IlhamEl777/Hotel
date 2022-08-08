package com.hotel.rest;

import com.hotel.Helper;
import com.hotel.dto.room.UpsertRoomDto;
import com.hotel.dto.room.item.InsertInventoryDTO;
import com.hotel.dto.utility.RestResponse;
import com.hotel.service.abstraction.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/room")
public class RoomRestController {

    @Autowired
    private RoomService service;

    @GetMapping
    public ResponseEntity<RestResponse<Object>> get(@RequestParam(defaultValue = "") String number,
                                                    @RequestParam(defaultValue = "") String roomType,
                                                    @RequestParam(defaultValue = "") String status,
                                                    @RequestParam(defaultValue = "1") Integer page) {
        var pageCollection = service.getInventoryGrid(number, roomType, status, page);
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
        var dto = service.getUpdate(id);
        return new ResponseEntity<>(
                new RestResponse<>(dto,
                        "Data berhasil ditemukan",
                        HttpStatus.OK.value()
                ),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertRoomDto dto, BindingResult br) {
        if (br.hasErrors()) {
            var error = Helper.getErrors(br.getAllErrors());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
        }
        var response = service.saveItem(dto);
        return new ResponseEntity<>(
                new RestResponse<>(response,
                        "Data berhasil disimpan",
                        HttpStatus.OK.value()
                ),
                HttpStatus.OK
        );
    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpsertRoomDto dto, BindingResult br) {
        if (dto.getId() == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Id is required");
        }
        if (service.checkExistingRoom(dto.getId()) == false) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Room does not exist");
        }
        if (br.hasErrors()) {
            var error = Helper.getErrors(br.getAllErrors());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
        }
        var response = service.saveItem(dto);
        return new ResponseEntity<>(
                new RestResponse<>(response,
                        "Data berhasil disimpan",
                        HttpStatus.OK.value()
                ),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) String id) {

        var response = service.deleteOne(id);
        if ((response[0] > 0 && response[1] > 0) || (response[0] > 0 && response[1] == 0) || (response[0] == 0 && response[1] > 0)) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(String.format("Tidak dapat dihapus. Ditemukan %s Inventory & %s Riwayat Reservation", response[0], response[1]));

        }
        return new ResponseEntity<>(
                new RestResponse<>(response,
                        "Data berhasil dihapus",
                        HttpStatus.OK.value()
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}/item")
    public ResponseEntity<Object> getItem(@PathVariable(required = true) String id,
                                          @RequestParam(defaultValue = "1") Integer page) {
        var pageCollection = service.getInventoryGrid(id, page);
        return new ResponseEntity<>(
                new RestResponse<>(pageCollection,
                        "Data berhasil ditemukan",
                        HttpStatus.OK.value()
                ),
                HttpStatus.OK
        );
    }

    @PostMapping("/item")
    public ResponseEntity<Object> postItem(@Valid @RequestBody InsertInventoryDTO dto, BindingResult br) {

        if (br.hasErrors()) {
            var error = Helper.getErrors(br.getAllErrors());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
        }
        var response = service.saveItem(dto);
        return new ResponseEntity<>(
                new RestResponse<>(response,
                        "Data berhasil disimpan",
                        HttpStatus.OK.value()
                ),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}/item/{itemId}")
    public ResponseEntity<Object> deleteItem(@PathVariable(required = true) String id,
                                             @PathVariable(required = true) Long itemId) {
        var response = service.deleteItem(id, itemId);
        if (response > 0) {
            return new ResponseEntity<>(
                    new RestResponse<>(response,
                            "Item berhasil dihapus",
                            HttpStatus.OK.value()
                    ),
                    HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
                new RestResponse<>(response,
                        "Item tidak ditemukan",
                        HttpStatus.UNPROCESSABLE_ENTITY.value()
                ),
                HttpStatus.UNPROCESSABLE_ENTITY
        );

    }


}
