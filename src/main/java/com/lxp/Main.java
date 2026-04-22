package com.lxp;

import com.lxp.courses.controller.CreateCourseController;
import com.lxp.model.CreateCourseRequest;
import com.lxp.model.DTO.ContentInsertDTO;
import com.lxp.model.DTO.CourseInsertDTO;
import com.lxp.model.DTO.SectionInsertDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static CreateCourseController createCourseController = new CreateCourseController();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("1. 모든 강의 조회 2. 단일 강의 조희 3. 강의 생성(상세) 4. 강의 수정 5. 강의 삭제");
        System.out.print("명령어를 입력하세요: ");
        String command = sc.nextLine();

        if (command.equals("3")) {
            handleCreateCourse(sc);
        }
        // 나머지 명령어 추가 예정
    }

    private static void handleCreateCourse(Scanner sc) {
        System.out.println("=== 새 강의 생성 (상세) ===");

        System.out.print("강의 제목: ");
        String title = sc.nextLine();
        System.out.print("강의 설명: ");
        String description = sc.nextLine();

        CourseInsertDTO courseDTO = new CourseInsertDTO();
        courseDTO.setCourseTitle(title);
        courseDTO.setCourseDescription(description);

        List<SectionInsertDTO> sections = new ArrayList<>();
        while (true) {
            System.out.print("\n[섹션 추가] 섹션 제목을 입력하세요 (그만하려면 'q' 입력): ");
            String sectionTitle = sc.nextLine();
            if (sectionTitle.equalsIgnoreCase("q")) {
                break;
            }

            SectionInsertDTO sectionDTO = new SectionInsertDTO();
            sectionDTO.setSectionTitle(sectionTitle);

            List<ContentInsertDTO> contents = new ArrayList<>();
            while (true) {
                System.out.print("[컨텐츠 추가] 컨텐츠 제목 (그만하려면 'q' 입력): ");
                String contentTitle = sc.nextLine();
                if (contentTitle.equalsIgnoreCase("q")) {
                    break;
                }

                System.out.print("컨텐츠 URL: ");
                String url = sc.nextLine();
                System.out.print("재생 시간(초): ");
                int time = Integer.parseInt(sc.nextLine());

                ContentInsertDTO contentDTO = new ContentInsertDTO();
                contentDTO.setContentTitle(contentTitle);
                contentDTO.setContentUrl(url);
                contentDTO.setTime(time);
                contents.add(contentDTO);
            }

            sectionDTO.setContents(contents);
            sections.add(sectionDTO);
        }
        
        CreateCourseRequest request = new CreateCourseRequest();
        request.setCourse(courseDTO);
        request.setSections(sections);

        createCourseController.createCourse(request);
    }
}