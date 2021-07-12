package ae.accumed.lookuprequestsmanager.dto;

import ae.accumed.lookuprequestsmanager.entities.Facility;
import ae.accumed.lookuprequestsmanager.entities.Payers;
import lombok.Data;

@Data
public class AccountDTO {
    private final int id;
    private final Boolean isActive;
    private final String userName;
    private final Facility facility;
    private final Payers payer;
}
