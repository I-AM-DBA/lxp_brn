# LXP Project
## 목적
LXP 사이트를 직접 구현해봄으로써, Java 및 JDBC 기반의 CRUD를 통해 Controller, Service, Repository 영역의 역할에 대해 깊이 이해하고, 이를 통해 실제 CRUD를 구현할 수 있다.

## ERD
![LXP ERD](./imgs/erd.png)

## 기능 소개
1. 강좌 개설 및 수정, 삭제가 가능하다. 
2. 강좌 삭제의 경우 soft delete가 적용된다.
3. 강좌 개설 시, user 타입 중 Teacher 타입만이 강의 개설이 가능하다.
4. 회원 가입을 하면 기본적으로 UUID 값이 할당되며, 회원 탈퇴시에는 동일하게 soft-delete 정책이 적용된다.