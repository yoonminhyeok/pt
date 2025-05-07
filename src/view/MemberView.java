package view;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.MemberDto;

public class MemberView {
    
	public void displayAllMembers(List<MemberDto> members) {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    System.out.println("============================================= 회원 목록 =======================================================");
	    System.out.printf("%-10s %-8s %-5s %-16s %-13s %-10s %-10s %s\n", "이름", "성별", "나이", "생년월일", "전화번호",
	    		"주소", "PT 세션 수", "등록한 pt 패키지");
	    System.out.println("--------------------------------------------------------------------------------------------------------------");

	    for (MemberDto m : members) {
	        try {
	            String birthDateStr = m.MEMBER_BIRTHDAY;
	            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
	            java.util.Date birthDate = inputFormat.parse(birthDateStr);
	            String formattedBirthday = sdf.format(birthDate);

	            System.out.printf("%-10s %-9s %-5d %-15s %-15s %-13s %-10s %s\n",
	       
	                    m.MEMBER_NAME,
	                    m.MEMBER_GENDER,
	                    m.MEMBER_AGE,
	                    formattedBirthday,
	                    m.MEMBER_PHONE,
	                    m.MEMBER_ADDRESS,
	                    m.TOTAL_SESSION,
	                    m.PACKAGE_NAME);
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("생일: " + m.MEMBER_BIRTHDAY);
	        }
	    }
	  
	}

}
