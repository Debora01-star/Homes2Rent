package com.Homes2Rent.Homes2Rent.service;
import com.Homes2Rent.Homes2Rent.dto.HomeDto;
import com.Homes2Rent.Homes2Rent.exceptions.RecordNotFoundException;
import com.Homes2Rent.Homes2Rent.model.Home;
import com.Homes2Rent.Homes2Rent.repository.HomeRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Service
public class HomeService {


        private HomeRepository homeRepository;

        public HomeService(HomeRepository homeRepository)

        {
            this.homeRepository = homeRepository;

        }

    public List<HomeDto> getAllHome() {
        List<Home> homeList = homeRepository.findAll();
        List<HomeDto> dtos = new ArrayList<>();
        for (Home home : homeList) {
            dtos.add(transferToDto(home));
        }
        return dtos;
    }

    public HomeDto getHome(Long id) {
        Optional<Home> home = homeRepository.findById(id);
        if(home.isPresent()) {
            HomeDto dto = transferToDto(home.get());
            return dto;
        } else {
            throw new RecordNotFoundException("No home found");
        }
    }

    public HomeDto addHome(HomeDto homeDto) {
        Home home = transferToHome(homeDto);
        homeRepository.save(home);
        return transferToDto(home);
    }


    public void deleteHome(Long id) {
        homeRepository.deleteById(id);
    }

    public void updateHome(Long id, HomeDto homeDto) {
        if(!homeRepository.existsById(id)) {
            throw new RecordNotFoundException("No home found");
        }
        Home home = homeRepository.findById(id).orElse(null);

        home.setId(home.getId());
        home.setRented(home.getRented());
        home.setName(home.getName());
        home.setPrice(home.getPrice());
        homeRepository.save(home);

    }

    public HomeDto transferToDto(Home home){
        var dto = new HomeDto();

        dto.setId(home.getId());
        dto.setType(home.getType());
        dto.setName(home.getName());
        dto.setRented(home.getRented());
        dto.setPrice(home.getPrice());

        return dto;
    }

    public Home transferToHome(HomeDto homeDto){
        Home home = new Home();

        home.setId(homeDto.getId());
        home.setType(homeDto.getType());
        home.setName(homeDto.getName());
        home.setRented(homeDto.getRented());
        home.setPrice(homeDto.getPrice());

        return home;
    }



}

