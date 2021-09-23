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
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ModifyOk
 */

@WebServlet("/ModifyOk")
public class ModifyOk extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	private Connection connection;
	private Statement stmt;

	private String name,id,pw,pw2,phone1,phone2,phone3,gender;

	HttpSession httpsession;

	public ModifyOk() {
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
		httpsession = request.getSession(); // 겟세션으로 현재값을 불러오는 과정이 필요하다!
		//파라미터어
		name = request.getParameter("name");
		//id = request.getParameter("id");
		id = (String)httpsession.getAttribute("id");
		pw2 =(String)httpsession.getAttribute("pw"); // pw2 = 객체 , (String)httpsession.getAttribute("pw") = 문자열
		pw = request.getParameter("pw");
		phone1 = request.getParameter("phone1");
		phone2 = request.getParameter("phone2");
		phone3 = request.getParameter("phone3");
		gender = request.getParameter("gender");

		//오브젝트끼리 같은지를 봐주어야 한다. 그래서 .equals를 사용하여 쓴다!
		//객체들끼리 비교할때는 equals를 사용
		if(pw2.equals(pw)) { //참일때의 값

			String query = 	"update members set name='"+name+"',phone1='"+phone1+"',phone2='"+phone2+"',phone3='"+phone3+"',gender='"+gender+"' where id='"+id+"' ";

			//try catch 문
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","scott","tiger");
				stmt = connection.createStatement();

				int i  = stmt.executeUpdate(query); // 쿼리가 성공하면 int 값 정수 1을 반환

				if(i==1) {
					System.out.println("정보 수정 성공"); // 콘솔창에 찍어주기
					httpsession.setAttribute("name", name); //수정된 이름을 세션에 overwrite
					response.sendRedirect("modifyResult.jsp"); // 정보수정 성공 시 modifyResult.jsp로 이동
				} else {
					System.out.println("정보 수정 실패"); // 콘솔창에 찍어주기
					response.sendRedirect("modify.jsp"); // 정보수정에 실패하면 modify.jsp로 이동하여 다시 정보 입력
				}

				//에러처리이이이이이이
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
		} else {
			//틀린내용 집어넣기
			System.out.println("비밀번호의 값이 다릅니다.");
			response.sendRedirect("modify.jsp");
		}

	}
}
