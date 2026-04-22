package com.lxp.courses.controller;

import com.lxp.courses.services.InsertCourseService;
import com.lxp.model.CreateCourseRequest;
import java.util.Map;

public class CreateCourseController {
    private InsertCourseService insertCourseService = new InsertCourseService();

    public void createCourse(CreateCourseRequest createCourseRequest) {
        if (createCourseRequest == null) {
            throw new IllegalArgumentException("Essential value for creating course is null");
        }

        try {
            Map<String, Object> result = insertCourseService.insertCourse(createCourseRequest);
            System.out.println("create Course Success. Course ID: " + result.get("courseId"));
            System.out.println("Full Result: " + result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
