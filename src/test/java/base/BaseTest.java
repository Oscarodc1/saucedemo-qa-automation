package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class BaseTest {

    protected WebDriver driver;

    // 🪵 Logger de BaseTest
    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);

    @BeforeMethod
    public void setUp() {

        // 🪵 Log de inicio del setup
        log.info("=== [SETUP] Iniciando navegador para el test ===");

        // ✅ WebDriverManager (como ya lo tienes)
        WebDriverManager.chromedriver().setup();

        // ✅ 1) ChromeOptions para BLOQUEAR el popup de Google / Password Manager
        ChromeOptions options = new ChromeOptions();

        // ✅ Preferencias internas de Chrome (desactiva guardado de credenciales)
        Map<String, Object> prefs = new HashMap<>();

        // 🔒 Desactiva el servicio de credenciales y el password manager
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);

        // 🔕 Bloquea notificaciones del navegador (por si aparece algún prompt)
        prefs.put("profile.default_content_setting_values.notifications", 2);

        // Asignamos prefs a Chrome
        options.setExperimentalOption("prefs", prefs);

        // ✅ 2) Argumentos útiles para evitar UI molesta (prompts/infobars)
        options.addArguments("--disable-notifications");   // refuerzo a notificaciones
        options.addArguments("--incognito");               // perfil limpio (menos popups)
        options.addArguments("--disable-infobars");        // menos banners
        options.addArguments("--start-maximized");

        // ⚠️ IMPORTANTÍSIMO:
        // NO uses "user-data-dir" apuntando a tu perfil real, porque trae contraseñas guardadas
        // y dispara el popup "Cambia la contraseña".
        // options.addArguments("user-data-dir=C:\\..."); // ❌ NO

        // ✅ 3) Crear driver con opciones
        driver = new ChromeDriver(options);

        log.info("[SETUP] Ventana maximizada");
        log.info("[SETUP] Navegando a SauceDemo: https://www.saucedemo.com/");
        driver.get("https://www.saucedemo.com/");
    }

    @AfterMethod
    public void tearDown() {
        log.info("=== [TEARDOWN] Cerrando navegador ===");

        if (driver != null) {
            driver.quit();
            log.info("[TEARDOWN] Driver cerrado correctamente");
        }
    }
}
