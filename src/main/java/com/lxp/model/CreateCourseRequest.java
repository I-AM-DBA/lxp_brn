package com.lxp.model;

import com.lxp.model.dto.CourseInsertDTO;
import com.lxp.model.dto.SectionInsertDTO;
import java.util.List;

public class CreateCourseRequest {
    private CourseInsertDTO course;
    private List<SectionInsertDTO> sections;

    public CreateCourseRequest(CourseInsertDTO course, List<SectionInsertDTO> sections) {
        this.course = course;
        this.sections = sections;
    }

    public CreateCourseRequest() {
    }

    public void setCourse(CourseInsertDTO course) {
        this.course = course;
    }

    public void setSections(List<SectionInsertDTO> sections) {
        this.sections = sections;
    }

    public CourseInsertDTO getCourse() {
        return course;
    }

    public List<SectionInsertDTO> getSections() {
        return sections;
    }

    @Override
    public String toString() {
        return "CreateCourseRequest{" + "course=" + course + ", sections=" + sections + '}';
    }
}
