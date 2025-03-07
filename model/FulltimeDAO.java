package model;

import util.DBUtil;
import vo.EmployeeVO;
import vo.FulltimeVO;
import vo.ParttimeVO;
import vo.StudentVO;

import java.sql.*;
import java.util.ArrayList;

public class FulltimeDAO implements Fulltime  {

    // 테스트용 메인
    public static void main(String[] args) {
        FulltimeDAO dao = FulltimeDAO.getInstance();
        FulltimeVO fulltimeVO = new FulltimeVO();
        fulltimeVO.setEmpNo("2345");
        fulltimeVO.setName("신숙자");
        fulltimeVO.setResult(170);
        fulltimeVO.setBasicSalary(5000000);



        dao.update(fulltimeVO);





    }

    private static FulltimeDAO dao;

    private FulltimeDAO(){ }

    public static FulltimeDAO getInstance(){
        if(dao==null) dao = new FulltimeDAO();

        return dao;
    }

    private ArrayList<FulltimeVO> fulltimeList;
    private Connection conn;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;
    private CallableStatement cs;

    // 사용한 자원 반납하는 메소드
    private void disconnect(){
        if (rs != null) try {rs.close();} catch (SQLException e) {}
        if (stmt != null) try {stmt.close();} catch (SQLException e) {}
        if (pstmt != null) try {pstmt.close();} catch (SQLException e) {}
        if (conn != null) try {conn.close();} catch (SQLException e) {}


    }



    @Override
    public void delete(String deleteNum) {
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            cs = conn.prepareCall("{call FULLTIME_DELETE(?,?)}");

            cs.setString(1, deleteNum);
            cs.registerOutParameter(2,java.sql.Types.INTEGER);

            boolean flag = cs.execute();
            System.out.println(flag);

            String resultMsg = cs.getString(2);
            System.out.println(resultMsg);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }




