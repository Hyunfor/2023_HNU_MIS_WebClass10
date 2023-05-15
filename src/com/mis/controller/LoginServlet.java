package com.mis.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mis.dao.MemberDAO;
import com.mis.dto.MemberVO;
import com.sun.media.jfxmedia.MediaPlayer;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO 로그인 화면 이동
		String url = "member/login.jsp";
		
		/* 
		 * 로그인 되어 있으면, main.jsp 이동
		 * 로그인 되어 있지 않으면 로그인 페이지로 이동
		*/
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("loginUser") != null){
			url = "main.jsp";
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO 회원 인증 기능 -> 회원 인증하면 main.jsp 이동, 실패하면 login.jsp로 이동
		String url = "member/login.jsp";
		
		String userid = request.getParameter("userid");
		String pwd = request.getParameter("pwd");
		
		MemberDAO mDao = MemberDAO.getInstance();
		int result = mDao.userCheck(userid, pwd);
		
		if(result == 1){
			// User 정보 가져와 Session 담기
			
			MemberVO mVo = mDao.getMember(userid);
			
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", mVo);
			request.setAttribute("message", "회원가입에 성공했습니다.");
			
			url = "main.jsp";
			
		} else if(result == 0) {
			request.setAttribute("message", "비밀번호가 일치하지 않습니다.");
		} else if(result == -1) {
			request.setAttribute("message", "등록된 사용자 정보가 없습니다.");
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
	}

}
