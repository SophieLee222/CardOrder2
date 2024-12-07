import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class FormTest {
    public String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldRegisterMeeting(){
        String meetingDate = generateDate(4, "dd.MM.yyyy");

        Selenide.open("http://localhost:9999");
        $("[data-test-id='city']").setValue("Екатеринбург");
        $("[data-test-id='date']").setValue(meetingDate);
        $("[data-test-id='name']").setValue("Петин Василий");
        $("[data-test-id='phone']").setValue("+79765844572");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(Condition.text("Забронировать")).click();
        $("[data-test-id='notification']").should(Condition.visible, Duration.ofSeconds(15));


    }
}
