package com.lxp.model;

import com.lxp.model.dto.CourseDTO;
import com.lxp.model.dto.SectionDTO;
import java.util.List;

public class CreateCourseRequest {
    private CourseDTO course;
    private List<SectionDTO> sections;

    public CreateCourseRequest(CourseDTO course, List<SectionDTO> sections) {
        this.course = course;
        this.sections = sections;
    }

    public CreateCourseRequest() {
    }

    public void setCourse(CourseDTO course) {
        this.course = course;
    }

    public void setSections(List<SectionDTO> sections) {
        this.sections = sections;
    }

    public CourseDTO getCourse() {
        return course;
    }

    public List<SectionDTO> getSections() {
        return sections;
    }

    @Override
    public String toString() {
        return "CreateCourseRequest{" + "course=" + course + ", sections=" + sections + '}';
    }
}
