package com.hotel.rest;

import com.hotel.Helper;
import com.hotel.dto.Inventory.InsertInventoryDto;
import com.hotel.dto.Inventory.UpdateInventoryDto;
import com.hotel.service.abstraction.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/inventory")
public class InventoryRestController {

    @Autowired
    InventoryService service;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "") String name,
                                      @RequestParam(defaultValue = "1") Integer page) {
        var pageCollection = service.getGrid(name, page);
        return ResponseEntity.ok(pageCollection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable(required = true) String id) {
        var dto = service.getUpdate(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody InsertInventoryDto dto, BindingResult br) {
        try {
            if (br.hasErrors()) {
                var error = Helper.getErrors(br.getAllErrors());
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
            }
            var response = service.saveOne(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpdateInventoryDto dto, BindingResult br) {
        if (dto.getId() == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Inventory does not exist, make sure you are using the correct id");
        }

        if (br.hasErrors()) {
            var error = Helper.getErrors(br.getAllErrors());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
        }
        var response = service.updateOne(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) String id) {
        var response = service.deleteOne(id);
        if (response > 0){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).
                    body(String.format("Inventory cannot be deleted because it is still in use by %d rooms", response));
        }
        return ResponseEntity.status(HttpStatus.OK).body("Room berhasil dihapus ");
    }
}
