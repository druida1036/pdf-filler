package com.hackathon.pdffiller.service;

import com.hackathon.pdffiller.model.Agent;
import com.hackathon.pdffiller.model.Application;
import com.hackathon.pdffiller.model.DocumentField;
import com.hackathon.pdffiller.repository.AgentRepository;
import com.hackathon.pdffiller.repository.ApplicationRepository;
import com.hackathon.pdffiller.repository.DocumentFieldRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDNonTerminalField;
import org.apache.pdfbox.pdmodel.interactive.form.PDRadioButton;
import org.apache.pdfbox.pdmodel.interactive.form.PDSignatureField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class ApplicationService {

    private ApplicationRepository applicationRepository;
    private DocumentFieldRepository documentFieldRepository;
    private AgentRepository agentRepository;

    public ApplicationService(ApplicationRepository applicationRepository,
                              DocumentFieldRepository documentFieldRepository,
                              AgentRepository agentRepository) {
        this.applicationRepository = applicationRepository;
        this.documentFieldRepository = documentFieldRepository;
        this.agentRepository = agentRepository;
    }

    public Application getDocument(Long id) {
        Application application = query(id);
        application.setFields(documentFieldRepository.findByApplicationId(application.getId()));
        String formTemplate = "src/main/resources/templates/form.pdf";
        File formDocument = new File(formTemplate);
        try (PDDocument pdfDocument = PDDocument.load(formDocument)) {
            pdfDocument.setAllSecurityToBeRemoved(true);
            // get the document catalog
            PDAcroForm acroForm = pdfDocument.getDocumentCatalog().getAcroForm();


            for (DocumentField field : application.getFields()) {

                PDField formField = acroForm.getField(field.getPath());
                if (formField instanceof PDTextField) {
                    formField.setValue(field.getValue());
                } else if (formField instanceof PDCheckBox) {
                    ((PDCheckBox) formField).check();
                } else if (formField instanceof PDRadioButton) {
                    formField.setValue(((PDRadioButton) formField).getOnValues().toArray()[0].toString());
                }
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            pdfDocument.save(out);
            application.setData(out.toByteArray());
            return application;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return application;
    }

    public Application createDocument(Agent agent, File source) throws IOException {
        try (PDDocument pdfDocument = PDDocument.load(source)) {
            pdfDocument.setAllSecurityToBeRemoved(true);
            // get the application catalog
            PDAcroForm acroForm = pdfDocument.getDocumentCatalog().getAcroForm();
            Optional<Application> result = applicationRepository.findByName(source.getName());
            Application application;
            Queue<DocumentField> documentFields = new ConcurrentLinkedQueue<>();
            if (result.isPresent()) {
                application = result.get();
                documentFields = new ConcurrentLinkedQueue<>(application.getFields());
            } else {
                application = Application.builder()
                        .applicantName("Juan Perez")
                        .name("document.pdf")
                        .type("application/pdf")
                        .data(Files.readAllBytes(Paths.get(source.toURI())))
                        .build();
            }

            List<PDField> fields = acroForm.getFields();
            for (PDField field : fields) {
                list(field, documentFields, application);
            }
            application.setFields(new HashSet<>(documentFields));

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            pdfDocument.save(outputStream);
            application.setData(outputStream.toByteArray());
            agent.addApplications(application);
            application.setAgent(agent);

            agentRepository.save(agent);
            return application;
        }
    }

    public List<Application> query() {
        return applicationRepository.findAll();
    }

    public Application query(Long id) {
        return applicationRepository.findById(id).map(r -> r)
                .orElseThrow(() -> new RuntimeException("Application id " + id + " not found"));
    }

    public Application save(Long agentId, Application application) throws IOException {
        Agent agent = agentRepository.findById(agentId).map(r -> r)
                .orElseThrow(() -> new RuntimeException("Agent id " + agentId + " not found"));
        String formTemplate = "src/main/resources/templates/form.pdf";
        File formDocument = new File(formTemplate);
        return createDocument(agent, formDocument);
    }

    public Application save(Application application) {
        return applicationRepository.save(application);
    }

    public List<Application> findByAgentId(Long id) {
        return applicationRepository.findByAgentId(id);
    }

    private void list(PDField field, Queue<DocumentField> fieldList, Application document) throws IOException {

        if (field instanceof PDNonTerminalField) {
            PDNonTerminalField nonTerminalField = (PDNonTerminalField) field;
            for (PDField child : nonTerminalField.getChildren()) {
                list(child, fieldList, document);
            }
        } else {
            DocumentField documentField;
            if (field instanceof PDTextField) {
                field.setValue("Default Test");
                documentField = getDocumentField(document, field, "Default Test", field.getAlternateFieldName(), "Default Test");
            } else if (field instanceof PDCheckBox) {
                documentField = getDocumentField(document, field, "1", field.getAlternateFieldName(), "1");
                ((PDCheckBox) field).check();
            } else if (field instanceof PDRadioButton) {
                Object[] values = ((PDRadioButton) field).getOnValues().toArray();
                documentField = getDocumentField(document, field,
                        values[0].toString(),
                        field.getAlternateFieldName(), Arrays.toString(values));
                field.setValue(values[0].toString());
            } else if (field instanceof PDSignatureField) {
                documentField = getDocumentField(document, field, "1", field.getAlternateFieldName(), "1");
            } else {
                documentField = getDocumentField(document, field, "1", field.getAlternateFieldName(), "1");
            }

            if (!fieldList.contains(documentField)) {
                fieldList.add(documentField);
            }

        }
    }

    private DocumentField getDocumentField(Application document, PDField field, String defaultValue, String alternateFieldName, String referenceValue) {
        return DocumentField.builder()
                .application(document)
                .name(field.getPartialName())
                .type(field.getClass().getCanonicalName())
                .path(field.getFullyQualifiedName())
                .nameDocument(alternateFieldName)
                .value(defaultValue)
                .referenceValue(referenceValue)
                .build();
    }
}


