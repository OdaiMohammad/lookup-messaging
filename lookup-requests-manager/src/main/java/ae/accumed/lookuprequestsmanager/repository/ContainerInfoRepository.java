package ae.accumed.lookuprequestsmanager.repository;

import ae.accumed.lookuprequestsmanager.dto.ContainerInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Repository
public class ContainerInfoRepository {

    private final RestTemplate restTemplate;

    @Value("${supervisor.server.address}")
    private String supervisorHost;

    @Autowired
    public ContainerInfoRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ArrayList<ContainerInfoDTO> getContainerInfo(String name) {
        ResponseEntity<ArrayList<ContainerInfoDTO>> response = restTemplate.exchange(String.format("http://%s/containers?name=%s", supervisorHost, name), HttpMethod.GET, null, new ParameterizedTypeReference<ArrayList<ContainerInfoDTO>>() {
        });
        return response.getBody();
    }
}
