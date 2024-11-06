package pl.pjatk.zjazd2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WikipediaTest extends BaseTest {

    @BeforeEach
    public void setUp() {
        setupBrowser("edge");
    }

    @Test
    public void testWikipediaNavigationAndSearch() {
        // 1. Otwarcie Wikipedii
        driver.get("https://www.wikipedia.org/");

        // Asercja 1: Sprawdzenie tytułu strony głównej
        String homeTitle = driver.getTitle();
        assertEquals("Wikipedia", homeTitle);

        // Asercja 2: Sprawdzenie widoczności loga Wikipedia na stronie głównej
        WebElement logo = driver.findElement(By.cssSelector(".central-featured-logo"));
        assertTrue(logo.isDisplayed());

        // Asercja 3: Sprawdzenie widoczności przycisku wyszukiwania na stronie głównej
        WebElement searchInput = driver.findElement(By.name("search"));
        assertTrue(searchInput.isDisplayed());

        // 2. Wyszukiwanie frazy "Selenium"
        searchInput.sendKeys("Selenium");
        WebElement searchButton = driver.findElement(By.xpath("//button[@type='submit']"));
        searchButton.click();

        // Asercja 4: Sprawdzenie tytułu na stronie wyników wyszukiwania
        String searchResultTitle = driver.getTitle();
        assertTrue(searchResultTitle.contains("Selenium – Wikipedia, wolna encyklopedia"));

        // Asercja 5: Sprawdzenie, że nagłówek na stronie wyników wyszukiwania to "Selenium"
        WebElement heading = driver.findElement(By.className("mw-page-title-main"));
        assertEquals("Selenium", heading.getText());

        // Asercja 6: Sprawdzenie, że na stronie wyników wyszukiwania znajduje się sekcja "Contents"
        WebElement contentsSection = driver.findElement(By.id("content"));
        assertTrue(contentsSection.isDisplayed());

        // 3. Przejście do sekcji historii na stronie artykułu
        WebElement historyLink = driver.findElement(By.linkText("Wyświetl historię"));
        historyLink.click();

        // Asercja 7: Sprawdzenie, czy nagłówek po przejściu do sekcji to "Selenium: Historia i autorzy"
        assertEquals("Selenium: Historia i autorzy", driver.findElement(By.id("firstHeading")).getText());

        // 4. Przejście do strony głównej po kliknięciu na logo Wikipedia
        WebElement wikiLogo = driver.findElement(By.className("mw-logo-icon"));
        wikiLogo.click();

        // Asercja 8: Sprawdzenie, że użytkownik wrócił na stronę główną
        String returnHomeTitle = driver.getTitle();
        assertEquals("Wikipedia, wolna encyklopedia", returnHomeTitle);
    }
}
