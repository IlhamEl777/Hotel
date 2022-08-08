package com.hotel.service.abstraction;

import com.hotel.dto.Inventory.InventoryGridDTO;
import com.hotel.dto.Inventory.InsertInventoryDto;
import com.hotel.dto.Inventory.UpdateInventoryDto;
import com.hotel.entity.Inventory;
import org.springframework.data.domain.Page;

public interface InventoryService {

    public Page<InventoryGridDTO> getGrid(String name, int page);

    public InsertInventoryDto getUpdate(String name);

    public Inventory saveOne(InsertInventoryDto dto);

    public Inventory updateOne(UpdateInventoryDto dto);

    public Long deleteOne(String name);

    public Boolean checkExistingInventory(String name);

}
