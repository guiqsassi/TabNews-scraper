package guiqsassi.scraper.Service;


import guiqsassi.scraper.Dtos.ResponseDTO;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;

@Service
public class ScraperService {

    @Value("${website}")
    String url;


    public Set<ResponseDTO> scrapeNews(Integer page){
        Set<ResponseDTO> responseDTOs = new HashSet<>();

        if(page == null){
            page = 1;
        }

        try{
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless", "--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");

            WebDriver driver = new ChromeDriver(options);

            driver.get(url + "/pagina/" + page);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("a")));


            for (WebElement e : driver.findElements(By.cssSelector("ol > li article"))) {
                try {
                    WebElement linkEl = e.findElement(By.cssSelector("a[href]"));
                    WebElement spanEl = e.findElement(By.cssSelector("span"));
                    WebElement authorEl = e.findElement(By.cssSelector("address > a[href]"));

                    String href = linkEl.getAttribute("href");
                    String tabcoins = spanEl.getText();
                    String text = linkEl.getText();
                    String author = authorEl.getText();

                    if (href != null && !href.isBlank()) {
                        ResponseDTO dto = new ResponseDTO();
                        dto.setUrl(href);
                        dto.setTitle(text);
                        dto.setTabcoins(tabcoins);
                        dto.setAuthor(author);
                        responseDTOs.add(dto);
                    }
                } catch (StaleElementReferenceException ignored) {
                }
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return responseDTOs;
    }





}
