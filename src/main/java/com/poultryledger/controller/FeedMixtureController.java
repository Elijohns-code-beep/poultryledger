package com.poultryledger.controller;

import com.poultryledger.entity.FeedIngredient;
import com.poultryledger.entity.FeedMixture;
import com.poultryledger.repository.FeedMixtureRepository;
import com.poultryledger.service.FeedMixtureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
@RequestMapping("/feed-mixtures")
public class FeedMixtureController {
    private final FeedMixtureRepository repository; private final FeedMixtureService service;
    public FeedMixtureController(FeedMixtureRepository repository, FeedMixtureService service) { this.repository=repository; this.service=service; }
    @GetMapping public String list(Model model) { model.addAttribute("mixtures",repository.findAllWithIngredients()); model.addAttribute("service",service); return "feed-mixtures/list"; }
    @GetMapping("/new") public String create(Model model) { FeedMixture f=new FeedMixture(); f.setDate(LocalDate.now()); f.addIngredient(new FeedIngredient()); model.addAttribute("form",f); model.addAttribute("service",service); return "feed-mixtures/form"; }
    @GetMapping("/{id}/edit") public String edit(@PathVariable Long id, Model model) { FeedMixture f=repository.findByIdWithIngredients(id).orElseThrow(); if(f.getIngredients().isEmpty()) f.addIngredient(new FeedIngredient()); model.addAttribute("form",f); model.addAttribute("service",service); return "feed-mixtures/form"; }
    @PostMapping public String save(@Valid @ModelAttribute("form") FeedMixture form, BindingResult result, Model model) { if(result.hasErrors()) { model.addAttribute("service",service); return "feed-mixtures/form"; } try { service.save(form); return "redirect:/feed-mixtures"; } catch(IllegalArgumentException ex) { model.addAttribute("formError",ex.getMessage()); model.addAttribute("service",service); return "feed-mixtures/form"; } }
}
