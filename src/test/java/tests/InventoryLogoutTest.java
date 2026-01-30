package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;

// ✅ Si quieres logs en el test, usa tu propio logger (no el de BaseTest)
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InventoryLogoutTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(InventoryLogoutTest.class);

    @Test
    public void logoutShouldReturnToLoginPage() {

        log.info("Escenario: validar logout y retorno a Login");

        // 1) Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        // 2) Logout desde Inventory
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.logout();

        // 3) ✅ Validar que ya estás en Login (espera explícita)
        Assert.assertTrue(
                loginPage.waitForLoginPage(),
                "❌ Después de logout no regresó a Login"
        );
    }
}
