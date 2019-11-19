package com.hackathon.pdffiller.repository;

import com.hackathon.pdffiller.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository <Agent, Long> {
}
