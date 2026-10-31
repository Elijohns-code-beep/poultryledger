package com.poultryledger.controller;

import com.poultryledger.entity.*;
import com.poultryledger.repository.ChickGroupRepository;
import com.poultryledger.repository.HenTypeRepository;
import com.poultryledger.service.ChickService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.time.LocalDate;

@Controller
@RequestMapping("/chicks")
public class ChickController {
    private final ChickGroupRepository groupRepository; private final HenTypeRepository henTypeRepository; private final ChickService service;
    public ChickController(ChickGroupRepository groupRepository, HenTypeRepository henTypeRepository, ChickService service) { this.groupRepository=groupRepository; this.henTypeRepository=henTypeRepository; this.service=service; }
    @GetMapping public String page(Model model) { var groups=service.groups(); model.addAttribute("groups",groups); model.addAttribute("openCount",groups.stream().filter(g -> g.getStatus()==GroupStatus.OPEN).count()); model.addAttribute("groupForm",new ChickGroup()); model.addAttribute("eventForm",new ChickEvent()); model.addAttribute("henTypes",henTypeRepository.findAll()); model.addAttribute("today",LocalDate.now()); return "chicks/page"; }
    @PostMapping("/groups") public String saveGroup(@Valid @ModelAttribute("groupForm") ChickGroup form, BindingResult result, Model model) { if(result.hasErrors()) { model.addAttribute("formError","Check the group code and starting count."); return page(model); } try { service.saveGroup(form); return "redirect:/chicks"; } catch(IllegalArgumentException ex) { model.addAttribute("formError",ex.getMessage()); return page(model); } }
    @PostMapping("/{id}/events") public String addEvent(@PathVariable Long id, @Valid @ModelAttribute("eventForm") ChickEvent form, BindingResult result, Model model) { if(result.hasErrors()) { model.addAttribute("formError","Enter a date and a whole-number change."); return page(model); } try { service.addEvent(groupRepository.findByIdWithEvents(id).orElseThrow(),form); return "redirect:/chicks"; } catch(IllegalArgumentException ex) { model.addAttribute("formError",ex.getMessage()); return page(model); } }
    @PostMapping("/{id}/graduate") public String graduate(@PathVariable Long id, @RequestParam Long henTypeId, @RequestParam LocalDate date, Model model) { try { service.graduate(groupRepository.findByIdWithEvents(id).orElseThrow(),henTypeId,date); return "redirect:/chicks"; } catch(IllegalArgumentException ex) { model.addAttribute("formError",ex.getMessage()); return page(model); } }
}
