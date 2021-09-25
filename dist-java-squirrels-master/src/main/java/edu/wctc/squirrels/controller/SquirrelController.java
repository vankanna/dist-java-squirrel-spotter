package edu.wctc.squirrels.controller;

import edu.wctc.squirrels.entity.Sighting;
import edu.wctc.squirrels.entity.Squirrel;
import edu.wctc.squirrels.service.LocationService;
import edu.wctc.squirrels.service.SightingService;
import edu.wctc.squirrels.service.SquirrelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class SquirrelController {
    private SquirrelService squirrelService;
    private SightingService sightingService;

    @Autowired
    public SquirrelController(SquirrelService sqs, SightingService sis, LocationService los) {
        this.squirrelService = sqs;
        this.sightingService = sis;
    }


    @PostMapping("/delete-squirrel/{squirrel_id}")
    public String deleteSquirrel(
            Model model,@PathVariable int squirrel_id,
            @Valid @ModelAttribute Squirrel squirrel,
            BindingResult bindingResult) {
        Squirrel theSquirrel = squirrelService.getSquirrel(squirrel_id);
        String name = theSquirrel.getCommonName();

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            model.addAttribute("pageTitle", "Pick a Squirrel");
            model.addAttribute("squirrelList", squirrelService.getSquirrelList());

            return "squirrel-list";
        }
        sightingService.deleteAllSightings(sightingService.getSightingsForSquirrel(squirrel_id));
        squirrelService.deleteSquirrel(squirrel_id);
        model.addAttribute("pageTitle", "Thank You!");
        model.addAttribute("name", name);

        return "delete-confirmation";
    }

}
