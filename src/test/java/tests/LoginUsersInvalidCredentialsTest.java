package tests;
import base.BaseTest;  // 🧱 BaseTest nos da el WebDriver y el setup/teardown
import org.testng.Assert;
import org.testng.annotations.DataProvider; // 🔁 Permite ejecutar el mismo test con distintos datos
import org.testng.annotations.Test;
import pages.LoginPage; // 📄 Page Object del login

public class LoginUsersInvalidCredentialsTest extends BaseTest {
    // 👆 Heredamos de BaseTest para usar el driver sin crearlo aquí

    @DataProvider(name = "invalidLoginData")
    // 🧩 DataProvider: fuente de datos para el test // name = "blockedUsers" → nombre con el que el test lo va a usar
    public Object[][] invalidLoginData() {
        return new Object[][]{
                // Usuario inválido + password inválido
                {
                        "invalid_user",
                        "invalid_password",
                        //"Epic sadface: Username and password do not match any user in this services"
                        "Epic sadface: Username and password do not match any user in this service"
                },
                // Usuario válido pero password vacío
                {
                        "standard_user",
                        "",
                        "Epic sadface: Password is required"
                },
                // Usuario vacío pero password válido
                {
                        "",
                        "secret_sauce",
                        "Epic sadface: Username is required"
                }
        };
    }
    @Test(dataProvider = "invalidLoginData")
    public void loginShouldShowProperErrorMessage(
            String username,
            String password,
            String expectedErrorMessage
    ) {

        LoginPage loginPage = new LoginPage(driver);
        // 🧱 Creamos el Page Object del login
        loginPage.login(username, password);
        // ▶️ Intentamos hacer login con los datos del DataProvider
        // 🧠 Primero validamos que el error exista
        Assert.assertTrue(
                loginPage.isErrorVisible(),
                "❌ Se esperaba un mensaje de error pero no apareció"
        );
        // 🧠 Luego validamos que el mensaje sea el correcto
        Assert.assertEquals(
                loginPage.getErrorMessage(),
                expectedErrorMessage,
                "❌ El mensaje de error no es el esperado"
        );
    }
}