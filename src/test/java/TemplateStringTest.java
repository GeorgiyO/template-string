import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TemplateStringTest {

  @Test
  void format() {
    var template = new TemplateString(
      """
      email to ${recipient.email} from ${sender.email}
      Hello, ${recipient.name}
      It's a letter about my cat ${cat.name}, today he's grow up, and he's ${cat.age} y.o. now!
      With regards, ${sender.name}!
      """
    );
    var result = template.format(
      Map.of(
        "recipient", Map.of("name", "Neko",
                            "email", "neko@mail.ru"),
        "sender", Map.of("name", "Gochan",
                         "email", "gochan@mail.ru"),
        "cat", Map.of("name", "Tomas",
                      "age", "4")
      )
    );
    assertEquals(
      """
      email to neko@mail.ru from gochan@mail.ru
      Hello, Neko
      It's a letter about my cat Tomas, today he's grow up, and he's 4 y.o. now!
      With regards, Gochan!
      """,
      result
    );
  }
}