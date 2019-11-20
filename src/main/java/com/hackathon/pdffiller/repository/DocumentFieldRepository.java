package com.hackathon.pdffiller.repository;

import com.hackathon.pdffiller.model.Application;
import com.hackathon.pdffiller.model.DocumentField;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.print.Doc;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DocumentFieldRepository extends JpaRepository <DocumentField, Long> {

    Set<DocumentField> findByApplicationId(Long id);
}
