package com.app.service;

import com.app.dto.RealEstateDTO;
import com.app.model.RealEstate;
import com.app.model.User;

import java.util.List;

public interface IRealEstateService {

    List<RealEstate> findAll();
    RealEstate findById(Integer id);
    void delete(Integer id);
    RealEstate save(RealEstate realEstate);
    RealEstate createRealEstate(RealEstateDTO realEstateDTO);
}
