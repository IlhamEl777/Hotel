package com.hotel.service.abstraction;

import com.hotel.dto.guest.GuestGridDTO;
import com.hotel.dto.guest.InsertGuestDto;
import com.hotel.dto.guest.UpdateGuestDto;
import org.springframework.data.domain.Page;

public interface GuestService {
    public Page<GuestGridDTO> getGrid(String id, String fullName, int page);

    public InsertGuestDto getUpdate(String name);

    public GuestGridDTO saveOne(InsertGuestDto dto);

    public GuestGridDTO updateOne(UpdateGuestDto dto);

    public Boolean deleteOne(String name);

    public Boolean checkExistingGuest(String id);

    public Boolean notSpace(String input);

}
