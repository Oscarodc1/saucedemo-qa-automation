package listeners;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import io.qameta.allure.Allure;


public class ScreenshotListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) { // 🧪 Este método se ejecuta automáticamente SOLO cuando un test falla

        // 1) Intentamos obtener el driver del test que falló
        WebDriver driver = getDriverFromTestInstance(result); //“Dame el objeto del test que acaba de fallar”

        if (driver == null) {
            // Si no logramos obtener el driver, no podemos sacar screenshot
            System.out.println("⚠️ No se pudo obtener WebDriver para screenshot en: " + result.getName());
            return;
        }

        try {
            // 2) Tomamos el screenshot
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // 3) Construimos nombre y ruta (queda dentro de target/)
            String testName = result.getName();
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            Path screenshotsDir = Path.of("target", "screenshots"); //Guardarlo en target/screenshots 🧠 Crea la carpeta si no existe.
            Path destination = screenshotsDir.resolve(testName + "_" + timestamp + ".png");//🧠 El nombre del archivo incluye: nombre del test + fecha/hora para que no se sobreescriban.

            // 4) Creamos carpeta si no existe
            Files.createDirectories(screenshotsDir);

            // 5) Copiamos el archivo temporal al destino final
            Files.copy(screenshotFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

            // 📎 Adjuntamos el screenshot a Allure
            Allure.addAttachment(

                    "Screenshot on failure",
                    Files.newInputStream(destination)
            );

            System.out.println("📸 Screenshot guardado en: " + destination.toAbsolutePath());

        } catch (Exception e) {
            System.out.println("❌ Error tomando screenshot en fallo: " + e.getMessage());
        }
    }

    private WebDriver getDriverFromTestInstance(ITestResult result) {
        // 🎯 Objetivo: obtener el WebDriver desde la instancia del test (que extiende BaseTest)
        // Como tus tests usan "driver" directamente (viene de BaseTest),


        Object testInstance = result.getInstance();
        if (testInstance == null) return null;

        try {
            // Busca un campo llamado "driver" en la clase o superclase “Busca un atributo llamado driver en esa clase o en su padre (BaseTest)”
            Field driverField = findField(testInstance.getClass(), "driver");
            if (driverField == null) return null;

            driverField.setAccessible(true);  // “Permíteme leer ese campo aunque sea protected”
            return (WebDriver) driverField.get(testInstance); //“Devuélveme el valor del driver”

        } catch (Exception e) {
            return null;
        }
    }

    private Field findField(Class<?> clazz, String fieldName) {
        // 🔎 Busca el campo en la clase y luego va subiendo a la superclase (BaseTest)
        Class<?> current = clazz;

        while (current != null) {
            try {
                return current.getDeclaredField(fieldName);
            } catch (NoSuchFieldException ignored) {
                current = current.getSuperclass();
            }
        }
        return null;
    }
}
