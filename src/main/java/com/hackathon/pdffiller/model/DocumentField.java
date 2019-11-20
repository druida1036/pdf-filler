package com.hackathon.pdffiller.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class DocumentField {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Column( name = "name_document")
    @Lob
    private String nameDocument;
    private String type;
    private String path;
    private String value;
    @Lob
    @Column(name = "reference_value")
    private String referenceValue;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Application application;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentField that = (DocumentField) o;
        return path.equals(that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }
}
