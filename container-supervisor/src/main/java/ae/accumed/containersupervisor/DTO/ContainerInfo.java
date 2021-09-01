package ae.accumed.containersupervisor.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContainerInfo {
    String id;
    String name;
    String status;
    String ports;
}
