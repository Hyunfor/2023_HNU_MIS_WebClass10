package com.mis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mis.dto.MemberVO;

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

}
