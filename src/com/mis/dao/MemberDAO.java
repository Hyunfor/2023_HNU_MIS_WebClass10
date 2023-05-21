package com.mis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mis.dto.MemberVO;

import oracle.net.aso.p;

public class MemberDAO {

	private MemberDAO() { // 생성자

	}

	private static MemberDAO instance = new MemberDAO();

	public static MemberDAO getInstance() {

		return instance;
	}

	public Connection getConnection() throws Exception {

		Context initContext = new InitialContext();
		Context envContext = (Context) initContext.lookup("java:/comp/env");
		DataSource ds = (DataSource) envContext.lookup("jdbc/myoracle");
		Connection conn = ds.getConnection();

		return conn;

	}

	// 사용자 인증시 사용하는 메소드 => 로그인 기능
	public int userCheck(String userid, String pwd) {

		int result = -1; // -1은 회원정보가 없으면 -1로 리턴
		String sql = "SELECT PWD FROM MEMBER WHERE USERID=?";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				// 일치하는 userid 존재

				if (rs.getString("pwd") != null && rs.getString("pwd").equals(pwd)) {
					// 비밀번호 일치
					result = 1;
				} else {
					// 비밀번호 불일치. 0
					result = 0;

				}

			} else {
				// 일치하는 userid 없음
				result = -1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}

	// 사용자 정보 가져오는 기능
	public MemberVO getMember(String userid) {
		MemberVO mVo = null;
		String sql = "SELECT * FROM MEMBER WHERE USERID=?";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				mVo = new MemberVO();
				mVo.setName(rs.getString("name"));
				mVo.setUserid(rs.getString("userid"));
				mVo.setPwd(rs.getString("pwd"));
				mVo.setEmail(rs.getString("email"));
				mVo.setPhone(rs.getString("phone"));
				mVo.setAdmin(rs.getInt("admin"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mVo;

	}

	// 사용자 정보 수정 기능
	public int updateMember(MemberVO mVo) {

		int result = -1;

		String sql = "UPDATE MEMBER SET PWD =?, EMAIL=?, PHONE=?, ADMIN=? WHERE USERID=?";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			conn = getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, mVo.getPwd());
			pstmt.setString(2, mVo.getEmail());
			pstmt.setString(3, mVo.getPhone());
			pstmt.setInt(4, mVo.getAdmin());
			pstmt.setString(5, mVo.getUserid());

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}

	// 회원가입
	public int insertMember(MemberVO mVo) {
		int result = -1;
		String sql = "INSERT INTO MEMBER (USERID, PWD, EMAIL, PHONE, ADMIN) VALUES (?, ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			// 컬럼에 저장할 값은 매개 변수로 받은 VO 객체에서 얻어와 바인딩
			pstmt.setString(1, mVo.getName());
			pstmt.setString(2, mVo.getUserid());
			pstmt.setString(3, mVo.getPwd());
			pstmt.setString(4, mVo.getEmail());
			pstmt.setString(5, mVo.getPhone());
			pstmt.setInt(6, mVo.getAdmin());
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public int confiirmID(String userid) {
		int result = -1;
		String sql = "SELECT USERID FROM MEMBER WHERE USERID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = 1;
			} else {
				result = -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {

				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
