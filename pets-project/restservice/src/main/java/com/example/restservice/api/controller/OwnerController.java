package com.example.restservice.api.controller;

import com.example.restservice.api.model.Owner;
import com.example.restservice.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @GetMapping("/owner/{id}")
    public Owner getOwnerById(@PathVariable("id") int id) {
        return ownerService.getOwnerById(id);
    }

    @GetMapping("/owner")
    public List<Owner> getOwner(@RequestParam("name") String name) {
        return ownerService.getOwner(name);
    }

    @PostMapping("/owner")
    public Owner createOwner(@RequestBody() Owner owner){
       return ownerService.createOwner(owner);
    }

    @PutMapping("/owner/{id}")
    public Owner updateOwner(@PathVariable("id") int id, @RequestBody() Owner owner) {
        return ownerService.updateOwner(id, owner);
    }

    @DeleteMapping("/owner/{id}")
    public Owner deleteOwner(@PathVariable("id") int id) { return ownerService.deleteOwner(id);}

    @GetMapping("/owners")
    public List<Owner> getOwnersWithPets() { return ownerService.getOwnersWithPets();}

    @GetMapping("food/owner/{id}")
    public Owner getCostFoodOwner(@PathVariable("id") int id) { return ownerService.getAmountFoodPerOwner(id);}

}

