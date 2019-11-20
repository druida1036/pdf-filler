package com.hackathon.pdffiller.controller;

import com.hackathon.pdffiller.model.Application;
import com.hackathon.pdffiller.model.DocumentField;
import com.hackathon.pdffiller.repository.DocumentFieldRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("documentFields")
public class DocumentFieldController {

    private DocumentFieldRepository documentFieldRepository;

    public DocumentFieldController(DocumentFieldRepository documentFieldRepository) {
        this.documentFieldRepository = documentFieldRepository;
    }



    @GetMapping("/application/{applicationId}")
    public Set<DocumentField> query(@PathVariable Long applicationId) {
        documentFieldRepository.findByApplicationId(applicationId);
        return documentFieldRepository.findByApplicationId(applicationId);
    }

//    @PutMapping("/application/{applicationId}")
//    public Application update(@PathVariable Long applicationId,
//                              @Valid @RequestBody Application applicationDetails) {
//        Application application = applicationService.query(id);
//        application.setFields(applicationDetails.getFields());
//        return applicationService.save( application);
//    }

}
