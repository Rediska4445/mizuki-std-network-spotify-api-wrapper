import me.API.Net;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.net.HttpURLConnection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class NetTest {

    private Net net;
    private HttpURLConnection mockConnection;

    @BeforeEach
    public void setup() {
        mockConnection = mock(HttpURLConnection.class);

        net = new Net() {
            @Override
            public HttpURLConnection openConnection(String url) {
                return mockConnection;
            }
        };
    }

    @Test
    public void testSendGETRequestReturnsResponse() throws Exception {
        String response = "Hello world";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(response.getBytes());
        when(mockConnection.getInputStream()).thenReturn(inputStream);

        doNothing().when(mockConnection).setRequestMethod("GET");
        doNothing().when(mockConnection).setUseCaches(false);

        String actual = net.sendGETRequest("http://example.com");
        assertEquals(response, actual);

        verify(mockConnection).setRequestMethod("GET");
        verify(mockConnection).setUseCaches(false);
        verify(mockConnection).getInputStream();
    }

    @Test
    public void testSendGETForFindRequestWithParams() throws Exception {
        String trackName = "test track";
        String response = "{\"result\": \"ok\"}";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(response.getBytes());
        when(mockConnection.getInputStream()).thenReturn(inputStream);
        doNothing().when(mockConnection).setRequestMethod("GET");
        doNothing().when(mockConnection).setUseCaches(false);

        String actual = net.sendGETForFindRequest(trackName);
        assertEquals(response, actual);
    }
}
