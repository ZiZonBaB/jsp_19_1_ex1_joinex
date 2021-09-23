package com.javayongju.ex;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class JoinOk
 */
@WebServlet("/JoinOk")
public class JoinOk extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	//선언문
	
	private Connection connection;
	private Statement stmt;
	
	//join에서 올라오는 변수들 선언
	
	private String name,id,pw,phone1,phone2,phone3,gender;
	
    public JoinOk() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("doGet!!");
		actionDo(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		actionDo(request, response);
		System.out.println("doPost!!");
	}
	
	//actionDo 선언
	private void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
          //한글처리
		request.setCharacterEncoding("EUC-KR");
		
		//파라미터어
		name = request.getParameter("name");
		id = request.getParameter("id");
		pw = request.getParameter("pw");
		phone1 = request.getParameter("phone1");
		phone2 = request.getParameter("phone2");
		phone3 = request.getParameter("phone3");
		gender = request.getParameter("gender");
		
	String query = 	"insert into members values('"+ name+"','" + id + "','" + pw + "','" + phone1 + "','" + phone2 +  "','" + phone3 + "','" + gender + "')";
	
	//try catch 문
	try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","scott","tiger");
		stmt = connection.createStatement();
		
		int i  = stmt.executeUpdate(query); // 쿼리가 성공하면 int 값 정수 1을 반환
		
		if(i==1) {
			System.out.println("DB 저장 성공"); // 콘솔창에 찍어주기
			response.sendRedirect("joinResult.jsp"); // DB저장에 성공하면 joinResult.jsp 페이지로 이동
		} else {
			System.out.println("DB 저장 실패"); // 콘솔창에 찍어주기
			response.sendRedirect("join.html"); // DB저장에 실패하면 join.html로 이동
		}
		
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		try {
			
			if(stmt != null) stmt.close();
			if(connection != null) connection.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	}

}
