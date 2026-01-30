package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;

public class InventorySmokeTest extends BaseTest {

    @Test
    public void inventoryShouldLoadAfterLogin() {

        // 1) Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        // 2) ✅ Validar Inventory
        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(
                inventoryPage.isPageDisplayed(),
                "❌ No cargó Inventory (no se encontró el logo)"
        );

        // 3) Logout
        inventoryPage.logout();

        // 4) ✅ Validar regreso a Login
        Assert.assertTrue(
                loginPage.waitForLoginPage(),
                "❌ Logout no regresó a Login"
        );
    }
}
