package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class HistoryDto {
	public int HISTORY_ID;
	public int PURCHASE_ID;
	public String SESSION_DATE;
	public int REMAINING_SESSIONS;
	
}