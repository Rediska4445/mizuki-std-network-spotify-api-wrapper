import me.API.Net;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NetTest {
    private Net net;

    @BeforeEach
    void setUp() {
        net = new Net();
    }

    @Test
    public void testSendGETRequestReturnsResponse()
            throws Exception
    {
        String actual = net.sendGETRequest("http://example.com");
        assertNotNull(actual);
        assertTrue(actual.length() > 1);
    }

    @Test
    public void testSendGETForFindRequestWithParams() throws Exception {
        String actual = net.sendGETForFindRequest("dvrst - dream space");
        assertTrue(actual.length() > 1);
    }
}
