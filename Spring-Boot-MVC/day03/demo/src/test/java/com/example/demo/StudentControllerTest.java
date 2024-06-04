package com.example.demo;

import com.example.demo.controller.StudentController;
import com.example.demo.domain.Student;
import com.example.demo.exception.StudentNotFoundException;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.StudentRepositoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
@ContextConfiguration(classes = {StudentRepository.class})
public class StudentControllerTest {
    private MockMvc mockMvc;
    private StudentRepository studentRepository;
    private MockHttpSession session;

    @BeforeEach
    void setUp() throws Exception {
        studentRepository = mock(StudentRepositoryImpl.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new StudentController(studentRepository)).build();
        when(studentRepository.matches("1", "1")).thenReturn(true);

        session = new MockHttpSession();


    }

    @Test
    void testGetStudents() throws Exception {
        List<Student> students = List.of(new Student[]{
                new Student("1", "1", "Student1", "student1@example.com", 0, "None"),
                new Student("tom", "1234", "Tom", "tom@naver.com", 100, "Excellent"),
                new Student("jake", "567fgse", "Jake", "jake@naver.com", 82, "Good"),
                new Student("ethan", "sdf3@#4", "Ethan", "ethan@naver.com", 20, "Bad")
        });

        String studentId = "tom";
        String password = "1234";
        when(studentRepository.matches(studentId, password)).thenReturn(true);
        when(studentRepository.getStudent(studentId)).thenReturn(new Student(studentId, "1234"));

        mockMvc.perform(post("/login")
                        .param("id", studentId)
                        .param("password", password)
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(redirectedUrl("/student"))
                .andExpect(cookie().value("SESSION", session.getId()));

        when(studentRepository.getStudents()).thenReturn(students);


        mockMvc.perform(get("students"))
                .andExpect(status().isOk())
                .andExpect(view().name("students"))
                .andExpect(model().attributeExists("students"))
                .andExpect(model().attribute("students", students));
    }

    @Test
    void testStudentExists() throws Exception {
        Student student = Student.create("tom", "1234");
        when(studentRepository.getStudent(anyString())).thenReturn(student);

        MvcResult mvcResult = mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(view().name("students"))
                .andReturn();

        Optional<Student> viewStudent = Optional.ofNullable(mvcResult.getModelAndView())
                .map(ModelAndView::getModel)
                .map(m -> m.get("students"))
                .map(Student.class::cast);

        assertThat(viewStudent).isPresent();
        assertThat(viewStudent.get().getId()).isEqualTo(student.getId());
    }

    @Test
    void testStudentNotExists() throws Exception {
        mockMvc.perform(get("/students")
                        .session(session)
                        .cookie(new Cookie("SESSION", session.getId())))
                .andExpect(status().isOk())
                .andExpect(view().name("students"));
    }

}
