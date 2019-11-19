package com.hackathon.pdffiller.repository;

import com.hackathon.pdffiller.model.Agent;
import com.hackathon.pdffiller.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository <Application, Long> {
    Optional<Application> findByName(String documentName);
}
