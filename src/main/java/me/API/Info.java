/*
*
* Created with help Spotify-API wrapper into https://dubolt.com/!
*
* 03.02.2025
*/

package me.API;

import me.API.Album.Art;
import me.API.Album.Track;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Info {
    public static final String ID_TYPE = "id";

    public static Info info = new Info();

    public Info() {
    }

    public String getSeedFromRequest(String req) {
        try {
            JSONArray itemSearched = getSearchedItems(req);

            Object idParser = new JSONParser().parse(itemSearched.get(0).toString());
            JSONObject idObject = (JSONObject) idParser;
            return idObject.get(ID_TYPE).toString();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public org.json.simple.JSONArray getSearchedItems(String req) {
        try {
            Object tracksParser = new JSONParser().parse(req);

            JSONObject tracksObject = (JSONObject) tracksParser;
            String tracksSearched = tracksObject.get("tracks").toString();

            Object itemsParser = new JSONParser().parse(tracksSearched);
            JSONObject itemsObject = (JSONObject) itemsParser;
            String itemsStringsSearched = itemsObject.get("items").toString();
            return (org.json.simple.JSONArray) new JSONParser().parse(itemsStringsSearched);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public Track[] getRawSimilarTracks(String seed, Params param) throws IOException, ParseException {
        String req = Net.netty.sendGETRequest((
                "https://dubolt.com/api/recommendations?" + URLEncoder.encode(
                        "limit=" + param.limit +
                                "&min_popularity=" + param.min_popularity +
                                "&max_popularity=" + param.max_popularity +
                                "&min_energy=" + param.min_energy +
                                "&max_energy=" + param.max_energy +
                                "&min_instrumentalness=" + param.min_instrumentalness +
                                "&max_instrumentalness=" + param.max_instrumentalness +
                                "&min_tempo=" + param.min_temp +
                                "&max_tempo=" + param.max_temp +
                                "&min_danceability=" + param.min_danceability +
                                "&max_danceability=" + param.max_danceability +
                                "&min_valence=" + param.min_valence +
                                "&max_valence=" + param.max_valence +
                                "&min_acousticness=" + param.min_acousticness +
                                "&max_acousticness=" + param.max_acousticness +
                                "&seed_artists=" +
                                "&seed_tracks=" + seed +
                                "&seed_genres=", StandardCharsets.UTF_8))
        );

        return getRawSimilarTracks(req);
    }

    public Track[] getRawSimilarTracks(String req) throws ParseException {
        StringBuilder artist = new StringBuilder();

        Track[] res;

        String tracks = parseObject(req, "tracks");

        org.json.simple.JSONArray itemSearched = (org.json.simple.JSONArray) new JSONParser().parse(tracks);

        res = new Track[itemSearched.size()];

        for (int i = 0; i < itemSearched.size(); i++) {
            org.json.simple.JSONArray ArtistsArr = getArray(parseObject(itemSearched.get(i).toString(), "artists"));

            JSONObject nameObject = getObject(itemSearched.get(i).toString());

            if (ArtistsArr.size() > 1) {
                for (Object o : ArtistsArr) {
                    artist.append(parseObject(o.toString(), "name")).append(",").append(" ");
                }

                artist.delete(artist.length() - 2, artist.length());
            } else {
                artist = new StringBuilder(getObject(ArtistsArr.get(0).toString()).get("name").toString());
            }

            Track track = new Track()
                    .setId(nameObject.get("name").toString())
                    .setTitle(nameObject.get("name").toString())
                    .setPopularity(Integer.parseInt(nameObject.get("popularity").toString()))
                    .setDuration(Integer.parseInt(nameObject.get("duration_ms").toString()))
                    .setExplicit(Boolean.parseBoolean(nameObject.get("explicit").toString()))
                    .setAuthor(artist.toString())
                    .setAlbumArt(getAlbumUrlsFromRawJson(nameObject));

            res[i] = track;

            artist.setLength(0);
        }

        return res;
    }

    private List<Art> getAlbumUrlsFromRawJson(JSONObject nameObject) throws ParseException {
        List<Art> arts = new ArrayList<>();

        String album = parseObject(nameObject.toString(), "album");
        String images = parseObject(album, "images");

        for(Object img : getArray(images)) {
            arts.add(new Art(parseObject(img.toString(), "url"))
                    .setHeight(Integer.parseInt(parseObject(img.toString(), "height")))
                    .setWidth(Integer.parseInt(parseObject(img.toString(), "width"))));
        }

        return arts;
    }

    private JSONArray getArray(String in) throws ParseException {
        Object albumParser = new JSONParser().parse(in);
        return (JSONArray) albumParser;
    }

    private JSONObject getObject(String in) throws ParseException {
        Object albumParser = new JSONParser().parse(in);
        return  (JSONObject) albumParser;
    }

    private String parseObject(String in, String key) throws ParseException {
        JSONObject albumObject = getObject(in);
        return albumObject.get(key).toString();
    }

    private String parseArray(String in, int key) throws ParseException {
        JSONArray albumObject = getArray(in);
        return albumObject.get(key).toString();
    }
}