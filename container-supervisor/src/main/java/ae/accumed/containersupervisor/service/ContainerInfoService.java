package ae.accumed.containersupervisor.service;

import ae.accumed.containersupervisor.DTO.ContainerInfo;
import ae.accumed.containersupervisor.repository.ContainerInfoRepository;
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

    public ArrayList<ContainerInfo> getContainersInfoByName(String name) {
        return containerInfoRepository.getContainersInfoByName(name);
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
