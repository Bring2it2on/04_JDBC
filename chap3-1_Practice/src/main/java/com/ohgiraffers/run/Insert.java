package com.ohgiraffers.run;

import com.ohgiraffers.model.EmployeeDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Insert {

    public static void main(String[] args) {

        Connection con = getConnection();

        PreparedStatement pstmt = null;

        int result = 0;

        Properties prop = new Properties();

        try {
            prop.loadFromXML(
                    new FileInputStream(
                            "src/main/java/com/ohgiraffers/mapper/menu-query.xml"
                    )
            );

            String query = prop.getProperty("insert");

            pstmt = con.prepareStatement(query);

            Scanner sc = new Scanner(System.in);

            System.out.print("아이디를 입력하세요 : ");
            int empId = sc.nextInt();

            System.out.print("이름을 입력하세요 : ");
            String empName = sc.next();

            System.out.print("사원번호를 입력하세요 : ");
            String empNo = sc.next();

            System.out.print("이메일을 입력하세요 : ");
            String email = sc.next();

            System.out.print("전화번호를 입력하세요 : ");
            String phone = sc.next();

            System.out.print("부서코드를 입력하세요 : ");
            String deptCode = sc.next();

            System.out.print("직업코드를 입력하세요 : ");
            String jobCode = sc.next();

            System.out.print("연봉단계를 입력하세요 : ");
            String salLevel = sc.next();

            System.out.print("연봉을 입력하세요 : ");
            int salary = sc.nextInt();

            System.out.print("보너스를 입력하세요 : ");
            float bonus = sc.nextFloat();

            System.out.print("매니저 아이디를 입력하세요 : ");
            String managerId = sc.next();

            System.out.print("고용일은 현재 날짜를 반영합니다...");
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.sql.Date hireDate = java.sql.Date.valueOf(simpleDateFormat.format(date));

            System.out.print("퇴사여부(Y/N) : ");
            String entYN = sc.next().toUpperCase();

            java.sql.Date entDate = null;
            if(entYN.equals("Y")) {

                System.out.print("퇴사일을 입력하세요 (yyyyMMdd) : ");
                String getstr = sc.next();
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
                Date tempDate = null;
                try {
                    // 현재 yyyymmdd로된 날짜 형식으로 java.util.Date 객체를 만든다.
                    tempDate = df.parse(getstr);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                // java.util.Date를 yyyy-mm-dd 형식으로 변경하여 String으로 변환
                String transDate = simpleDateFormat.format(tempDate);

                // 반환된 String 값을 java.sql.Date로 변경한다.
                entDate = java.sql.Date.valueOf(transDate);

            }



            EmployeeDTO newEmployee = new EmployeeDTO();

            newEmployee.setEmpId(empId);
            newEmployee.setEmpName(empName);
            newEmployee.setEmpNo(empNo);
            newEmployee.setEmail(email);
            newEmployee.setPhone(phone);
            newEmployee.setDeptCode(deptCode);
            newEmployee.setJobCode(jobCode);
            newEmployee.setSalLevel(salLevel);
            newEmployee.setSalary(salary);
            newEmployee.setBonus(bonus);
            newEmployee.setManagerId(managerId);
            newEmployee.setHireDate(hireDate);
            newEmployee.setEntDate(entDate);
            newEmployee.setEntYN(entYN);



            /*-------------------------------다른 클래스라고 생각하세요---------------------------------*/

            pstmt.setInt(1, newEmployee.getEmpId());
            pstmt.setString(2,newEmployee.getEmpName());
            pstmt.setString(3, newEmployee.getEmpNo());
            pstmt.setString(4, newEmployee.getEmail());
            pstmt.setString(5, newEmployee.getPhone());
            pstmt.setString(6, newEmployee.getDeptCode());
            pstmt.setString(7, newEmployee.getJobCode());
            pstmt.setString(8, newEmployee.getSalLevel());
            pstmt.setInt(9, newEmployee.getSalary());
            pstmt.setFloat(10, newEmployee.getBonus());
            pstmt.setString(11, newEmployee.getManagerId());
            pstmt.setDate(12, newEmployee.getHireDate());
            pstmt.setDate(13, newEmployee.getEntDate());
            pstmt.setString(14, newEmployee.getEntYN());

            // insert,update, delete 할때는 executeUpdate() 사용
            result = pstmt.executeUpdate();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
            close(con);
        }

        if(result > 0) {
            System.out.println("메뉴 저장 성공!!");
        } else {
            System.out.println("메뉴 저장 실패...");
        }
    }


}
