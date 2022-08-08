package com.hotel.service.implemention;

import com.hotel.dao.GuestRepository;
import com.hotel.dto.guest.GuestGridDTO;
import com.hotel.dto.guest.InsertGuestDto;
import com.hotel.dto.guest.UpdateGuestDto;
import com.hotel.dto.utility.DropdownDTO;
import com.hotel.entity.Guest;
import com.hotel.service.abstraction.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GuestServiceImpl implements GuestService {

    @Autowired
    GuestRepository guestRepository;

    private final int rowInPage = 10;


    @Override
    public Page<GuestGridDTO> getGrid(String id, String fullName, int page) {
        var pagination = PageRequest.of(page - 1, rowInPage, Sort.by("id"));
        var data = guestRepository.findByIdContainingAndFullNameContaining(id, fullName, pagination);
        var grid = data.map(x -> new GuestGridDTO(
                x.getId(),
                x.getFullName(),
                x.getBirthDate(),
                DropdownDTO.getGender().stream().filter(z -> z.getValue().equals(x.getGender())).findFirst().get().getText(),
                x.getCitizenship()
        ));
        if (grid.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "No data found");
        }
        return grid;
    }

    @Override
    public InsertGuestDto getUpdate(String name) {
        var entity = guestRepository.findById(name).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Guest not found"));
        var dto = new InsertGuestDto(
                entity.getId(),
                entity.getFirstName(),
                entity.getMiddleName(),
                entity.getLastName(),
                entity.getBirthDate(),
                entity.getGender(),
                entity.getCitizenship()
        );
        return dto;
    }

    @Override
    public GuestGridDTO saveOne(InsertGuestDto dto) {
        var entity = new Guest(
                dto.getId(),
                dto.getFirstName(),
                dto.getMiddleName(),
                dto.getLastName(),
                dto.getBirthDate(),
                dto.getGender(),
                dto.getCitizenship()
        );
        var data = guestRepository.save(entity);
        var result = new GuestGridDTO(
                data.getId(),
                data.getFirstName() + " " + data.getMiddleName() + " " + data.getLastName(),
                data.getBirthDate(),
                data.getGender(),
                data.getCitizenship()
        );
        return result;
    }

    @Override
    public GuestGridDTO updateOne(UpdateGuestDto dto) {
        var entity = guestRepository.findById(dto.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Guest not found"));
        entity.setFirstName(dto.getFirstName());
        entity.setMiddleName(dto.getMiddleName());
        entity.setLastName(dto.getLastName());
        entity.setBirthDate(dto.getBirthDate());
        entity.setGender(dto.getGender());
        entity.setCitizenship(dto.getCitizenship());
        var data = guestRepository.save(entity);
        var result = new GuestGridDTO(
                data.getId(),
                data.getFirstName() + " " + data.getMiddleName() + " " + data.getLastName(),
                data.getBirthDate(),
                data.getGender(),
                data.getCitizenship()
        );
        return result;
    }

    @Override
    public Boolean deleteOne(String name) {
        var entity = guestRepository.findById(name).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Guest not found"));
        guestRepository.delete(entity);
        return true;
    }

    @Override
    public Boolean checkExistingGuest(String id) {
        var check = guestRepository.existsById(id);
        return check;
    }

    @Override
    public Boolean notSpace(String input) {
        Pattern pattern = Pattern.compile("\\s");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return false;
        }
        return true;
    }
}
