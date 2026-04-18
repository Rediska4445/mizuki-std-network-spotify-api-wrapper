import me.API.Info;
import me.API.Net;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import static org.junit.jupiter.api.Assertions.*;

public class InfoTest {

    private Info info;

    @BeforeEach
    public void setup() {
        info = new Info();
    }

    @Test
    public void testGetSeedFromRequest() throws ParseException {
        String json = "{ \"tracks\": { \"items\": [ { \"id\": \"12345\" } ] } }";

        String seed = info.getSeedFromRequest(json);
        assertEquals("12345", seed);
    }

    @Test
    public void testGetSearchedItems() {
        String json = "{ \"tracks\": { \"items\": [ { \"id\": \"1\" }, { \"id\": \"2\" } ] } }";

        JSONArray items = info.getSearchedItems(json);
        assertEquals(2, items.size());
    }

    @Test
    public void testParseExceptionOnInvalidJson() {
        String badJson = "invalid json";

        Exception ex = assertThrows(RuntimeException.class, () -> info.getSeedFromRequest(badJson));
        assertTrue(ex.getCause() instanceof ParseException);
    }
}
