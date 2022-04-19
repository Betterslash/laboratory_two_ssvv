package integration;

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
import validation.ValidationException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
    public void addStudent_validStudent_isAdded() {
        Student student = new Student("123", "test", 100, "test@test.com");
        assertNull(service.addStudent(student));
    }

    @Test
    public void addAssignment_validAssignment_isAdded() {
        assertNull(service.addTema(new Tema("123", "test", 1, 1)));
    }

    @Test
    public void addGrade_invalidGrade_throwsValidationException() {
        assertThrows(ValidationException.class, () -> service.addNota(
                new Nota("1", "", "", 10.0, LocalDate.now()), "test")
        );
    }

    @Test
    public void testAll() {
        service.addStudent(new Student("123", "test", 100, "test@test.com"));
        service.addTema(new Tema("123", "test", 1, 1));
        assertThrows(ValidationException.class, () -> service.addNota(
                new Nota("1", "123", "123", 10.0, LocalDate.now()), "test"));
    }
}