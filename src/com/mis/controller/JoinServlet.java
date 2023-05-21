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

/**
 * Servlet implementation class JoinServlet
 */
@WebServlet("/join.do")
public class JoinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public JoinServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 회원가입 페이지로 이동합니다.
		/*String url = "member/join.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);*/
		
        RequestDispatcher dispatcher = request.getRequestDispatcher("member/join.jsp");
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		// 사용자가 입력한 정보 request 객체 받아오기
		String name = request.getParameter("name");
		String userid = request.getParameter("userid");
		String pwd = request.getParameter("pwd");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String admin = request.getParameter("admin");

		// MemberVO 객체 생성
		MemberVO mVo = new MemberVO();
		// 회원가입 from에서 받은 데이터 저장
		mVo.setName(name);
		mVo.setUserid(userid);
		mVo.setPwd(pwd);
		mVo.setEmail(email);
		mVo.setPhone(phone);
		mVo.setAdmin(Integer.parseInt(admin));
		
		// MemberVO 객체를 전달인자로 주어 MemberDAO객체로 insertMember 메소드를 호출
		MemberDAO mDao = MemberDAO.getInstance();
		int result = mDao.insertMember(mVo);

		HttpSession session = request.getSession();
		
		if (result==1) {
			// session에 userid를 키로 하여 가입한 아이디 값을 저장 후 login.jsp로 제어를 이동
			session.setAttribute("userid", mVo.getUserid());
			request.setAttribute("message", "회원가입에 성공했습니다.");
		}else {
			request.setAttribute("message", "회원가입에 실패했습니다.");
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("member/login.jsp");
		dispatcher.forward(request, response);

	}

}
