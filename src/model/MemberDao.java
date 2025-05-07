package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.Pt_DBUtil;

public class MemberDao {

	public List<MemberDto> getAllMembers() {
		List<MemberDto> members = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT m.*, p.total_session, p.regdate, pk.package_name\r\n" 
					+ "FROM member m\r\n"
					+ "LEFT JOIN purchase p ON m.member_id = p.member_id\r\n"
					+ "LEFT JOIN pt_package pk ON p.package_id = pk.package_id\r\n"
					+ "ORDER BY m.member_id";

		try {
			conn = Pt_DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Integer totalSession = rs.getInt("total_session");
				if (rs.wasNull()) {
					totalSession = 0;
				}
				MemberDto member = MemberDto.builder().MEMBER_ID(rs.getInt("member_id"))
						.MEMBER_NAME(rs.getString("member_name")).MEMBER_GENDER(rs.getString("member_gender"))
						.MEMBER_AGE(rs.getInt("member_age")).MEMBER_BIRTHDAY(rs.getString("member_birthday"))
						.MEMBER_PHONE(rs.getString("member_phone")).MEMBER_ADDRESS(rs.getString("member_address"))
						.TOTAL_SESSION(totalSession).REGDATE(rs.getString("regdate"))
						.PACKAGE_NAME(rs.getString("package_name")).build();

				members.add(member);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Pt_DBUtil.dbDisconnect(conn, pstmt, rs);
		}

		return members;
	}

	public List<MemberDto> findMembers(String name) {
		// TODO Auto-generated method stub

		List<MemberDto> members = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT m.*, p.total_session, p.regdate, pk.package_name\r\n" 
						+ "FROM member m\r\n"
						+ "LEFT JOIN purchase p ON m.member_id = p.member_id\r\n"
						+ "LEFT JOIN pt_package pk ON p.package_id = pk.package_id\r\n" 
						+ "where m.member_name=?"
						+ "ORDER BY m.member_id";

		try {
			conn = Pt_DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Integer totalSession = rs.getInt("total_session");
				if (rs.wasNull()) {
					totalSession = 0;
				}
				MemberDto member = MemberDto.builder().MEMBER_ID(rs.getInt("member_id"))
						.MEMBER_NAME(rs.getString("member_name")).MEMBER_GENDER(rs.getString("member_gender"))
						.MEMBER_AGE(rs.getInt("member_age")).MEMBER_BIRTHDAY(rs.getString("member_birthday"))
						.MEMBER_PHONE(rs.getString("member_phone")).MEMBER_ADDRESS(rs.getString("member_address"))
						.TOTAL_SESSION(totalSession).REGDATE(rs.getString("regdate"))
						.PACKAGE_NAME(rs.getString("package_name")).build();

				members.add(member);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Pt_DBUtil.dbDisconnect(conn, pstmt, rs);
		}

		return members;
	}

	public boolean deleteMembers(String name, String phone) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;

		try {
			conn = Pt_DBUtil.getConnection();
			conn.setAutoCommit(false); 

			String sql = "SELECT member_id FROM member WHERE member_name = ? AND member_phone = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, phone);
			rs = pstmt.executeQuery();

			int memberId = 0;
			if (rs.next()) {
				memberId = rs.getInt("member_id");
			} else {
				System.out.println("해당 회원을 찾을 수 없습니다.");
				return false;
			}
			rs.close();
			pstmt.close();

			
			String purchaseSql = "SELECT purchase_id FROM purchase WHERE member_id = ?";
			pstmt = conn.prepareStatement(purchaseSql);
			pstmt.setInt(1, memberId);
			rs = pstmt.executeQuery();
			List<Integer> purchaseIds = new ArrayList<>();
			
			while (rs.next()) {
				purchaseIds.add(rs.getInt("purchase_id"));
			}
			rs.close();
			pstmt.close();

			String deleteHistorySql = "DELETE FROM history WHERE purchase_id = ?";
			pstmt = conn.prepareStatement(deleteHistorySql);
			for (int p : purchaseIds) {
				pstmt.setInt(1, p);
				pstmt.executeUpdate();
			}
			pstmt.close();

			String deletePurchaseSql = "DELETE FROM purchase WHERE member_id = ?";
			pstmt = conn.prepareStatement(deletePurchaseSql);
			pstmt.setInt(1, memberId);
			pstmt.executeUpdate();
			pstmt.close();

			String deleteMemberSql = "DELETE FROM member WHERE member_id = ?";
			pstmt = conn.prepareStatement(deleteMemberSql);
			pstmt.setInt(1, memberId);
			pstmt.executeUpdate();
			

			conn.commit(); 
			result=true;

		} catch (SQLException e) {
			try {
				if (conn != null)
					conn.rollback(); 
			} catch (SQLException rollbackEx) {
				rollbackEx.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			Pt_DBUtil.dbDisconnect(conn, pstmt, rs);
		}

		return result;
	}

