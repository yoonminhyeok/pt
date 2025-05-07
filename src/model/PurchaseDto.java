package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PurchaseDto {
    public int PURCHASE_ID;
	public String REGDATE;
	public int MEMBER_ID;
	public int PACKAGE_ID;
	public int TOTAL_SESSION;
}
