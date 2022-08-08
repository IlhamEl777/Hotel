package com.hotel.service.implemention;

import com.hotel.dao.InventoryRepository;
import com.hotel.dao.RoomInventoryRepository;
import com.hotel.dto.Inventory.InventoryGridDTO;
import com.hotel.dto.Inventory.InsertInventoryDto;
import com.hotel.dto.Inventory.UpdateInventoryDto;
import com.hotel.entity.Inventory;
import com.hotel.service.abstraction.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class InventoryServiceImpl implements InventoryService {

    InventoryRepository inventoryRepository;

    RoomInventoryRepository roomInventoryRepository;

    @Autowired
    public InventoryServiceImpl(InventoryRepository inventoryRepository, RoomInventoryRepository roomInventoryRepository) {
        this.inventoryRepository = inventoryRepository;
        this.roomInventoryRepository = roomInventoryRepository;
    }

    private final int rowInPage = 10;

    @Override
    public Page<InventoryGridDTO> getGrid(String name, int page) {
        var pagination = PageRequest.of(page - 1, rowInPage, Sort.by("id"));
        var data = inventoryRepository.findByIdContaining(name, pagination);
        var grid = data.map(inventory -> new InventoryGridDTO(
                inventory.getId(),
                inventory.getStock(),
                inventory.getDescription()));

        if (grid.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Inventory not found");
        }
        return grid;
    }

    @Override
    public InsertInventoryDto getUpdate(String name) {
        var inventory = inventoryRepository.findById(name).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Inventory not found"));
        var dto = new InsertInventoryDto(
                inventory.getId(),
                inventory.getStock(),
                inventory.getDescription());
        return dto;
    }

    @Override
    public Inventory saveOne(InsertInventoryDto dto) {
        var entity = new Inventory(
                dto.getId(),
                dto.getDescription(),
                dto.getStock()
        );
        var response = inventoryRepository.save(entity);
        return response;
    }

    @Override
    public Inventory updateOne(UpdateInventoryDto dto) {
        var entity = inventoryRepository.findById(dto.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Inventory not found"));
        entity.setStock(dto.getStock());
        entity.setDescription(dto.getDescription());
        var response = inventoryRepository.save(entity);
        return response;
    }

    @Override
    public Long deleteOne(String name) {
        var entity = inventoryRepository.findById(name).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Inventory not found"));
        var totalDependenInventory = roomInventoryRepository.countByInventoryName_Id(entity.getId());
        if (totalDependenInventory == 0) {
            inventoryRepository.deleteById(name);
        }
        return totalDependenInventory;
    }

    @Override
    public Boolean checkExistingInventory(String name) {
        var check = inventoryRepository.existsById(name);
        return check;
    }
}
