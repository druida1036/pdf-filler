package com.hackathon.pdffiller.config;

import com.hackathon.pdffiller.model.Agent;
import com.hackathon.pdffiller.model.Application;
import com.hackathon.pdffiller.repository.AgentRepository;
import com.hackathon.pdffiller.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class DataBaseInitializer implements CommandLineRunner {
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private ApplicationService applicationService;

    @Override
    public void run(String... args) throws Exception {
        Agent agent = Agent.builder().name("Agent 1").build();
        String formTemplate = "src/main/resources/templates/form.pdf";
        File formDocument = new File(formTemplate);

        Application application = Application.builder()
                .applicantName("Juan Perez")
                .name("document.pdf")
                .type("")
                .data(Files.readAllBytes(Paths.get(formTemplate)))
                .build();
        applicationService.createDocument(agent, formDocument, application);

//        Application application = Application.builder()
//                .name("Juan Perez")
//                .type("application/pdf")
//                .data(Files.readAllBytes(Paths.get(formDocument.toURI())))
//                .build();
//        agent.addApplications(application);
//        agentRepository.save(agent);
    }
}
