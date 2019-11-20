package com.hackathon.pdffiller.controller;

import com.hackathon.pdffiller.model.Application;
import com.hackathon.pdffiller.repository.DocumentFieldRepository;
import com.hackathon.pdffiller.service.ApplicationService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("applications")
public class ApplicationController {

    private ApplicationService applicationService;
    private DocumentFieldRepository documentFieldRepository;

    public ApplicationController(ApplicationService applicationService, DocumentFieldRepository documentFieldRepository) {
        this.applicationService = applicationService;
        this.documentFieldRepository = documentFieldRepository;
    }

    @GetMapping()
    public List<Application> query() {
        return applicationService.query();
    }

    @GetMapping("/agent/{id}")
    public List<Application> queryByAgentId(@PathVariable Long id) {
        return applicationService.findByAgentId(id);
    }

    @GetMapping("/{id}")
    public Application query(@PathVariable Long id) {
        return applicationService.query(id);
    }

    @GetMapping("/{id}/fields")
    public Application queryWithFields(@PathVariable Long id) {
        Application application = applicationService.query(id);
        application.setFields(documentFieldRepository.findByApplicationId(id));
        return application;
    }

    @PostMapping("/agent/{agentId}")
    public Application create(@PathVariable() Long agentId, @Valid @RequestBody Application application) throws IOException {
        application.setId(null);
        return applicationService.save(agentId, application);
    }

    @PutMapping("/{id}")
    public Application update(@PathVariable(value = "id") Long id,
                           @Valid @RequestBody Application applicationDetails) {
        Application application = applicationService.query(id);
        application.setFields(applicationDetails.getFields());
        return applicationService.save( application);
    }

    @GetMapping("/{id}/downloadDocument")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long id,
                                                 HttpServletRequest request) throws IOException {
        Application application = applicationService.getDocument(id);
        Resource resource = new ByteArrayResource(application.getData());
        return (ResponseEntity<Resource>) ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(application.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + application.getName() + "\"")
                .body(resource);
    }
}
