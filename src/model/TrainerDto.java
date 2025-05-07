package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainerDto {
    public int TRAINER_ID;
    public String TRAINER_NAME;
    public String TRAINER_PHONE;
    public String SPECIALTY;
}