	public boolean insertMembers(String name, String gender, int age, String birthday, String phone, String address,
			int packageId, int totalSession) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int memberId = 0;
		int purchaseId = 0;
		boolean result = false;

		String memberSql = "INSERT INTO member (member_name, member_gender, member_age, member_birthday, member_phone, member_address) VALUES (?, ?, ?, ?, ?, ?)";
		String purchaseSql = "INSERT INTO purchase (member_id, package_id, total_session) VALUES (?, ?, ?)";
		String historySql = "INSERT INTO history (purchase_id, session_date, remaining_sessions) VALUES (?, ?, ?)";

		try {
			conn = Pt_DBUtil.getConnection();
			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(memberSql, new String[] { "member_id" });
			pstmt.setString(1, name);
			pstmt.setString(2, gender);
			pstmt.setInt(3, age);
			pstmt.setString(4, birthday);
			pstmt.setString(5, phone);
			pstmt.setString(6, address);
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				memberId = rs.getInt(1);
			}
			pstmt.close();

			pstmt = conn.prepareStatement(purchaseSql, new String[] { "purchase_id" });
			pstmt.setInt(1, memberId);
			pstmt.setInt(2, packageId);
			pstmt.setInt(3, totalSession);
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				purchaseId = rs.getInt(1);
			}
			
			pstmt.close();
			conn.commit(); 
			result = true;

		} catch (SQLException e) {
			try {
				if (conn != null)
					conn.rollback(); 
			} catch (SQLException rollbackEx) {
				rollbackEx.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			Pt_DBUtil.dbDisconnect(conn, pstmt, rs);
		}

		return result;
	}

	public boolean updateMembers(String column, String newVal, String name, String phone) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean result = false;

	    String sql = "UPDATE member SET " + column + " = ? WHERE member_name = ? AND member_phone = ?";

	    try {
	        conn = Pt_DBUtil.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        
	        pstmt.setString(1, newVal); 
	        pstmt.setString(2, name);  
	        pstmt.setString(3, phone);   

	        int count = pstmt.executeUpdate();

	        if (count > 0) {
	            result = true;
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        Pt_DBUtil.dbDisconnect(conn, pstmt, null); 
	    }

	    return result;
	}

	public boolean decreaseMembers(String name, String phone) {
		// TODO Auto-generated method stub
		Connection conn= null;
		PreparedStatement pstmt= null;
		ResultSet rs= null;
		boolean result= false;
		
		try {
			conn=Pt_DBUtil.getConnection();
			
			String memberSql="select member_id from member where member_name =? and member_phone=?";
			pstmt=conn.prepareStatement(memberSql);
			
			pstmt.setString(1,name);
			pstmt.setString(2,phone);
			rs=pstmt.executeQuery();
			
			int memberId=-1;
			
			if(rs.next()) {
				memberId= rs.getInt("member_id");
			}
			else {
				System.out.println("해당회원을 찾을수 없습니다.");
				return false;
			}
			
			rs.close();
			pstmt.close();
			
			String sql="update purchase set total_session= total_session-1 where member_id=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, memberId);
			int count= pstmt.executeUpdate();
			
			  if (count > 0) {
		            result = true;
		        }
			  else {
				 System.out.println("수업이 없거나 모두 소진되었습니다");
			  }
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			Pt_DBUtil.dbDisconnect(conn, pstmt, null);
		}
		return result;
	
	}
}