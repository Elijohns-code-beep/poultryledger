package com.poultryledger.controller;

import com.poultryledger.entity.HenType;
import com.poultryledger.entity.PopulationEvent;
import com.poultryledger.repository.HenTypeRepository;
import com.poultryledger.service.PopulationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/hens")
public class HenController {
    private final HenTypeRepository henTypeRepository; private final PopulationService service;
    public HenController(HenTypeRepository henTypeRepository, PopulationService service) { this.henTypeRepository=henTypeRepository; this.service=service; }
    @GetMapping public String page(Model model) { List<HenType> types=henTypeRepository.findAll(); model.addAttribute("types",types); model.addAttribute("rows",types.stream().map(t -> new PopulationRow(t,service.currentPopulation(t))).toList()); model.addAttribute("events",service.events()); model.addAttribute("eventForm",new PopulationEvent()); model.addAttribute("today",LocalDate.now()); return "hens/page"; }
    @PostMapping("/events") public String save(@Valid @ModelAttribute("eventForm") PopulationEvent form, BindingResult result, Model model) { if(result.hasErrors()) { model.addAttribute("formError","Enter a date, hen type, and whole-number change."); return page(model); } try { service.saveEvent(form); return "redirect:/hens"; } catch(IllegalArgumentException ex) { model.addAttribute("formError",ex.getMessage()); return page(model); } }
    public record PopulationRow(HenType type, int current) {}
}
