package com.app.service.implementation;

import com.app.dto.RealEstateDTO;
import com.app.model.RealEstate;
import com.app.repository.RealEstateRepository;
import com.app.service.IRealEstateService;
import com.app.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RealEstateService implements IRealEstateService {

    private final RealEstateRepository realEstateRepository;

    @Autowired
    public RealEstateService(RealEstateRepository realEstateRepository) {
        this.realEstateRepository = realEstateRepository;
    }

    @Override
    public List<RealEstate> findAll() {
        return this.realEstateRepository.findAll();
    }

    @Override
    public RealEstate findById(Integer id) {
        return this.realEstateRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Integer id) {
        RealEstate realEstate = this.findById(id);
        realEstate.setDeleted(true);
        this.save(realEstate);
    }

    @Override
    public RealEstate save(RealEstate realEstate) {
        return this.realEstateRepository.save(realEstate);
    }

    @Override
    public RealEstate createRealEstate(RealEstateDTO realEstateDTO) {
        RealEstate realEstate = new RealEstate(realEstateDTO.getName(), false);
        return this.save(realEstate);
    }
}
