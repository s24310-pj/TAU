package pl.pjatk.zjazd2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GitHubTest extends BaseTest {

    @BeforeEach
    public void setUp() {
        setupBrowser("firefox");
    }

    @Test
    public void testGitHubNavigationAndAssertions() {
        // 1. Otwarcie strony głównej GitHub
        driver.get("https://github.com");

        // Asercja 1: Sprawdzenie tytułu strony głównej
        String homeTitle = driver.getTitle();
        assertEquals("GitHub · Build and ship software on a single, collaborative platform · GitHub", homeTitle);

        // Asercja 2: Sprawdzenie widoczności logo GitHub na stronie głównej
        WebElement logo = driver.findElement(By.cssSelector("a[href='/']"));
        assertTrue(logo.isDisplayed());

        // Asercja 3: Sprawdzenie, że link do „Pricing” jest dostępny na stronie głównej
        WebElement pricingLink = driver.findElement(By.linkText("Pricing"));
        assertTrue(pricingLink.isDisplayed());

        // 2. Przejście na podstronę "Pricing"
        pricingLink.click();

        // Asercja 4: Sprawdzenie tytułu strony "Pricing"
        String pricingTitle = driver.getTitle();
        assertEquals("Pricing · Plans for every developer · GitHub", pricingTitle);

        // Asercja 5: Sprawdzenie widoczności logo GitHub na stronie "Pricing"
        WebElement pricingLogo = driver.findElement(By.cssSelector("a[href='/']"));
        assertTrue(pricingLogo.isDisplayed());

        // Asercja 6: Sprawdzenie, że widoczna jest sekcja „Compare all features”
        WebElement compareAllFeatures = driver.findElement(By.xpath("//a[contains(text(), 'Compare all features')]"));
        assertTrue(compareAllFeatures.isDisplayed());

        // Asercja 7: Sprawdzenie obecności przycisku „Join for free” w sekcji Pricing
        WebElement joinForFreeButton = driver.findElement(By.xpath("//a[contains(text(), 'Join for free')]"));
        assertTrue(joinForFreeButton.isDisplayed());

        // Asercja 8: Sprawdzenie, że na stronie "Pricing" znajduje się link do sklepu
        WebElement shopLink = driver.findElement(By.linkText("Shop"));
        assertTrue(shopLink.isDisplayed());
    }
}
