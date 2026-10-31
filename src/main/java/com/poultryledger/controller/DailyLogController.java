package com.poultryledger.controller;

import com.poultryledger.entity.DailyLog;
import com.poultryledger.entity.FeedUsage;
import com.poultryledger.repository.DailyLogRepository;
import com.poultryledger.repository.FeedMixtureRepository;
import com.poultryledger.service.DailyLogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.math.BigDecimal;

@Controller
public class DailyLogController {
    private final DailyLogRepository repository; private final FeedMixtureRepository mixtureRepository; private final DailyLogService service;
    public DailyLogController(DailyLogRepository repository, FeedMixtureRepository mixtureRepository, DailyLogService service) { this.repository=repository; this.mixtureRepository=mixtureRepository; this.service=service; }
    @GetMapping({"/","/daily-log"})
    public String page(@RequestParam(required=false) LocalDate date, Model model) { DailyLog form=repository.findByDateWithFeedUsages(date==null?LocalDate.now():date).orElseGet(DailyLog::new); if(form.getDate()==null) form.setDate(date==null?LocalDate.now():date); if(form.getFeedUsages().isEmpty()) form.getFeedUsages().add(new FeedUsage()); model.addAttribute("form",form); model.addAttribute("logs",service.list()); model.addAttribute("mixtures",mixtureRepository.findAllWithIngredients()); model.addAttribute("totalEggs",service.totalEggs(form)); model.addAttribute("totalFeedCost",service.totalFeedCost(form)); return "daily-log/page"; }
    @PostMapping("/daily-log")
    public String save(@Valid @ModelAttribute("form") DailyLog form, BindingResult result, Model model) { if(result.hasErrors()) { model.addAttribute("logs",service.list()); model.addAttribute("mixtures",mixtureRepository.findAllWithIngredients()); return "daily-log/page"; } try { service.save(form); return "redirect:/daily-log?date="+form.getDate(); } catch(IllegalArgumentException ex) { model.addAttribute("formError",ex.getMessage()); model.addAttribute("logs",service.list()); model.addAttribute("mixtures",mixtureRepository.findAllWithIngredients()); return "daily-log/page"; } }
}
