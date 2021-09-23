package com.javayongju.ex;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginOk
 */
@WebServlet("/LoginOk")
public class LoginOk extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	private Connection connection;
	private Statement stmt;
	private ResultSet resultset;
	
	
	private String name,id,pw,phone1,phone2,phone3,gender;
	
    public LoginOk() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		actionDo(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		actionDo(request, response);
	}
	
	private void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
          //한글처리
		request.setCharacterEncoding("EUC-KR");
		
		//파라미터어
		id = request.getParameter("id");
		pw = request.getParameter("pw");
		

	String query = 	"select * from members where id= '"+id+"' and pw = '"+pw+"' ";
	
	//try catch 문
	try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","scott","tiger");
		stmt = connection.createStatement();
		resultset = stmt.executeQuery(query);
		
		while(resultset.next()){ //데이터 불러오기 한줄씩 차례대로 null값이 나올 때 까지
			 name =  resultset.getString("name");
			 id = resultset.getString("id");
			 pw =  resultset.getString("pw");
			 phone1 =  resultset.getString("phone1");
			 phone2 =  resultset.getString("phone2");
			 phone3 =  resultset.getString("phone3");
			 gender =  resultset.getString("gender");

		}
		// 세션객체 생성
		HttpSession httpsession = request.getSession();
		httpsession.setAttribute("name", name);
		httpsession.setAttribute("id", id);
		httpsession.setAttribute("pw", pw);
	
		// 보내버리기이
		response.sendRedirect("loginResult.jsp");
		
		
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
