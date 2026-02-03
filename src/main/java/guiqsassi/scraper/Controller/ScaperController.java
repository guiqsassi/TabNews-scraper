package guiqsassi.scraper.Controller;


import guiqsassi.scraper.Dtos.ResponseDTO;
import guiqsassi.scraper.Service.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(path = "/")
public class ScaperController {

    @Autowired
    private ScraperService scraperService;



    @GetMapping("/")
    public Set<ResponseDTO> getVehicleByModel(@RequestParam Integer page) {

        return scraperService.scrapeNews(page);
    }



}
