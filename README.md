# ManagementSystem

## 프로젝트 소개
ManagementSystem은 학생과 직원의 정보를 쉽고 편리하게 관리할 수 있는 시스템입니다. Java와 MySQL로 개발되었으며, 명령줄 인터페이스(CLI)를 통해 간단하고 직관적인 사용 환경을 제공합니다.

---

## 주요 기능

### 학생 관리
- 학생 정보의 추가, 수정, 삭제, 조회 가능

### 직원 관리
- 정규직 직원의 성과 평가와 급여 관리
- 파트타임 직원의 시급 및 근무시간을 통한 급여 자동 계산

### 데이터베이스 관리
- MySQL 데이터베이스 연동
- 저장 프로시저(Stored Procedure)를 이용한 효율적인 데이터 처리

---

## 설치 방법

### 요구 사항
- Java 8 이상
- MySQL 5.7 이상

### 프로젝트 다운로드
```bash
git clone https://github.com/your-repo/ManagementSystem.git
cd ManagementSystem
```

### 데이터베이스 설정

#### dbinfo 파일 설정
다음과 같이 `util/dbinfo.properties` 파일을 생성하고, 본인의 데이터베이스 설정에 맞게 YOURDATA 부분을 변경하세요.

```properties
driver=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://localhost:3306/YOURDATA?serverTimezone=YOURDATA
user=YOURDATA
password=YOURDATA
```

#### 테이블 생성

**학생 테이블**
```sql
CREATE TABLE Student (
    sno VARCHAR(20) PRIMARY KEY,
    name VARCHAR(20),
    korean INT,
    english INT,
    math INT,
    science INT
);
```

**정규직 직원 테이블**
```sql
CREATE TABLE Fulltime (
    empno VARCHAR(20) PRIMARY KEY,
    name VARCHAR(20),
    result INT,
    basicsalary INT
);
```

**파트타임 직원 테이블**
```sql
CREATE TABLE Parttime (
    empno VARCHAR(20) PRIMARY KEY,
    name VARCHAR(20),
    hourwage INT,
    workhour INT
);
```

#### 저장 프로시저 생성 예시
**학생 정보 추가 프로시저**
```sql
DELIMITER $$
CREATE PROCEDURE STUDENT_INSERT (
    IN sno VARCHAR(20), IN name VARCHAR(20),
    IN korean INT, IN english INT, IN math INT, IN science INT,
    OUT RTN_CODE INT
)
BEGIN
    INSERT INTO Student VALUES(sno, name, korean, english, math, science);
    SET RTN_CODE = 200;
END$$
DELIMITER ;
```
그 외 프로시저의 생성 예시는 [저장 프로시저](https://github.com/psns0122/ms_DBProcedure)를 참고하세요.

---

## 실행 방법
```bash
javac -d bin $(find . -name "*.java")
java -cp bin view.MainMenu
```

---

## 프로젝트 구조

```
ManagementSystem/
├── controller/        # 사용자 요청을 처리하여 모델과 뷰를 연결
├── model/             # 데이터베이스 접근 및 관리
├── util/              # 공통 유틸리티
├── view/              # 사용자 인터페이스 (CLI)
├── vo/                # 데이터 전송 객체(Value Objects)
├── .gitignore         # Git 버전 관리 제외 목록
├── LICENSE            # MIT 라이선스 정보
└── README.md          # 프로젝트 설명서
```

---

## 기여 방법
- GitHub에서 Issue를 등록하여 개선 사항을 제안할 수 있습니다.
- Repository를 Fork한 뒤, 수정 사항을 반영하여 Pull Request를 제출해 주세요.

---

## 제작자

- [정유진](https://github.com/yujini02): 사용자 인터페이스 담당 (yujinjeong76@gmail.com)
- [김성준](https://github.com/kimsj18): 학생 모델 담당 (kimsj0118@gmail.com)
- [김선민](https://github.com/seonmin12): 직원 모델 담당 (seonmin.kim1030@gmail.com)
- [박건희](https://github.com/psns0122): DB 모델링 담당 (psns0122@nave.com)

---

## 라이선스
본 프로젝트는 MIT 라이선스를 따릅니다. 자세한 내용은 [LICENSE](LICENSE)를 참고하세요.

---

## 참고 자료
- [MySQL 공식 문서](https://dev.mysql.com/doc/)
- [Java JDBC 튜토리얼](https://docs.oracle.com/javase/tutorial/jdbc/)
- [MySQL 저장 프로시저 가이드](https://www.mysqltutorial.org/getting-started-with-mysql-stored-procedures.aspx)


---

## 프로젝트로 인해 느낀점
처음으로 해보는 팀프로젝트, 새로운 것을 배우는것을 넘어 협업은 역시 어렵다는 것을 또한번 느꼈다.


숙제로 게시판을 만들어보았지만 DB연결과 새로운 코드는 또 다른 도전이였다.
학생 모델을 담당하였으며, 입출력, 삭제 순위 등을 구현해보았다.
또한 협업에 있어 깃허브 사용이 중요하다는 것을 다시한번 느낄 수 있었다. 
