package com.lxp;

import com.lxp.config.JDBCConnection;
import com.lxp.courses.controller.CourseController;
import com.lxp.model.courses.CourseDTO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static CourseController courseController = new CourseController();

    public static void main(String[] args) {
        try (Connection connection = JDBCConnection.getConnection();
                Scanner sc = new Scanner(System.in);) {
            System.out.println("succeed to connection: " + connection);

            System.out.println("1. 모든 강의 조회 2. 단일 강의 조회 3. 강의 생성 4. 강의 수정 5. 강의 삭제");
            System.out.println("명령어를 입력하세요: ");
            String command = sc.nextLine();
            Main.CourseComand(command, sc);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void CourseComand(String command, Scanner sc) {
        while (true) {
            if (command.equals("1")) {
                courseController.findAllCourses();
                break;
            } else if (command.equals("2")) {
                System.out.println("조회하고 싶은 강의 id를 입력하세요");
                String course_id = sc.nextLine();

                courseController.findCourse(Long.valueOf(course_id));
                break;
            } else if (command.equals("3")) {
                System.out.println("강의명: ");
                String title = sc.nextLine();
                System.out.println("생성할 강의 설명을 입력하세요");
                String description = sc.nextLine();
                description = description.isEmpty() ? null : description;
                courseController.createCourse(new CourseDTO(title, description));
                break;
            } else if (command.equals("4")) {
                System.out.println("수정할 강의 id를 입력하세요");
                String course_id = sc.nextLine();
                System.out.println("강의명: ");
                String title = sc.nextLine();
                System.out.println("수정할 강의 설명을 입력하세요");
                String description = sc.nextLine();
                description = description.isEmpty() ? null : description;
                System.out.println("강의 공개 여부를 선택하세요");
                boolean isPublic = sc.nextBoolean();
                System.out.println("강의 삭제 여부를 선택하세요");
                boolean isDeleted = sc.nextBoolean();
                courseController.updateCourse(
                        new CourseDTO(Long.valueOf(course_id), title, description, isPublic,
                                isDeleted));
                break;
            } else if (command.equals("5")) {
                System.out.println("삭제할 강의 id를 입력하세요");
                String course_id = sc.nextLine();
                courseController.deleteCourse(Long.valueOf(course_id));
                break;
            }
        }
    }
}
