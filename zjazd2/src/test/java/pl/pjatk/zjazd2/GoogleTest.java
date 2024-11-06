package pl.pjatk.zjazd2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GoogleTest extends BaseTest {

    @BeforeEach
    public void setUp() {
        setupBrowser("chrome");
    }

    @Test
    public void testGoogleSearchandShop() {
        // 1. Otwarcie strony Google
        driver.get("https://www.google.com");

        // Asercja 1 - Sprawdza czy na stronie jest dostępny przycisk odrzucenia ciasteczek
        WebElement rejectButton = driver.findElement(By.id("W0wltc"));
        assertTrue(rejectButton.isDisplayed());

        // 2. Kliknięcie przycisku odrzucenia ciasteczek
        rejectButton.click();

        // Asercja 2 - sprawdza czy tytuł strony to "Google"
        String homeTitle = driver.getTitle();
        assertEquals("Google", homeTitle);

        // Asercja 3 - sprawdza czy na stronie dostępny jest link z tekstem "Google Store"
        WebElement storeLink = driver.findElement(By.linkText("Google Store"));
        assertTrue(storeLink.isDisplayed());

        // 3. Przejście na stronę Google Store
        storeLink.click();

        // Asercja 4 - sprawdza czy strona zawiera link gdzie część tekstu to "Telefony"
        WebElement phones = driver.findElement(By.partialLinkText("Telefony"));
        assertTrue(phones.isDisplayed());

        // 4. Powrót na stronę
        driver.navigate().back();

        // Asercja 5 - sprawdza ponownie czy link tytuł to google (czy jesteśmy na dobrej stronie po cofnięciu)
        String homeTitle2 = driver.getTitle();
        assertEquals("Google", homeTitle2);

        // 5. Wpisanie oraz wyszukanie "pjatk"
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("pjatk");
        searchBox.submit();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("pjatk"));

        // Asercja 6 - Sprawdza czy na stronie jest obecny link do grafiki
        WebElement photos = driver.findElement(By.linkText("Grafika"));
        assertTrue(photos.isDisplayed());

        // 6. Przejście do zakładki z grafiką
        photos.click();

        //Asercja 7 - Sprawdza czy na stronie jest przycisk "zaloguj się"
        WebElement login = driver.findElement(By.linkText("Zaloguj się"));
        assertTrue(login.isDisplayed());

        //Asercja 8 - Sprawdza czy na stronie jest element o takiej nazwie klasy - sekcja "Podobne wyszukiwania"
        WebElement logoPjatk = driver.findElement(By.className("BA0zte"));
        assertTrue(logoPjatk.isDisplayed());
    }
}
