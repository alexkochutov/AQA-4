import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.selector.ByTagAndText;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.exactOwnText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class UsingHelpersTests {
    String[] availableLetters = {
            "Аб", "Ан", "Ар", "Ас", "Ба", "Бе", "Би", "Бл", "Бр", "Ве", "Вл", "Во", "Га", "Го","Гр",
            "Ек", "Ив", "Иж", "Ир", "Йо", "Ка", "Ке", "Ки", "Ко", "Кр", "Ку", "Кы", "Ли", "Ма", "Мо",
            "Му", "На", "Ни", "Но", "Ом", "Ор", "Пе", "Пс", "Ро", "Ря", "Са", "Се", "Си", "См", "Ст",
            "Сы", "Та", "Тв", "То", "Ту", "Тю", "Ул", "Уф", "Ха", "Че", "Чи", "Эл", "Юж", "Як", "Яр"
    };

    public int getRandom(int max) {
        return (int) (Math.random()*max);
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Test
    void useHelpers() {
        open("http://localhost:9999");
        SelenideElement form = $("form");

        // Send into the form two first randomly chosen symbols of available citi names
        form.$("[data-test-id='city'] input").sendKeys(availableLetters[getRandom(availableLetters.length)]);

        // Get list of available cities meeting requirement
        SelenideElement optionList = $(".popup_visible .menu");

        // Wait for popup list of cities become visible
        optionList.shouldBe(visible);

        // Click on random chosen item
        optionList.find(".menu-item", getRandom(optionList.findAll(By.className("menu-item")).size())).click();

        // Remove default value of "Date" field
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);

        // Click on calendar icon for picking another date
        form.$("[data-test-id='date'] button").click();

        // Get list of available cities meeting requirement
        SelenideElement calendar = $(".calendar-input__calendar-wrapper");

        // Wait for date picker become visible
        calendar.shouldBe(visible);

        // Set new date (one week forward)
        LocalDate currentDate = LocalDate.now();
        LocalDate targetDate = LocalDate.now().plusDays(7);

        if (currentDate.getMonthValue() != targetDate.getMonthValue()) {
            calendar.$("[data-step='1'].calendar__arrow_direction_right").click();
        }
        calendar.$(new ByTagAndText("td", "" + targetDate.getDayOfMonth())).click();

        // Fill all the other field
        form.$("[data-test-id='name'] input").setValue("Василий Степанов");
        form.$("[data-test-id='phone'] input").setValue("+79011234567");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();

        // Wait for confirmation
        $("[data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__title").shouldBe(exactOwnText("Успешно!"));
        $("[data-test-id='notification'] .notification__content").shouldBe(exactOwnText("Встреча успешно забронирована на " + targetDate.format(formatter)));
    }
}