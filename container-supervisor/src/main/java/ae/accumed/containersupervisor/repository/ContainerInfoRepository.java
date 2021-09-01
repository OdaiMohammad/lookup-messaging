package ae.accumed.containersupervisor.repository;

import ae.accumed.containersupervisor.DTO.ContainerInfo;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

@Repository
public class ContainerInfoRepository {
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
}
