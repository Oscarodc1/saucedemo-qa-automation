package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

// ✅ SLF4J imports
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage {
    // 🪵 Logger específico de esta clase
    private static final Logger log = LoggerFactory.getLogger(LoginPage.class);

    // Guardamos el driver que viene de BaseTest (ya está creado y ya abrió la web)  // final → esta referencia no debe cambiar
    private final WebDriver driver;

    // Locators (cómo encontrar elementos en la UI) // Locators (no deben cambiar → final)
    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton   = By.id("login-button");
    private final By errorMsg      = By.cssSelector("[data-test='error']");

    // Constructor: recibimos el driver desde el test
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        log.info("[LoginPage] Página de Login inicializada");
    }

    // Acción: hacer login completo
    public void login(String username, String password) {
        log.info("[LoginPage] Intentando login con usuario: {}", username);

        driver.findElement(usernameInput).clear();
        driver.findElement(usernameInput).sendKeys(username);

        // ⚠️ Nunca loguear passwords reales
        log.info("[LoginPage] Ingresando password: ***");
        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(password);

        log.info("[LoginPage] Click en botón Login");
        driver.findElement(loginButton).click();
    }

    // Acción: leer el mensaje de error (cuando falla el login)
    public String getErrorMessage() {
        return driver.findElement(errorMsg).getText();
    }

    public boolean isErrorVisible() {
        return !driver.findElements(errorMsg).isEmpty();
    }

    // ✅ Valida login
    public boolean isLoginPageDisplayed() {
        return !driver.findElements(loginButton).isEmpty();
    }

    public boolean waitForLoginPage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(loginButton));
            return true;
        } catch (Exception e) {
            log.warn("[LoginPage] No apareció el login-button tras esperar", e);
            return false;
        }
    }
}
