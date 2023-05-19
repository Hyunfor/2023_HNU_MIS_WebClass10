package com.mis.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mis.dao.MemberDAO;

/**
 * Servlet implementation class IdCheckServlet
 */
@WebServlet("/idCheck.do")
public class IdCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public IdCheckServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 체크하고자 하는 ID 값을 받아옵니다.
		String userid = request.getParameter("userid");

		// 아이디 중복을 체크하는 로직을 작성
		// 필요한 경우 데이터베이스와 연동하여 아이디 중복 여부를 확인
		MemberDAO mDao = MemberDAO.getInstance();
		boolean isDuplicate = mDao.confiirmID(userid);

		// 중복 여부에 따라 결과를 전달
		response.setContentType("text/plain");
		if (isDuplicate) {
			response.getWriter().write("중복된 아이디입니다.");
		} else {
			response.getWriter().write("사용 가능한 아이디입니다.");
		}
	}

	/**
	 * @see HttpServlet#doPost( HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
