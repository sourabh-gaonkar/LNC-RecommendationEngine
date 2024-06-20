package service.chef;

import com.lnc.service.chef.RolloutMenu;
import org.junit.jupiter.api.Test;

public class TestRolloutMenu {
    @Test
    public void testInvalidItem(){
        String jsonData = "{\"breakfastItems\":[\"Imaginary Item\",\"Kimchi Bokkeumbap\",\"Idli\"],\"lunchItems\":[\"palak Paneer\",\"japchae\",\"mapo tofu\",\"pad thai\"],\"snackItems\":[\"onigiri\",\"samosa\",\"gyoza\",\"pajeon\"],\"dinnerItems\":[\"butter chicken\",\"teriyaki salmon\",\"green curry\",\"khadai mashroom\"]}";

        RolloutMenu rolloutMenu = new RolloutMenu();
        String result = rolloutMenu.rolloutMenu(jsonData);
        System.out.println(result);
    }
}
