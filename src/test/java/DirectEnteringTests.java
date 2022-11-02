import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class DirectEnteringTests {

    String getDate(int offset, char divider) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd" + divider + "MM" + divider + "yyyy");
        return LocalDate.now().plusDays(offset).format(format);
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    //
    //  Two positive tests with valid simple and complex (divided by dash) city names
    //

    @Test
    void simpleCityName() {
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("Москва");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        String date = getDate(5, '.');
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Василий Степанов");
        form.$("[data-test-id='phone'] input").setValue("+79011234567");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        $("[data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__title").shouldBe(exactOwnText("Успешно!"));
        $("[data-test-id='notification'] .notification__content").shouldBe(exactOwnText("Встреча успешно забронирована на " + date));
    }

    @Test
    void complexCityName() {
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("Горно-Алтайск");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        String date = getDate(5, '.');
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Василий Степанов");
        form.$("[data-test-id='phone'] input").setValue("+79011234567");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        $("[data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__title").shouldBe(exactOwnText("Успешно!"));
        $("[data-test-id='notification'] .notification__content").shouldBe(exactOwnText("Встреча успешно забронирована на " + date));
    }

    //
    //  Two positive tests with date divider as dash and slash
    //

    @Test
    void dateDividerIsDash() {
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("Москва");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        String date = getDate(5, '-');
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Василий Степанов");
        form.$("[data-test-id='phone'] input").setValue("+79011234567");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        $("[data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__title").shouldBe(exactOwnText("Успешно!"));
        $("[data-test-id='notification'] .notification__content").shouldBe(exactOwnText("Встреча успешно забронирована на " + getDate(5, '.')));
    }

    @Test
    void dateDividerIsSlash() {
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("Москва");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        String date = getDate(5, '/');
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Василий Степанов");
        form.$("[data-test-id='phone'] input").setValue("+79011234567");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        $("[data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__title").shouldBe(exactOwnText("Успешно!"));
        $("[data-test-id='notification'] .notification__content").shouldBe(exactOwnText("Встреча успешно забронирована на " + getDate(5, '.')));
    }

    //
    //  Positive test with complex last name divided by dash
    //

    @Test
    void complexLastName() {
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("Москва");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        String date = getDate(5, '.');
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Василий Степанов-Архипов");
        form.$("[data-test-id='phone'] input").setValue("+79011234567");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        $("[data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__title").shouldBe(exactOwnText("Успешно!"));
        $("[data-test-id='notification'] .notification__content").shouldBe(exactOwnText("Встреча успешно забронирована на " + date));
    }

    //
    //  Negative tests for "City Name" field
    //

    @Test
    void emptyCityField() {
        SelenideElement form = $("form");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        String date = getDate(5, '.');
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Василий Степанов");
        form.$("[data-test-id='phone'] input").setValue("+79011234567");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        form.$("[data-test-id='city'].input_invalid .input__sub").shouldBe(exactOwnText("Поле обязательно для заполнения"));
    }

    @Test
    void cityNotFromAllowedList() {
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("Среднеуральск");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        String date = getDate(5, '.');
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Василий Степанов");
        form.$("[data-test-id='phone'] input").setValue("+79011234567");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        form.$("[data-test-id='city'].input_invalid .input__sub").shouldBe(exactOwnText("Доставка в выбранный город недоступна"));
    }

    @Test
    void validCityNameWithSpecialSymbols() {
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("!Москва");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        String date = getDate(5, '.');
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Василий Степанов");
        form.$("[data-test-id='phone'] input").setValue("+79011234567");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        form.$("[data-test-id='city'].input_invalid .input__sub").shouldBe(exactOwnText("Доставка в выбранный город недоступна"));
    }

    @Test
    void digitsInsteadOfString() {
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("1234567890");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        String date = getDate(5, '.');
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Василий Степанов");
        form.$("[data-test-id='phone'] input").setValue("+79011234567");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        form.$("[data-test-id='city'].input_invalid .input__sub").shouldBe(exactOwnText("Доставка в выбранный город недоступна"));
    }

    @Test
    void cityInLatinChars() {
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("Moscow");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        String date = getDate(5, '.');
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Василий Степанов");
        form.$("[data-test-id='phone'] input").setValue("+79011234567");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        form.$("[data-test-id='city'].input_invalid .input__sub").shouldBe(exactOwnText("Доставка в выбранный город недоступна"));
    }

    //
    //  Negative tests for "Date" field
    //

    @Test
    void emptyDateField() {
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("Москва");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        form.$("[data-test-id='name'] input").setValue("Василий Степанов");
        form.$("[data-test-id='phone'] input").setValue("+79011234567");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        form.$("[data-test-id='date'] .input_invalid .input__sub").shouldBe(exactOwnText("Неверно введена дата"));
    }

    @Test
    void dateInThePast() {
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("Москва");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        String date = getDate(-3, '.');
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Василий Степанов");
        form.$("[data-test-id='phone'] input").setValue("+79011234567");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        form.$("[data-test-id='date'] .input_invalid .input__sub").shouldBe(exactOwnText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void dateIsToday() {
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("Москва");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        String date = getDate(0, '.');
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Василий Степанов");
        form.$("[data-test-id='phone'] input").setValue("+79011234567");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        form.$("[data-test-id='date'] .input_invalid .input__sub").shouldBe(exactOwnText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void dateIsLessThanThreeDays() {
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("Москва");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        String date = getDate(2, '.');
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Василий Степанов");
        form.$("[data-test-id='phone'] input").setValue("+79011234567");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        form.$("[data-test-id='date'] .input_invalid .input__sub").shouldBe(exactOwnText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void latinSymbolInsteadOfDate() {
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("Москва");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        form.$("[data-test-id='date'] input").setValue("dD");
        form.$("[data-test-id='name'] input").setValue("Василий Степанов");
        form.$("[data-test-id='phone'] input").setValue("+79011234567");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        form.$("[data-test-id='date'] .input_invalid .input__sub").shouldBe(exactOwnText("Неверно введена дата"));
    }

    @Test
    void cyrillicSymbolInsteadOfDate() {
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("Москва");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        form.$("[data-test-id='date'] input").setValue("фФ");
        form.$("[data-test-id='name'] input").setValue("Василий Степанов");
        form.$("[data-test-id='phone'] input").setValue("+79011234567");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        form.$("[data-test-id='date'] .input_invalid .input__sub").shouldBe(exactOwnText("Неверно введена дата"));
    }

    @Test
    void specialSymbolInsteadOfDate() {
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("Москва");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        form.$("[data-test-id='date'] input").setValue("@#");
        form.$("[data-test-id='name'] input").setValue("Василий Степанов");
        form.$("[data-test-id='phone'] input").setValue("+79011234567");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        form.$("[data-test-id='date'] .input_invalid .input__sub").shouldBe(exactOwnText("Неверно введена дата"));
    }

    @Test
    void datePresentedByOneDigit() {
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("Москва");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        form.$("[data-test-id='date'] input").setValue("13");
        form.$("[data-test-id='name'] input").setValue("Василий Степанов");
        form.$("[data-test-id='phone'] input").setValue("+79011234567");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        form.$("[data-test-id='date'] .input_invalid .input__sub").shouldBe(exactOwnText("Неверно введена дата"));
    }

    @Test
    void datePresentedByFiveDigit() {
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("Москва");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        form.$("[data-test-id='date'] input").setValue("13.04.197");
        form.$("[data-test-id='name'] input").setValue("Василий Степанов");
        form.$("[data-test-id='phone'] input").setValue("+79011234567");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        form.$("[data-test-id='date'] .input_invalid .input__sub").shouldBe(exactOwnText("Неверно введена дата"));
    }

    //
    //  Negative tests for "Name" field
    //

    @Test
    void emptyNameField() {
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("Москва");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        String date = getDate(5, '.');
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='phone'] input").setValue("+79011234567");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        form.$("[data-test-id='name'].input_invalid .input__sub").shouldBe(exactOwnText("Поле обязательно для заполнения"));
    }

    @Test
    void latinSymbol() {
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("Москва");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        String date = getDate(5, '.');
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Vasiliy Stepanov");
        form.$("[data-test-id='phone'] input").setValue("+79011234567");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        form.$("[data-test-id='name'].input_invalid .input__sub").shouldBe(exactOwnText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void nameContainsDigits() {
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("Москва");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        String date = getDate(5, '.');
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Василий 15-й");
        form.$("[data-test-id='phone'] input").setValue("+79011234567");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        form.$("[data-test-id='name'].input_invalid .input__sub").shouldBe(exactOwnText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void nameContainsSpecialSymbols() {
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("Москва");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        String date = getDate(5, '.');
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Ва$$илий Степ@нов");
        form.$("[data-test-id='phone'] input").setValue("+79011234567");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        form.$("[data-test-id='name'].input_invalid .input__sub").shouldBe(exactOwnText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    //
    //  Negative tests for "Phone" field
    //

    @Test
    void emptyPhoneField() {
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("Москва");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        String date = getDate(5, '.');
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Василий Степанов");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        form.$("[data-test-id='phone'].input_invalid .input__sub").shouldBe(exactOwnText("Поле обязательно для заполнения"));
    }

    @Test
    void validNumberWithoutLeadingPlus() {
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("Москва");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        String date = getDate(5, '.');
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Василий Степанов");
        form.$("[data-test-id='phone'] input").setValue("79011234567");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        form.$("[data-test-id='phone'].input_invalid .input__sub").shouldBe(exactOwnText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void validNumberWithTrailingPlus() {
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("Москва");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        String date = getDate(5, '.');
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Василий Степанов");
        form.$("[data-test-id='phone'] input").setValue("79011234567+");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        form.$("[data-test-id='phone'].input_invalid .input__sub").shouldBe(exactOwnText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void invalidNumberConsistsOfOneDigit() {
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("Москва");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        String date = getDate(5, '.');
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Василий Степанов");
        form.$("[data-test-id='phone'] input").setValue("+7");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        form.$("[data-test-id='phone'].input_invalid .input__sub").shouldBe(exactOwnText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void invalidNumberWithLengthLessThanRequired() {
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("Москва");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        String date = getDate(5, '.');
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Василий Степанов");
        form.$("[data-test-id='phone'] input").setValue("+7901123456");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        form.$("[data-test-id='phone'].input_invalid .input__sub").shouldBe(exactOwnText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void invalidNumberWithLengthMoreThanRequired() {
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("Москва");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        String date = getDate(5, '.');
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Василий Степанов");
        form.$("[data-test-id='phone'] input").setValue("+790112345678");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        form.$("[data-test-id='phone'].input_invalid .input__sub").shouldBe(exactOwnText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void validNumberWithBraces() {
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("Москва");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        String date = getDate(5, '.');
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Василий Степанов");
        form.$("[data-test-id='phone'] input").setValue("+7(901)1234567");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        form.$("[data-test-id='phone'].input_invalid .input__sub").shouldBe(exactOwnText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void validNumberDividedByDash() {
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("Москва");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        String date = getDate(5, '.');
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Василий Степанов");
        form.$("[data-test-id='phone'] input").setValue("+7-901-123-45-67");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        form.$("[data-test-id='phone'].input_invalid .input__sub").shouldBe(exactOwnText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void numberContainsCyrillicSymbols() {
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("Москва");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        String date = getDate(5, '.');
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Василий Степанов");
        form.$("[data-test-id='phone'] input").setValue("+790112Ф4567");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        form.$("[data-test-id='phone'].input_invalid .input__sub").shouldBe(exactOwnText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void numberContainsLatinSymbols() {
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("Москва");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        String date = getDate(5, '.');
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Василий Степанов");
        form.$("[data-test-id='phone'] input").setValue("+790112F4567");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        form.$("[data-test-id='phone'].input_invalid .input__sub").shouldBe(exactOwnText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void numberContainsSpecialSymbols() {
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("Москва");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        String date = getDate(5, '.');
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Василий Степанов");
        form.$("[data-test-id='phone'] input").setValue("+7901123456#");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        form.$("[data-test-id='phone'].input_invalid .input__sub").shouldBe(exactOwnText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    //
    //  Negative tests for CheckBox
    //

    @Test
    void emptyCheckBox() {
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("Москва");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        String date = getDate(5, '.');
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Василий Степанов");
        form.$("[data-test-id='phone'] input").setValue("+79011234567");
        form.$("button.button").click();
        form.$("[data-test-id='agreement']").shouldHave(cssClass("input_invalid"));
    }
}