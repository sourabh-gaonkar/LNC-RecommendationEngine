package app;

import org.junit.jupiter.api.Test;

import com.lnc.app.Route;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestRoute {
  @Test
  public void testInvalidRedirectPath() {
    Route route = new Route();

    assertThrows(IllegalArgumentException.class, () -> {
      route.redirect("/notActualPath&data");
    });
  }
}
