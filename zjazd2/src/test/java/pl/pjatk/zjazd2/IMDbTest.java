package pl.pjatk.zjazd2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IMDbTest extends BaseTest {

    @BeforeEach
    public void setUp() {
        setupBrowser("chrome");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
    }

    @Test
    public void testIMDbNavigationAndSearch() throws InterruptedException {
        // 1. Otwarcie strony IMDb
        driver.get("https://www.imdb.com/");

        // Asercja 1: Sprawdzenie tytułu strony głównej IMDb
        assertEquals("IMDb: Ratings, Reviews, and Where to Watch the Best Movies & TV Shows", driver.getTitle());

        // 2. Sprawdzenie czy głównego menu jest dostępne na stronie
        WebElement menu = driver.findElement(By.id("imdbHeader-navDrawerOpen"));
        assertTrue(menu.isDisplayed(), "Menu nawigacyjne jest widoczne");

        // 3. Przejście do zakładki "Top 250 Movies"
        menu.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(d -> menu.isDisplayed());
        WebElement topMoviesLink = driver.findElement(By.linkText("Top 250 Movies"));
        topMoviesLink.click();

        // Asercja 2: Sprawdzenie tytułu strony "Top 250 Movies"
        assertEquals("IMDb Top 250 Movies", driver.getTitle());

        // Asercja 3: Sprawdzenie, czy na liście znajduje się jakikolwiek film
        WebElement firstMovie = driver.findElement(By.partialLinkText("1."));
        assertTrue(firstMovie.isDisplayed());

        // 4. Przejście do strony szczegółowej pierwszego filmu
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstMovie);

        // Asercja 4: Sprawdzenie, że na stronie znajduje się link do "IMDb Pro"
        WebElement IMDbPro = driver.findElement(By.linkText("IMDbPro"));
        assertTrue(IMDbPro.isDisplayed());

        // Asercja 5: Sprawdzenie obecności oceny filmu
        WebElement ratingSection = driver.findElement(By.cssSelector("div.sc-acdbf0f3-0.iaJqqu.rating-bar__base-button"));
        assertTrue(ratingSection.isDisplayed(), "Ocena filmu jest widoczna");

        // Asercja 6: Sprawdzenie obecności opisu filmu
        WebElement storylineElement = driver.findElement(By.cssSelector("p.sc-3ac15c8d-3.bMUzwm"));
        assertTrue(storylineElement.isDisplayed(), "Opis fabuły jest widoczny");

        // 5. Wyszukiwanie filmu ("Inception")
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("Inception");
        searchBox.submit();

        // Asercja 7: Sprawdzenie tytułu strony wyników wyszukiwania
        assertTrue(driver.getTitle().contains("Find - IMDb"));

        // Asercja 8: Sprawdzenie, że pierwszy wynik wyszukiwania zawiera słowo "Inception"
        WebElement firstResult = driver.findElement(By.cssSelector("a.ipc-metadata-list-summary-item__t"));
        assertTrue(firstResult.getText().contains("Incepcja"), "Pierwszy wynik wyszukiwania zawiera słowo 'Inception'");

        // 6. Przejście do strony szczegółowej filmu "Incepcja"
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstResult);

        // Asercja 9: Sprawdzenie, że strona szczegółowa ma poprawny tytuł
        assertTrue(driver.getTitle().contains("Incepcja"), "Strona szczegółowa filmu 'Inception' ma poprawny tytuł");

        // Asercja 10: Sprawdzenie obecności sekcji z obsadą filmu
        WebElement castList = driver.findElement(By.cssSelector("section.ipc-page-section.ipc-page-section--base.sc-cd7dc4b7-0.ycheS.title-cast.title-cast--movie.celwidget"));
        assertTrue(castList.isDisplayed(), "Sekcja obsady jest widoczna");
    }

}
