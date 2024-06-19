package app;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.lnc.app.Route;
import org.junit.jupiter.api.Test;

public class TestRoute {
  @Test
  public void testInvalidRedirectPath() {
    Route route = new Route();

    assertThrows(IllegalArgumentException.class, () -> {
      route.redirect("/notActualPath&data");
    });
  }
}
