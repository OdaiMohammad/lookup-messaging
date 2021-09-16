package ae.accumed.lookuprequestsmanager.controller;

import ae.accumed.lookuprequestsmanager.service.StatisticsService;
import ae.accumed.lookuprequestsmanager.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/stats")
public class StatisticsController {
    private final StatisticsService statisticsService;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping
    public String getStats(@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, Model model) {
        Date start, end;
        try {
            if (startDate == null)
                start = DateUtils.atStartOfDay(DateUtils.minusDays(new Date(), 1));
            else
                start = dateFormat.parse(startDate);
            if (endDate == null)
                end = DateUtils.atEndOfDay(new Date());
            else
                end = dateFormat.parse(endDate);
        } catch (Exception e) {
            start = DateUtils.atStartOfDay(DateUtils.minusDays(new Date(), 1));
            end = DateUtils.atEndOfDay(DateUtils.plusDays(DateUtils.atStartOfDay(new Date()), 1));
        }
        model.addAttribute("data", statisticsService.getStats(start, end));
        model.addAttribute("startDate", dateFormat.format(start));
        model.addAttribute("endDate", dateFormat.format(end));
        return "stats";
    }
}
