import com.fasterxml.jackson.databind.ObjectMapper;
import model.CarModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class DataModelTest {

    ClassLoader cl = DataModelTest.class.getClassLoader();

    @Test
    void verifyJsonContent() throws Exception {
        try (InputStream stream = cl.getResourceAsStream("test.json");
             Reader reader = new InputStreamReader(stream)) {
            ObjectMapper mapper = new ObjectMapper();
            CarModel car = mapper.readValue(reader, CarModel.class);

            Assertions.assertTrue(car.isNew());
            Assertions.assertEquals(25000, car.getPrice());
            Assertions.assertEquals("Mazda", car.getName());
            Assertions.assertEquals("2.5L", car.getEngine().getType());
            Assertions.assertArrayEquals(new String[]{"metallic paint", "winter tires"}, car.getOptions());
        }
    }
}
