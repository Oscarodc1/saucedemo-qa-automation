package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;

public class LoginUsersSuccessTest extends BaseTest {

    @DataProvider(name = "validUsers")
    public Object[][] validUsers() {
        return new Object[][]{
                {"standard_user", "secret_sauce"},
                {"problem_user", "secret_sauce"},
                {"performance_glitch_user", "secret_sauce"},
                {"error_user", "secret_sauce"},
                {"visual_user", "secret_sauce"}
        };
    }

    @Test(dataProvider = "validUsers")
    public void loginShouldSucceedForValidUsers(String username, String password) {

        // 1) Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        // 2) ✅ Validar que NO hubo error de login
        Assert.assertFalse(
                loginPage.isErrorVisible(),
                "❌ Apareció error en login para el usuario: " + username
        );

        // 3) ✅ Validar Inventory (estás logueado)
        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(
                inventoryPage.isPageDisplayed(),
                "❌ No cargó Inventory para el usuario: " + username
        );

        // 4) Logout (limpieza por iteración)
        inventoryPage.logout();

        // 5) ✅ Validar regreso a Login (AHORA sí)
        Assert.assertTrue(
                loginPage.waitForLoginPage(),
                "❌ Después de logout no regresó a Login para el usuario: " + username
        );
    }
}
