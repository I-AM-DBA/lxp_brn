package com.lxp.courses.controller;

import com.lxp.courses.services.SectionService;
import com.lxp.model.sections.Section;
import com.lxp.utils.CheckNull;
import java.util.List;


public class SectionController {
    private SectionService sectionService = new SectionService();

    public void findAllSections() {
        try {
            List<Section> sections = sectionService.findAllSections();

            if (sections.isEmpty()) {
                System.out.println("There is no sections List");
            } else {
                for (Section section : sections) {
                    System.out.println(section);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void findSection(Long sectionId) throws Exception {
        CheckNull.checkArgument(sectionId, "There is no sectionId");

        try {
            Section section = sectionService.findSecById(sectionId);
            System.out.println("Founded this section info: " + section);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void createSection(Section section) throws Exception {
        CheckNull.checkArgument(section, "Object is null. Please fill in every value");

        try {
            Long result = sectionService.upsertCourse(section);
            System.out.println("create Course Success" + result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateSection(Section section) {
        CheckNull.checkArgument(section, "Object is null. Please fill in every value");

        try {
            Long result = sectionService.upsertCourse(section);
            System.out.println("update Course Success" + result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCourse(Long sectionId) {
        CheckNull.checkArgument(sectionId, "section Id is null");

        try {
            Boolean result = sectionService.deleteSection(sectionId);
            System.out.println("delete Course Success" + result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
