package service;

import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import utils.FileClearer;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTest {

    private Service service;

    void setUp(){
        FileClearer.clearFiles();
        String filenameStudent = "src/test/resources/Studenti.xml";
        String filenameTema = "src/test/resources/Teme.xml";
        String filenameNota = "src/test/resources/Note.xml";

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        this.service = new Service(studentXMLRepository, new StudentValidator(), temaXMLRepository, new TemaValidator(), notaXMLRepository, notaValidator);

    }

    ServiceTest() {
        setUp();
    }


    @Test
    public void test_addStudent_withNull() {
        setUp();
        var exception = assertThrows(RuntimeException.class, () -> service.addStudent(null));

        var expectedMessage = "Cannot invoke \"domain.Student.getID()\" because \"entity\" is null";
        var actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        FileClearer.clearFiles();
    }

    @Test
    public void test_addStudent_withValidInput() {
        setUp();
        var studentId = String.valueOf(Math.random() % 1000);
        var student = new Student(studentId, "Dori", 933, "daie2710@scs.ubbcluj.ro");
        var addedStudent = service.addStudent(student);
        assertNull(addedStudent);

        var retrievedStudent = service.findStudent(studentId);
        assertEquals(retrievedStudent.getID(), student.getID());
        assertEquals(retrievedStudent.getNume(), student.getNume());
        assertEquals(retrievedStudent.getGrupa(), student.getGrupa());
        assertEquals(retrievedStudent.getEmail(), student.getEmail());
        FileClearer.clearFiles();
    }

    @Test
    void addStudent_validStudentId_isAdded() {
        setUp();
        Student student = new Student("1", "nume", 10, "email@domeniu.com");
        assertNull(service.addStudent(student));
        FileClearer.clearFiles();
    }

    @Test
    void addStudent_validStudentName_isAdded() {
        setUp();
        Student student = new Student("1", "nume", 10, "email@domeniu.com");
        assertNull(service.addStudent(student));
        FileClearer.clearFiles();
    }

    @Test
    void addStudent_validStudentGroup_isAdded() {
        setUp();
        Student student = new Student("1", "nume", 10, "email@domeniu.com");
        assertNull(service.addStudent(student));
        FileClearer.clearFiles();
    }

    @Test
    void addStudent_validStudentEmail_isAdded() {
        setUp();
        Student student = new Student("1", "nume", 10, "email@domeniu.com");
        assertNull(service.addStudent(student));
        FileClearer.clearFiles();
    }

    @Test
    void addStudent_invalidStudentId_notAdded() {
        setUp();
        Student student = new Student("", "nume", 10, "email@domeniu.com");
        assertThrows(ValidationException.class, () -> service.addStudent(student));
        FileClearer.clearFiles();
    }

    @Test
    void addStudent_invalidStudentName_notAdded() {
        setUp();
        Student student = new Student("1", "", 10, "email@domeniu.com");
        assertThrows(ValidationException.class, () -> service.addStudent(student));
        FileClearer.clearFiles();
    }

    @Test
    void addStudent_invalidStudentGroup_notAdded() {
        setUp();
        Student student = new Student("1", "nume", -10, "email@domeniu.com");
        assertThrows(ValidationException.class, () -> service.addStudent(student));
        FileClearer.clearFiles();
    }

    @Test
    void addStudent_invalidStudentEmail_notAdded() {
        setUp();
        Student student = new Student("1", "nume", 10, "");
        assertThrows(ValidationException.class, () -> service.addStudent(student));
        FileClearer.clearFiles();
    }

    @Test
    void addStudent_groupNumberIsZero_isAdded() {
        setUp();
        Student student = new Student("1", "nume", 0, "email@domeniu.com");
        assertNull(service.addStudent(student));
        FileClearer.clearFiles();
    }

    @Test
    void addStudent_groupNumberIsMinusOne_notAdded() {
        setUp();
        Student student = new Student("1", "nume", -1, "email@domeniu.com");
        assertThrows(ValidationException.class, () -> service.addStudent(student));
        FileClearer.clearFiles();
    }


    @Test
    public void addAssignment_validAssignment() {
        setUp();
        Tema tema = new Tema("1", "descriere", 1, 1);
        assertNull(service.addTema(tema));
        FileClearer.clearFiles();
    }

    @Test
    public void addAssignment_invalidAssignment() {
        setUp();
        Tema tema = new Tema("", "", 1, 1);
        assertThrows(ValidationException.class, () -> service.addTema(tema));
        FileClearer.clearFiles();
    }
}