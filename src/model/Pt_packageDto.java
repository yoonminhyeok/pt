package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class Pt_packageDto {
	public int PACKAGE_ID;
	public String PACKAGE_NAME;
	public int PACKAGE_PRICE;
	public int SESSION_COUNT;
	public int TRAINER_ID;
}
