package com.RestAp.demo.rest;

import com.RestAp.demo.entity.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {

    private List<Student> theStudent;

    @PostConstruct
    public void loadData(){
        theStudent=new ArrayList<>();
        theStudent.add(new Student("Buğrahan","ERTAŞ"));
        theStudent.add(new Student("Ayhan","İncekara"));
        theStudent.add(new Student("Çiğdem","Ertaş"));
    }


    @GetMapping("/students")
    public List<Student> getStudents(){


        return theStudent;

    }

    @GetMapping("/students/{studentId}")
    public Student getStudent(@PathVariable int studentId){

        // JUST İNDEX İNTO THE LİST... KEEP İT SİMPLE FOR NOW


        // CHECH THE STUDENT İD AGAİN LİST SİZE
        if((studentId>=theStudent.size()) || (studentId<0)){
            throw new StudentNotFoundException("STUDENT İD NOT FOUND (BGR)-"+studentId);
        }
        return theStudent.get(studentId);
    }


    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(Exception exc){

        // CREATE A StudentErrorResponse
        StudentErrorResponse error = new StudentErrorResponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
        //return new ResponseEntity<StudentErrorResponse>(error, HttpStatus.NOT_FOUND);

    }

}
