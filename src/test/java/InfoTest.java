import me.API.Album.Track;
import me.API.Info;
import me.API.Net;
import me.API.Params;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InfoTest {

    private Info info;
    private Net netMock;

    @BeforeEach
    public void setup() {
        netMock = mock(Net.class);
        info = new Info();

        Net.netty = netMock;
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
    public void testGetRawSimilarTracks() throws IOException, ParseException {
        String jsonResponse = "{ \"tracks\": [ { " +
                "\"name\":\"track1\", \"popularity\": 10, \"duration_ms\": 2000, \"explicit\": false, " +
                "\"artists\": [ { \"name\": \"artist1\" } ], " +
                "\"album\": { \"images\": [ { \"url\": \"url1\", \"height\": 100, \"width\": 100 } ] } " +
                "} ] }";

        when(netMock.sendGETRequest(Mockito.anyString())).thenReturn(jsonResponse);

        Params params = new Params(1);
        params.min_popularity = 0; params.max_popularity = 100;
        params.min_energy = String.valueOf(0); params.max_energy = String.valueOf(100);
        params.min_instrumentalness = String.valueOf(0); params.max_instrumentalness = String.valueOf(100);
        params.min_temp = 0; params.max_temp = 200;
        params.min_danceability = String.valueOf(0); params.max_danceability = String.valueOf(100);
        params.min_valence = String.valueOf(0); params.max_valence = String.valueOf(100);
        params.min_acousticness = String.valueOf(0); params.max_acousticness = String.valueOf(100);

        Track[] tracks = info.getRawSimilarTracks("someSeed", params);

        assertEquals(1, tracks.length);
        assertEquals("track1", tracks[0].getTitle());
        assertEquals("artist1", tracks[0].getAuthor());
        assertFalse(tracks[0].isExplicit());
    }

    @Test
    public void testParseExceptionOnInvalidJson() {
        String badJson = "invalid json";

        Exception ex = assertThrows(RuntimeException.class, () -> info.getSeedFromRequest(badJson));
        assertTrue(ex.getCause() instanceof ParseException);
    }
}
