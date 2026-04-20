# LXP Project
## 목적
LXP 사이트를 직접 구현해봄으로써, Java 및 JDBC 기반의 CRUD를 통해 Controller, Service, Repository 영역의 역할에 대해 깊이 이해하고, 이를 통해 실제 CRUD를 구현할 수 있다.

## ERD
![LXP ERD](./imgs/erd.png)

## 기능 소개
### 강좌
1. 강좌 개설 및 조회, 수정, 삭제가 가능하다. (강좌 삭제의 경우 soft delete가 적용된다) 
2. 강좌 개설 시, user 타입 중 teacher 타입만이 강의 개설이 가능하다.
3. 강좌는 Courses > Section > Contents 순으로 상세해지며, 각 모든 값은 개별적으로 공개 여부와 삭제 여부를 설정할 수 있다.

### 회원
1. 회원 가입을 하면 기본적으로 UUID 값이 할당되며, 회원 탈퇴시에는 동일하게 soft-delete 정책이 적용된다.
2. 회원은 'user' 와 'teacher' 타입을 가지며, 기본적으로 'user' 타입이 할당된다.
3. 1년간 아무런 활동을 하지 않은 유저의 경우, 휴먼 상태(sleep)가 되며, 정책을 위반한 경우 강제 탈퇴가 이루어지면 'banned' 상태가 된다.