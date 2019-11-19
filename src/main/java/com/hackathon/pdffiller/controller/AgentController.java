package com.hackathon.pdffiller.controller;

import com.hackathon.pdffiller.model.Agent;
import com.hackathon.pdffiller.repository.AgentRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("agents")
public class AgentController {

    private AgentRepository agentRepository;

    public AgentController(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    @GetMapping()
    public List<Agent> query() {
        return agentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Agent query(@PathVariable Long id) {
        return agentRepository.findById(id).map( r -> r)
                .orElseThrow(() -> new RuntimeException("Agent id "+id+" not found"));
    }

    @PostMapping()
    public Agent create(@Valid @RequestBody Agent agent) {
        return agentRepository.save(agent);
    }
}
