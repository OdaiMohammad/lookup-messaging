package ae.accumed.containersupervisor.repository;

import ae.accumed.containersupervisor.DTO.ContainerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

@Repository
public class ContainerInfoRepository {
    private static Logger logger = LoggerFactory.getLogger(ContainerInfoRepository.class);

    public ArrayList<ContainerInfo> getContainersInfoByName(String name) {
        String s;
        Process process;
        ArrayList<ContainerInfo> results = new ArrayList<>();
        try {
            process = Runtime.getRuntime().exec(String.format("docker ps -a --format {{.ID}}={{.Names}}={{.Status}}={{.Ports}} --filter name=%s", name));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((s = bufferedReader.readLine()) != null) {
                String[] values = s.split("=");
                if(values.length == 3)
                results.add(new ContainerInfo(values[0], values[1], values[2], ""));
                else if (values.length == 4)
                    results.add(new ContainerInfo(values[0], values[1], values[2], values[3]));

            }
            return results;
        } catch (Exception e) {
            return results;
        }
    }

    public void restartContainer(String containerId) {
        String s;
        Process process;
        try {
            process = Runtime.getRuntime().exec(String.format("docker restart %s", containerId));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((s = bufferedReader.readLine()) != null) {
                logger.info("Restarted container {}", s);
            }
        } catch (Exception e) {
            logger.error("Failed to restart container {}", containerId, e);
        }
    }

    public void startContainer(String containerId) {
        String s;
        Process process;
        try {
            process = Runtime.getRuntime().exec(String.format("docker start %s", containerId));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((s = bufferedReader.readLine()) != null) {
                logger.info("Started container {}", s);
            }
        } catch (Exception e) {
            logger.error("Failed to start container {}", containerId, e);
        }
    }

    public void stopContainer(String containerId) {
        String s;
        Process process;
        try {
            process = Runtime.getRuntime().exec(String.format("docker stop %s", containerId));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((s = bufferedReader.readLine()) != null) {
                logger.info("Stopped container {}", s);
            }
        } catch (Exception e) {
            logger.error("Failed to stop container {}", containerId, e);
        }
    }
}
