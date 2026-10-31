package com.poultryledger.controller;

import com.poultryledger.entity.Sitting;
import com.poultryledger.repository.SittingRepository;
import com.poultryledger.service.SittingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.time.LocalDate;

@Controller
@RequestMapping("/sittings")
public class SittingController {
    private final SittingRepository repository; private final SittingService service;
    public SittingController(SittingRepository repository, SittingService service) { this.repository=repository; this.service=service; }
    @GetMapping public String list(Model model) { model.addAttribute("sittings",repository.findAllByOrderByDateGivenDesc()); return "sittings/list"; }
    @GetMapping("/new") public String create(Model model) { Sitting s=new Sitting(); s.setDateGiven(LocalDate.now()); s.setExpectedHatchDate(LocalDate.now().plusDays(21)); model.addAttribute("form",s); return "sittings/form"; }
    @GetMapping("/{id}/edit") public String edit(@PathVariable Long id, Model model) { model.addAttribute("form",repository.findById(id).orElseThrow()); return "sittings/form"; }
    @PostMapping public String save(@Valid @ModelAttribute("form") Sitting form, BindingResult result, Model model) { if(result.hasErrors()) return "sittings/form"; try { service.save(form); return "redirect:/sittings"; } catch(IllegalArgumentException ex) { model.addAttribute("formError",ex.getMessage()); return "sittings/form"; } }
}
