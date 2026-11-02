package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

class CardDeliveryTest {
    private static final String BASE_URL = "http://localhost:9999";
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @BeforeEach
    void setUp() {
        open(BASE_URL);
    }

    @Test
    void shouldSendCardDeliveryFormSuccessfully() {
        // 1. Дата встречи: не ранее трёх дней с текущей даты.
        String meetingDate = LocalDate.now().plusDays(3).format(DATE_FORMATTER);
        // 2. Город — один из административных центров РФ.
        // Прямой ввод без выбора из выпадающего списка.
        $("[data-test-id='city'] input").setValue("Москва");
        // 3. Поле даты предзаполнено. Метод clear() на таких формах не работает —
        // повторяем действия пользователя: выделяем текст и удаляем его.
        SelenideElement dateField = $("[data-test-id='date'] input");
        dateField.doubleClick(); // выделяем всё содержимое поля
        dateField.sendKeys(Keys.DELETE); // удаляем выделенный текст
        dateField.setValue(meetingDate);
        // 4. Фамилия и имя — только русские буквы, дефисы и пробелы.
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        // 5. Телефон — 11 цифр, символ + на первом месте.
        $("[data-test-id='phone'] input").setValue("+79991234567");
        // 6. Флажок согласия должен быть выставлен.
        $("[data-test-id='agreement']").click();
        // 7. Отправляем форму.
        $$("button").find(exactText("Забронировать")).click();
        // 8. Состояние загрузки не должно длиться более 15 секунд.
        // После успешной отправки формы появится всплывающее окно
        // об успешном завершении бронирования.
        $("[data-test-id='notification']")
                .shouldBe(visible, Duration.ofSeconds(15));
        // 9. Проверяем заголовок и текст уведомления.
        $("[data-test-id='notification'] .notification__title")
                .shouldHave(exactText("Успешно!"));
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(exactText("Встреча успешно забронирована на " + meetingDate));
    }
}