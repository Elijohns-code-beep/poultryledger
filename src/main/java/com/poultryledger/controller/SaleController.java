package com.poultryledger.controller;

import com.poultryledger.entity.Sale;
import com.poultryledger.repository.HenTypeRepository;
import com.poultryledger.repository.SaleRepository;
import com.poultryledger.service.SaleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.Map;

@Controller
@RequestMapping("/sales")
public class SaleController {
    private final SaleRepository repository; private final HenTypeRepository henTypeRepository; private final SaleService service;
    public SaleController(SaleRepository repository, HenTypeRepository henTypeRepository, SaleService service) { this.repository=repository; this.henTypeRepository=henTypeRepository; this.service=service; }
    @GetMapping public String list(Model model) { model.addAttribute("sales",service.list()); model.addAttribute("service",service); return "sales/list"; }
    private void defaults(Model model) { model.addAttribute("priceDefaults", Map.of("Shop",service.defaultPrice("Shop"), "Buyer",service.defaultPrice("Buyer"), "Home",service.defaultPrice("Home"))); }
    @GetMapping("/new") public String create(Model model) { Sale s=new Sale(); s.setDate(LocalDate.now()); s.setSoldTo("Shop"); s.setPricePerEgg(service.defaultPrice("Shop")); model.addAttribute("form",s); model.addAttribute("henTypes",henTypeRepository.findAll()); defaults(model); return "sales/form"; }
    @GetMapping("/{id}/edit") public String edit(@PathVariable Long id, Model model) { model.addAttribute("form",repository.findById(id).orElseThrow()); model.addAttribute("henTypes",henTypeRepository.findAll()); defaults(model); return "sales/form"; }
    @PostMapping public String save(@Valid @ModelAttribute("form") Sale form, BindingResult result, Model model) { if(result.hasErrors()) { model.addAttribute("henTypes",henTypeRepository.findAll()); defaults(model); return "sales/form"; } try { service.save(form); return "redirect:/sales"; } catch(IllegalArgumentException ex) { model.addAttribute("formError",ex.getMessage()); model.addAttribute("henTypes",henTypeRepository.findAll()); defaults(model); return "sales/form"; } }
}
