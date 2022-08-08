package com.hotel.service.implemention;

import com.hotel.dao.InventoryRepository;
import com.hotel.dao.ReservationRepository;
import com.hotel.dao.RoomInventoryRepository;
import com.hotel.dao.RoomRepository;
import com.hotel.dto.room.RoomGridDTO;
import com.hotel.dto.room.UpsertRoomDto;
import com.hotel.dto.room.item.InsertInventoryDTO;
import com.hotel.dto.room.item.InventoryGridDTO;
import com.hotel.dto.utility.DropdownDTO;
import com.hotel.entity.Room;
import com.hotel.entity.RoomInventory;
import com.hotel.service.abstraction.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class RoomServiceImpl implements RoomService {

    RoomRepository roomRepository;
    ReservationRepository reservationRepository;

    RoomInventoryRepository roomInventoryRepository;

    InventoryRepository inventoryRepository;


    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository, ReservationRepository reservationRepository, RoomInventoryRepository roomInventoryRepository, InventoryRepository inventoryRepository) {
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
        this.roomInventoryRepository = roomInventoryRepository;
        this.inventoryRepository = inventoryRepository;
    }

    private final int rowInPage = 10;

    @Override
    public Page<RoomGridDTO> getInventoryGrid(String number, String roomType, String status, Integer page) {
        var pagination = PageRequest.of(page - 1, rowInPage, Sort.by("id"));
        var grid = roomRepository.findAllByIdContainingAndRoomTypeContaining(number, roomType, pagination);
        var filtered = grid.map(x -> new RoomGridDTO(
                        x.getId(),
                        x.getFloor(),
                        DropdownDTO.getRoomType().stream().filter(y -> y.getValue().equals(x.getRoomType())).findFirst().get().getText(),
                        x.getGuestLimit(),
                        x.getCost(),
                        reservationRepository.countByroomNumber_IdAndCheckOutIsGreaterThanEqual(x.getId(), LocalDateTime.now()) > 0 ? "booked" : "vacant"))
                .filter(x -> x.getStatus().contains(status)).toList();
        if (filtered.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No data found");
        }
        return new PageImpl<>(filtered, pagination, grid.getTotalElements());
    }

    @Override
    public UpsertRoomDto getUpdate(String number) {
        var entity = roomRepository.findById(number).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No data found"));
        var dto = new UpsertRoomDto(
                entity.getId(),
                entity.getFloor(),
                entity.getRoomType(),
                entity.getGuestLimit(),
                entity.getDescription(),
                entity.getCost());
        return dto;
    }

    @Override
    public Room saveItem(UpsertRoomDto dto) {
        var entity = new Room(
                dto.getId(),
                dto.getFloor(),
                dto.getRoomType(),
                dto.getGuestLimit(),
                dto.getDescription(),
                dto.getCost()
        );
        var response = roomRepository.save(entity);
        return response;
    }

    @Override
    public Long[] deleteOne(String number) {
        var entity = roomRepository.findById(number).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No data found"));
        var totalDependenInventory = roomInventoryRepository.countByRoomNumber_Id(number);
        var totalDependenReservation = reservationRepository.countByRoomNumber_Id(number);
        if (totalDependenInventory == 0 && totalDependenReservation == 0) {
            roomRepository.deleteById(entity.getId());
        }
        return new Long[]{totalDependenInventory, totalDependenReservation};
    }

    @Override
    public Boolean checkExistingRoom(String number) {
        var exist = roomRepository.existsById(number);
        return exist;
    }

    @Override
    public Page<InventoryGridDTO> getInventoryGrid(String id, Integer page) {
        var pagination = PageRequest.of(page - 1, rowInPage, Sort.by("id"));
        var entity = roomInventoryRepository.findByRoomNumber_Id(id, pagination);
        var grid = entity.map(x -> new InventoryGridDTO(
                x.getId(),
                x.getInventoryName().getId(),
                x.getInventoryName().getStock(),
                x.getQuantity()
        )).toList();
        if (grid.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No data found");
        }
        return new PageImpl<>(grid, pagination, entity.getTotalElements());
    }


    @Override
    public InventoryGridDTO saveItem(InsertInventoryDTO dto) {
        var room = roomRepository.findById(dto.getRoomNumber()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));
        var inventory = inventoryRepository.findById(dto.getInventoryName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found"));
        var entity = new RoomInventory(
                room,
                inventory,
                dto.getQuantity()
        );
        inventory.setStock(inventory.getStock() - dto.getQuantity());
        roomInventoryRepository.save(entity);
        inventoryRepository.save(inventory);
        var response = new InventoryGridDTO(
                entity.getId(),
                entity.getInventoryName().getId(),
                entity.getInventoryName().getStock(),
                entity.getQuantity()
        );
        return response;
    }

    @Override
    public Long deleteItem(String roomId, Long id) {
        roomRepository.findById(roomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));
        var entity = roomInventoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No data found"));
        var inventory = inventoryRepository.findById(entity.getInventoryName().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found"));

        var count = roomInventoryRepository.countById(id);
        if (count == 1) {
            roomInventoryRepository.deleteById(id);
            inventory.setStock(inventory.getStock() + entity.getQuantity());
            inventoryRepository.save(inventory);
        }
        return count;
    }

    @Override
    public Boolean checkExistingInventoryLimitAvailable(String name, Integer qty) {
        var check = inventoryRepository.existsByIdAndStockGreaterThanEqual(name, qty);
        return check;
    }

    @Override
    public Boolean checkExistingRoomInventory(String roomNumber, String inventoryName) {
        var check = roomInventoryRepository.existsByRoomNumber_IdAndInventoryName_Id(roomNumber, inventoryName);
        return check;
    }

    @Override
    public Boolean checkExistingInventory(String inventoryName) {
        var check = inventoryRepository.existsById(inventoryName);
        return check;
    }


}
