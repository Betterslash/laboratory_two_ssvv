package integration.takeHome;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import utils.FileClearer;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class IntegrationTest {
    private Service service;

    @BeforeEach
    void setUp() {
        String filenameStudent = "src/test/resources/Studenti.xml";
        String filenameTema = "src/test/resources/Teme.xml";
        String filenameNota = "src/test/resources/Note.xml";

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);

        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);

        FileClearer.clearFiles();
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @AfterEach
    void tearDown() {
        FileClearer.clearFiles();
    }

    @Test
    public void addStudent_integration() {
        addStudent();
    }

    @Test
    public void addAssignment_integration() {
        addStudent();
        addAssignment();
    }

    @Test
    public void addGrade_integration() {
        addStudent();
        addAssignment();
        addGrade();
    }

    public void addStudent() {
        Student student = new Student("123", "name", 100, "some@email.com");
        assertNull(service.addStudent(student));
    }

    public void addAssignment() {
        assertNull(service.addTema(new Tema("123", "abc", 1, 1)));
    }

    public void addGrade() {
        service.addNota(new Nota("1","123","123",10.0, LocalDate.of(2022, 5, 3)),"asd");
        assertEquals(10.0, service.addNota(new Nota("1","123","123",10.0, LocalDate.of(2022, 5, 3)),"asd"));
    }

}
