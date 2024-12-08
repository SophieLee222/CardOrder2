import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.openqa.selenium.Keys.*;

public class FormTest {

    @BeforeEach
    void setup(){
        Selenide.open("http://localhost:9999");
    }

    public String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    public String generateNextMonthDate(int months, String pattern) {
        return LocalDate.now().plusMonths(months).format(DateTimeFormatter.ofPattern(pattern));
    }

    //не забыть про Shift Home

    @Test
    void shouldRegisterDelivery(){
        String deliveryDate = generateDate(4, "dd.MM.yyyy");

        $("[data-test-id='city'] input").setValue("Екатеринбург");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, HOME),Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Петин Василий");
        $("[data-test-id='phone'] input").setValue("+79765844572");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(Condition.text("Забронировать")).click();
        $("[data-test-id='notification']").should(Condition.visible,
                Duration.ofSeconds(15)).should(Condition.text("Встреча успешно забронирована на " + deliveryDate));
    }

    @Test
    void shouldChooseCityFromDropdown(){
        String deliveryDate = generateDate(4, "dd.MM.yyyy");

        $("[data-test-id='city'] input").setValue("Ек");
        $$(".menu-item").findBy(Condition.text("Екатеринбург")).click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(SHIFT, HOME),Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Петин Василий");
        $("[data-test-id='phone'] input").setValue("+79765844572");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(Condition.text("Забронировать")).click();
        $("[data-test-id='notification']").should(Condition.visible,
                Duration.ofSeconds(15)).should(Condition.text("Встреча успешно забронирована на " + deliveryDate));
    }

    @Test
    void shouldChooseDateFromDropdownCalendar(){
        String deliveryDate = generateDate(4, "d");
        String deliveryFullDate = generateDate(4, "d.MM.yyyy");

        $("[data-test-id='city'] input").setValue("Екатеринбург");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(SHIFT, HOME),Keys.BACK_SPACE);
        $("span.input__box span button").click();
        $$(".calendar__day").findBy(Condition.text(deliveryDate)).click();
        $("[data-test-id='name'] input").setValue("Петин Василий");
        $("[data-test-id='phone'] input").setValue("+79765844572");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(Condition.text("Забронировать")).click();
        $("[data-test-id='notification']").should(Condition.visible,
                Duration.ofSeconds(15)).should(Condition.text("Встреча успешно забронирована на " + deliveryFullDate));
    }

    @Test
    void shouldChooseDateFromDropdownCalendarIfNextMonth(){
        String deliveryDate = generateNextMonthDate(1, "d");
        String deliveryFullDate = generateNextMonthDate(1, "dd.MM.yyyy");


        $("[data-test-id='city'] input").setValue("Екатеринбург");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(SHIFT, HOME),Keys.BACK_SPACE);
        $("span.input__box span button").click();
        $(".calendar__arrow_direction_right[data-step='1'").click();
        $$(".calendar__day").findBy(Condition.text(deliveryDate)).click();
        $("[data-test-id='name'] input").setValue("Петин Василий");
        $("[data-test-id='phone'] input").setValue("+79765844572");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(Condition.text("Забронировать")).click();
        $("[data-test-id='notification']").should(Condition.visible,
                Duration.ofSeconds(15)).should(Condition.text("Встреча успешно забронирована на " + deliveryFullDate));
    }

}
