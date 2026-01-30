package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
// ✅ SLF4J imports
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InventoryPage {
    // 🪵 Logger específico de esta clase
    private static final Logger log = LoggerFactory.getLogger(InventoryPage.class);

    // Guardamos el driver que viene de BaseTest (ya está creado y ya abrió la web)// final → el driver no debe cambiar durante la vida del Page Object
    private final WebDriver driver;
    // Locators (cómo encontrar elementos en la UI)
    private final By appLogo    = By.cssSelector(".app_logo");
    private final By menuButton = By.id("react-burger-menu-btn");
    private final By logoutLink = By.id("logout_sidebar_link");

    // Constructor: recibimos el driver desde el test
    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        log.info("[InventoryPage] Página Inventory inicializada");
    }
    // Validar que el login fue exitoso
    public boolean isPageDisplayed() {
        return !driver.findElements(appLogo).isEmpty();
    }

    // Acción de negocio: logout
    public void logout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(menuButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
    }
}
