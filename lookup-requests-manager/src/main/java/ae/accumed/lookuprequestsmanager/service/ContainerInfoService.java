package ae.accumed.lookuprequestsmanager.service;

import ae.accumed.lookuprequestsmanager.dto.ContainerInfoDTO;
import ae.accumed.lookuprequestsmanager.repository.ContainerInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ContainerInfoService {
    private final ContainerInfoRepository containerInfoRepository;

    @Autowired
    public ContainerInfoService(ContainerInfoRepository containerInfoRepository) {
        this.containerInfoRepository = containerInfoRepository;
    }

    public ArrayList<ContainerInfoDTO> getContainerInfo(String name) {
        return containerInfoRepository.getContainerInfo(name);
    }

    public void restartContainer(String containerId) {
        containerInfoRepository.restartContainer(containerId);
    }

    public void startContainer(String containerId) {
        containerInfoRepository.startContainer(containerId);
    }

    public void stopContainer(String containerId) {
        containerInfoRepository.stopContainer(containerId);
    }
}
