package com.conexia.starwars.controller;

import com.conexia.starwars.domain.dto.PeopleDTO;
import com.conexia.starwars.domain.dto.VehicleDTO;
import com.conexia.starwars.domain.dto.pagination.PageResult;
import com.conexia.starwars.service.PeopleService;
import com.conexia.starwars.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping()
    public ResponseEntity<PageResult<VehicleDTO>> findAll(@RequestParam(required = false, defaultValue = "1") Integer page,
                                                          @RequestParam(required = false, defaultValue = "10") Integer size,
                                                          @RequestParam(required = false) Long id,
                                                          @RequestParam(required = false) String name) {
        return ResponseEntity.ok(vehicleService.findAll(page, size, id, name));
    }
}
