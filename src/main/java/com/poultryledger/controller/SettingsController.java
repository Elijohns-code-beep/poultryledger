package com.poultryledger.controller;

import com.poultryledger.entity.ChannelPrice;
import com.poultryledger.entity.HenType;
import com.poultryledger.repository.ChannelPriceRepository;
import com.poultryledger.repository.HenTypeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/settings")
public class SettingsController {
    private final HenTypeRepository henTypeRepository; private final ChannelPriceRepository priceRepository;
    public SettingsController(HenTypeRepository henTypeRepository, ChannelPriceRepository priceRepository) { this.henTypeRepository=henTypeRepository; this.priceRepository=priceRepository; }
    @GetMapping public String page(Model model) { model.addAttribute("types",henTypeRepository.findAll()); model.addAttribute("prices",priceRepository.findAll()); return "settings/page"; }
    @PostMapping("/populations") public String savePopulations(@RequestParam List<Long> ids, @RequestParam List<Integer> startingPopulations) { for(int i=0;i<ids.size();i++){ HenType t=henTypeRepository.findById(ids.get(i)).orElseThrow(); t.setStartingPopulation(Math.max(0,startingPopulations.get(i))); henTypeRepository.save(t); } return "redirect:/settings"; }
    @PostMapping("/prices") public String savePrices(@RequestParam List<Long> ids, @RequestParam List<java.math.BigDecimal> values) { for(int i=0;i<ids.size();i++){ ChannelPrice p=priceRepository.findById(ids.get(i)).orElseThrow(); p.setPrice(values.get(i)); priceRepository.save(p); } return "redirect:/settings"; }
}
