package com.hotel.service.abstraction;

import com.hotel.dto.room.RoomGridDTO;
import com.hotel.dto.room.UpsertRoomDto;
import com.hotel.dto.room.item.InsertInventoryDTO;
import com.hotel.dto.room.item.InventoryGridDTO;
import com.hotel.entity.Room;
import org.springframework.data.domain.Page;

public interface RoomService {
    Page<RoomGridDTO> getInventoryGrid(String number, String roomType, String status, Integer page);

    public UpsertRoomDto getUpdate(String number);

    public Room saveItem(UpsertRoomDto dto);

    public Long[] deleteOne(String number);

    public Boolean checkExistingRoom(String number);

    public Page<InventoryGridDTO> getInventoryGrid(String id, Integer page);

    public InventoryGridDTO saveItem(InsertInventoryDTO dto);

    public Long deleteItem(String room,Long id);

    public Boolean checkExistingInventoryLimitAvailable(String name, Integer qty);

    public Boolean checkExistingRoomInventory(String roomNumber, String inventoryName);

    public Boolean checkExistingInventory(String inventoryName);
}
