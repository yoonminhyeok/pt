package service;   //변경입니두

import java.util.List;
import model.MemberDao;
import model.MemberDto;

public class MemberService {
    
    private MemberDao memberDao;

    public MemberService() {
        memberDao = new MemberDao();
    }

    public List<MemberDto> getAllMembers() {
        return memberDao.getAllMembers();
    }

	public List<MemberDto> findMembers(String name) {
		// TODO Auto-generated method stub
		return memberDao.findMembers(name);
	}

	public boolean delete(String name, String phone) {
		// TODO Auto-generated method stub
		return memberDao.deleteMembers(name,phone);
	}

	public boolean InsertMember(String name, String gender, int age, String birthday, String phone, String address,
			int packageId, int totalSession) {
		// TODO Auto-generated method stub
		return memberDao.insertMembers(name,gender,age,birthday,phone,address,packageId,totalSession);
	}



	public boolean updateMember(String column, String newVal, String name, String phone) {
		// TODO Auto-generated method stub
		return memberDao.updateMembers(column,newVal,name,phone);
	}

	public boolean decreaseSession(String name, String phone) {
		// TODO Auto-generated method stub
		return memberDao.decreaseMembers(name,phone);
	}

	
	
}
