package com.mis.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mis.dao.MemberDAO;
import com.mis.dto.MemberVO;

/**
 * Servlet implementation class MemberUpdateServlet
 */
@WebServlet("/memberUpdate.do")
public class MemberUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO 회원정보 수정 페이지 이동 + 회원 정보 담아서
		String url = "member/memberUpdate.jsp";
		
		String userid = request.getParameter("userid");
		MemberDAO mDao = MemberDAO.getInstance();
		
		// 회원 정보 가져오기
		MemberVO mVo = mDao.getMember(userid);
		
		request.setAttribute("mVo", mVo);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO 회원정보 수정 후 메인페이지 이동(login.do)
		
		// 사용자가 입력한 정보 request 객체 받아오기
		String userid = request.getParameter("userid");
		String pwd= request.getParameter("pwd");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String admin = request.getParameter("admin");
		
		// MemberVO 객체 생성
		MemberVO mVo = new MemberVO();
		
		mVo.setUserid(userid);
		mVo.setPwd(pwd);
		mVo.setEmail(email);
		mVo.setPhone(phone);
		mVo.setAdmin(Integer.parseInt(admin));
		
		// MemberDAO 회원 정보 수정
		MemberDAO mDao = MemberDAO.getInstance();
		mDao.updateMember(mVo);
		
		// 페이지 이동
		response.sendRedirect("login.do");
		
	}
	
	// 회원가입 과제
	// MemberDAO (method 2개 추가)
			// confiirmID() (중복확인가능)
			// insertMember() (회원가입기능)
	// Servlet 2개 추가
	// IdCheckServlet - > doGet(중복확인)
	// JoinServlet  - > doGet - > 회원가입페이지 이동.
				// - > doPost - > 회원가입

}
