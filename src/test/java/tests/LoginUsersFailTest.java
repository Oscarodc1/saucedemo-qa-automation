package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginUsersFailTest extends BaseTest {

    @DataProvider(name = "blockedUsers")
    public Object[][] blockedUsers() {
        return new Object[][]{
                {"locked_out_user", "secret_sauce", "Epic sadface: Sorry, this user has been locked out."}
        };
    }

    @Test(dataProvider = "blockedUsers")
    public void loginShouldShowProperErrorMessage(String username, String password, String expectedErrorMessage) {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        Assert.assertTrue(
                loginPage.isErrorVisible(),
                "❌ Se esperaba un mensaje de error pero no apareció"
        );

        Assert.assertEquals(
                loginPage.getErrorMessage(),
                expectedErrorMessage,
                "❌ El mensaje de error no es el esperado para usuario bloqueado"
        );
    }
}