    @Override
    public void update(EmployeeVO newEmployee) {
        FulltimeVO fulltime = new FulltimeVO();
        if(newEmployee instanceof FulltimeVO){
            fulltime = (FulltimeVO) newEmployee;

        }else {
            System.out.println("업데이트 할 직원은 풀타임이 아닙니다.");
            return;

        }
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            cs = conn.prepareCall("{call FULLTIME_UPDATE(?,?,?,?,?)}");


          // 바인드 변수에 값 세팅(in parameter)
            cs.setString(1, fulltime.getEmpNo());
            cs.setString(2, fulltime.getName());
            cs.setInt(3,fulltime.getResult());
            cs.setInt(4, fulltime.getBasicSalary());

            // out 파라미터에 저장된 프로시저의 수행결과에 대한 외부 변수 등록
            cs.registerOutParameter(5, java.sql.Types.INTEGER);

            // 쿼리 수행, flag 값은 RS의 경우 true, 갱신, 카운트 또는 결과가 없는 경우 false 리턴
            boolean flag = cs.execute();
            System.out.println(flag);

            String resultMsg = cs.getString(5);
            System.out.println(resultMsg);



//            String sql = "update Fulltime set "
//                    + "name = ?, "
//                    + "result =?, "
//                    + "basicSalary = ?, "
//                    + "where empNo = ? ";
//            pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, newEmployee.getName());
//            pstmt.setInt(2,fulltime.getResult());
//            pstmt.setInt(3,fulltime.getBasicSalary());
//            pstmt.setString(4, fulltime.getEmpNo());
//
//            pstmt.executeUpdate();
//            fulltimeList.add(fulltime);
//            calcRankAndPercent(newFulltime);
//            calcSalaryIncrease(newFulltime);




        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }


    }

    @Override
    public void totalSearch() {

        try {
            conn = DBUtil.getConnection();
            String sql = "select * from Fulltime ";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            fulltimeList = new ArrayList<>();

            //  fulltimeList가 null이면 새로 생성, 이미 있으면 초기화
            if (fulltimeList == null) {
                fulltimeList = new ArrayList<>();
            } else {
                fulltimeList.clear(); // 기존 데이터 유지하면서 내용만 삭제
            }

            while(rs.next()){
                FulltimeVO fulltime = new FulltimeVO();
                fulltime.setName(rs.getString("name"));
                fulltime.setEmpNo(rs.getString("empNo"));
                fulltime.setResult(rs.getInt("result"));
                fulltime.setBasicSalary(rs.getInt("basicSalary"));
                fulltimeList.add(fulltime);



            }


            // 데이터 불러온 후, 모든 직원 순위 계산

//            for (FulltimeVO fulltimeVO : fulltimeList) {
//                calcRankAndPercent(fulltimeVO);
//                calcSalaryIncrease(fulltimeVO);
//            }

            // 출력
            for (FulltimeVO fulltimeVO : fulltimeList) {
                System.out.println(fulltimeVO);
            }




        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }


    }




    @Override
    public void search(String searchNum) {
        try {
            fulltimeList = new ArrayList<>();
            conn = DBUtil.getConnection();
            String sql = "select * from Fulltime where empNo = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, searchNum);

            rs = pstmt.executeQuery();


            while(rs.next()){
                FulltimeVO fulltimeVO = new FulltimeVO();
                fulltimeVO.setName(rs.getString("name"));
                fulltimeVO.setEmpNo(rs.getString("empNo"));
                fulltimeVO.setResult(rs.getInt("result"));
                fulltimeVO.setBasicSalary(rs.getInt("basicSalary"));


                fulltimeList.add(fulltimeVO);
//                calcRankAndPercent(fulltimeVO);
//                calcSalaryIncrease(fulltimeVO);
                System.out.println("검색된 직원 정보: " + fulltimeVO);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }


    }




    @Override
    public void sort(int sortNum) {
        try {
            conn = DBUtil.getConnection();
            String sql = "";
            switch (sortNum) {
                case 1: sql = "select * from Fulltime order by empNo ";
                break;
                case 2: sql = "select * from Fulltime order by name  ";
                break;
                case 3: sql = "select * from Fulltime order by result desc  ";
                break;
                default:
                    System.out.println("잘못 입력했습니다.");
                    return;
            }
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            // fulltimeList가 null이면 초기화
            if (fulltimeList == null) {
                fulltimeList = new ArrayList<>();
            } else {
                fulltimeList.clear(); // 기존 리스트 초기화
            }



            while(rs.next()){
                FulltimeVO vo = new FulltimeVO();
                vo.setName(rs.getString("name"));
                vo.setEmpNo(rs.getString("empNo"));
                vo.setResult(rs.getInt("result"));
                vo.setBasicSalary(rs.getInt("basicSalary"));


                fulltimeList.add(vo); // DB에서 모든 데이터를 fulltimelist에 먼저 추가 해야함


            }
            // 이제 전체 리스트가 완성되었으므로 순위와 백분율을 계산
//            for (FulltimeVO vo : fulltimeList) {
//                calcRankAndPercent(vo);
//                calcSalaryIncrease(vo);
//            }

            // 계산된 전체 리스트 출력
            for (FulltimeVO vo : fulltimeList) {
                System.out.println(vo);
            }




        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }


    }

//    @Override
//    public void assignGrade(FulltimeVO fulltimeVO) {
//        if (fulltimeVO == null) {
//            System.out.println("잘못된 직원 정보입니다.");
//            return;
//        }
//
//        double percentage = fulltimeVO.getPercentage(); // 백분율 가져오기
//        char grade;
//
//        if (percentage <= 10) {
//            grade = 'A';
//        } else if (percentage <= 30) {
//            grade = 'B';
//        } else if (percentage <= 50) {
//            grade = 'C';
//        } else {
//            grade = 'F';
//        }
//
//        fulltimeVO.setResultGrade(grade);
//
//    }

//    @Override
//    public void calcRankAndPercent(FulltimeVO fulltimeVO) {
//
//        if (fulltimeList == null || fulltimeList.isEmpty() || fulltimeVO == null) {
//            System.out.println("직원 리스트가 비어 있거나 잘못된 입력입니다.");
//            return;
//        }
//
//        //  전체 직원 리스트를 기반으로 실적 기준으로 정렬된 리스트 생성 (원본 데이터 변경 X)
//        ArrayList<FulltimeVO> sortedList = new ArrayList<>(fulltimeList);
//        sortedList.sort((a, b) -> Integer.compare(b.getResult(), a.getResult()));
//
//
//        // 순위 찾기
//        int totalEmployees = sortedList.size();
//        int rank = 1;
//
//        for (int i = 0; i < totalEmployees; i++) {
//            FulltimeVO current = sortedList.get(i);
//
//            // ✅ 동일한 실적이면 동일한 순위 유지
//            if (i > 0 && current.getResult() < sortedList.get(i - 1).getResult()) {
//                rank = i + 1;
//            }
//
//            if (current.getEmpNo().equals(fulltimeVO.getEmpNo())) {
//                fulltimeVO.setRank(rank);
//                break;
//            }
//        }
//
//        // 백분율 계산 = (순위 / 전체 사원 수) * 100
//        double percentage = ((double) fulltimeVO.getRank() / totalEmployees) * 100;
//        fulltimeVO.setPercentage(percentage);
//
//        // 등급 설정
//        assignGrade(fulltimeVO);
//
//
//
//    }
//
//    @Override
//    public void calcSalaryIncrease(FulltimeVO fulltimeVO) {
//        if (fulltimeVO == null) {
//            System.out.println("잘못된 직원 정보입니다.");
//            return;
//        }
//
//        int increaseAmount;
//        switch (fulltimeVO.getResultGrade()) {
//            case 'A': increaseAmount = (int) (fulltimeVO.getBasicSalary() * 0.20); break;
//            case 'B': increaseAmount = (int) (fulltimeVO.getBasicSalary() * 0.10); break;
//            case 'C': increaseAmount = (int) (fulltimeVO.getBasicSalary() * 0.05); break;
//            default: increaseAmount = 0; // 인상 없음
//        }
//
//
//        fulltimeVO.setBasicSalary(fulltimeVO.getBasicSalary() + increaseAmount);
//
//
//    }

    @Override
    public void input(EmployeeVO newEmployee) {
        FulltimeVO fulltime = null;
        if(newEmployee instanceof FulltimeVO){
            fulltime = (FulltimeVO) newEmployee;

        }else {
            System.out.println("업데이트 할 직원은 풀타임이 아닙니다.");
            return;

        }

        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            cs = conn.prepareCall("{call FULLTIME_INSERT(?,?,?,?,?)}");

            cs.setString(1, fulltime.getEmpNo());
            cs.setString(2, fulltime.getName());
            cs.setInt(3, fulltime.getResult());
            cs.setInt(4, fulltime.getBasicSalary());

            cs.registerOutParameter(5, java.sql.Types.INTEGER);

            boolean flag = cs.execute();
            System.out.println(flag);

            int resultMsg = cs.getInt(5);
            System.out.println(resultMsg);



        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }


    };
}
