# Pdf Filler 

# Getting Started

## Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.1.RELEASE/maven-plugin/)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.2.1.RELEASE/reference/htmlsingle/#using-boot-devtools)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/docs/2.2.1.RELEASE/reference/htmlsingle/#configuration-metadata-annotation-processor)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.2.1.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.2.1.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [PDF Box](https://pdfbox.apache.org/)

## Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)


## PDF Box 

The Apache PDFBox® library is an open source Java tool for working with PDF documents. 
This project allows creation of new PDF documents, 
manipulation of existing documents and the ability to extract content from documents. 
Apache PDFBox also includes several command line utilities. 
Apache PDFBox is published under the Apache License v2.0.

## Description

This is a REST API to handle the form document data, this API uses PDFBox library to manipulate the form and convert it to metadata and store to and in-memory database H2, with the metadata, we can fill the form with the information required.
For this demo has been developed different endpoints to handle the operations over the form/document.

* [PDF Filler API(Swagger)](http://localhost:8080/api/v1/swagger-ui.html)

This API allows to create and application, review and edit the information(fields on the form are listed each one), and download the document filled.

For demo proposes this API is preloaded with an application and it is filled with default values (All free text fields are filled with "Default Test")

## Principal Endpoints

### Query Application Document 

This Demo represent the form document as Application, an application is a combination 
of the Applicant info and the document(from). This endpoint query one single application.
For this demo use Agent ID -> 1 and Application ID -> in all endpoint to check the preloaded data.

* [Application Query](http://localhost:8080/api/v1/applications/1/fields)
![Alt text](extras/application_api.png?raw=true "Application")

### Create Application Document 

This endpoint allows to create a single application by default each application is created 
using form template ([here](extras/form.pdf)). 

* [Application Creation (POST)](http://localhost:8080/api/v1/applications/agent/1)
![Alt text](extras/application_api_create.png?raw=true "Application Creation")

### Update Application Document 

This endpoint allows to update the fields on the document you can uses 
[Application Query](http://localhost:8080/api/v1/applications/1/fields) 
to get a json ([here](extras/application.json)) and then update the value 
for each field (JSON field "value" and "referenceValue" is the set of values 
for that specific field document when it applies) as is shown below.

* [Application Update (PUT)](http://localhost:8080/api/v1/applications/)
![Alt text](extras/application_api_update.png?raw=true "Application")

![Alt text](extras/application_api_result.png?raw=true "Update Result")

### Download Application Document 

This endpoint allows to download the document form filled and example here 
[here](extras/document.pdf)). For this endpoint use postman in order to get
the file downloaded correctly.

* [Application Download (GET)](http://localhost:8080/api/v1/applications/1/downloadDocument)
![Alt text](extras/application_api_download.png?raw=true "Application")

![Alt text](extras/document.png?raw=true "Result")

## Execution

This is a Spring Boot application you need to  JDK 1.8 or above, Maven and git installed in your machine,
so first the must be compiled with below command: 

`mvn clean install`

Run the app. 

`mvn spring-boot:run`

## Extension


This app is the base API to fill document info and It can be integrated 
with external data sources, expand the internal database or create a UI 
to fill the information in a human-readable format.


