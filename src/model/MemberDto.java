package model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {
	public int MEMBER_ID;
	public String MEMBER_NAME;
	public String MEMBER_GENDER;
	public int MEMBER_AGE;
	public String MEMBER_BIRTHDAY;
	public String MEMBER_PHONE;
	public String MEMBER_ADDRESS;
	

    public Integer TOTAL_SESSION;   
    public String REGDATE;         
    public String PACKAGE_NAME;  
}
