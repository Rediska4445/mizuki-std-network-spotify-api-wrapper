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

/**
 * Класс Info предоставляет методы для работы с API Spotify через сервис dubolt.com,
 * обеспечивает парсинг JSON-ответов и формирование объектов Track для рекомендаций.
 * <p>
 * Синглтон экземпляр доступен через {@link #info}.
 * <p>
 * Основные методы:
 * <ul>
 *     <li>{@link #getSeedFromRequest(String)} — извлечение seed ID из JSON-ответа поискового запроса.</li>
 *     <li>{@link #getSearchedItems(String)} — получение массива JSON-объектов треков из JSON-строки.</li>
 *     <li>{@link #getRawSimilarTracks(String, Params)} — запрос рекомендаций по seed с указанными параметрами {@link Params}.</li>
 *     <li>{@link #getRawSimilarTracks(String)} — парсинг JSON-строки с треками в массив объектов {@link Track}.</li>
 * </ul>
 * <p>
 * Вспомогательные методы служат для удобного парсинга JSON-объектов и массивов, а также для извлечения информации об альбомных изображениях.
 *
 * @author Ebanina Std.
 * @version 1.0
 * @since 1.0
 */
public class Info {
    /**
     * Ключ для поиска ID в JSON объектах.
     */
    public static final String ID_TYPE = "id";
    /**
     * Синглтон-экземпляр класса для удобного вызова из других частей программы.
     */
    public static Info info = new Info();

    /**
     * Публичный конструктор.
     */
    public Info() {
    }

    /**
     * Извлекает ID seed из JSON-ответа поискового запроса.
     *
     * @param req JSON строка с ответом на поисковый запрос.
     * @return ID seed в виде строки.
     * @throws RuntimeException при ошибках парсинга JSON.
     */
    public String getSeedFromRequest(String req) throws ParseException {
        return parseObject(getSearchedItems(req).get(0).toString(), ID_TYPE);
    }

    /**
     * Извлекает массив найденных JSON треков из строки с полным ответом поискового запроса.
     *
     * @param req JSON строка с ответом.
     * @return Массив JSON объектов tracks.
     * @throws RuntimeException при ошибках парсинга JSON.
     */
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

    /**
     * Выполняет запрос рекомендаций Spotify по заданному seed и параметрам.
     *
     * @param seed  Seed ID трека.
     * @param param Параметры рекомендаций {@link Params}.
     * @return Массив объектов Track с рекомендованными треками.
     * @throws IOException    ошибка при сетевом запросе.
     * @throws ParseException ошибка парсинга JSON.
     */
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

    /**
     * Парсит JSON строку с треками в массив объектов Track.
     *
     * @param req JSON строка с треками.
     * @return Массив Track.
     * @throws ParseException ошибка парсинга JSON.
     */
    public Track[] getRawSimilarTracks(String req) throws ParseException {
        Track[] res;

        String tracks = parseObject(req, "tracks");

        org.json.simple.JSONArray itemSearched = (org.json.simple.JSONArray) new JSONParser().parse(tracks);

        res = new Track[itemSearched.size()];

        for (int i = 0; i < itemSearched.size(); i++) {
            res[i] = parseTrack(itemSearched.get(i).toString());
        }

        return res;
    }

    public Track[] getSimilarTracks(String viewName, int limit) throws ParseException, IOException {
        String searchResponse = Net.netty.sendGETForFindRequest(viewName);
        String seed = Info.info.getSeedFromRequest(searchResponse);

        return getRawSimilarTracks(seed, new Params(limit));
    }

    private Track parseTrack(String in) throws ParseException {
        StringBuilder artist = new StringBuilder();

        org.json.simple.JSONArray ArtistsArr = getArray(parseObject(in.toString(), "artists"));

        JSONObject nameObject = getObject(in.toString());

        if (ArtistsArr.size() > 1) {
            for (Object o : ArtistsArr) {
                artist.append(parseObject(o.toString(), "name")).append(",").append(" ");
            }

            artist.delete(artist.length() - 2, artist.length());
        } else {
            artist = new StringBuilder(getObject(ArtistsArr.get(0).toString()).get("name").toString());
        }

        Track track = new Track()
                .setAuthor(artist.toString())
                .setId(nameObject.get("name").toString())
                .setTitle(nameObject.get("name").toString())
                .setPopularity(Integer.parseInt(nameObject.get("popularity").toString()))
                .setDuration(Integer.parseInt(nameObject.get("duration_ms").toString()))
                .setExplicit(Boolean.parseBoolean(nameObject.get("explicit").toString()))
                .setAlbumArt(getAlbumUrlsFromRawJson(nameObject))
                .setExternalUrls(parseObject(nameObject.get("external_urls").toString(), "spotify"))
                .setHref(nameObject.get("href").toString())
                .setDateUpload(parseObject(nameObject.get("album").toString(), "release_date"));

        return track;
    }

   public Track search(String query) throws IOException, ParseException {
       String track = getSearchedItems(Net.netty.sendGETForFindRequest(query)).get(0).toString();

       JSONObject nameObject = getObject(track);

       StringBuilder artist = new StringBuilder();

       org.json.simple.JSONArray ArtistsArr = getArray(parseObject(track, "artists"));

       if (ArtistsArr.size() > 1) {
           for (Object o : ArtistsArr) {
               artist.append(parseObject(o.toString(), "name")).append(",").append(" ");
           }

           artist.delete(artist.length() - 2, artist.length());
       } else {
           artist = new StringBuilder(getObject(ArtistsArr.get(0).toString()).get("name").toString());
       }

       return new Track()
               .setId(nameObject.get("name").toString())
               .setTitle(nameObject.get("name").toString())
               .setPopularity(Integer.parseInt(nameObject.get("popularity").toString()))
               .setDuration(Integer.parseInt(nameObject.get("duration_ms").toString()))
               .setExplicit(Boolean.parseBoolean(nameObject.get("explicit").toString()))
               .setAuthor(artist.toString())
               .setAlbumArt(getAlbumUrlsFromRawJson(nameObject));
   }

    /**
     * Извлекает список объектов Art (альбомные изображения) из JSON.
     *
     * @param nameObject JSONObject с информацией об альбоме.
     * @return Список Art.
     * @throws ParseException ошибка парсинга JSON.
     */
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

    /**
     * Парсит строку JSON в JSONArray.
     *
     * @param in JSON строка.
     * @return JSONArray.
     * @throws ParseException ошибка парсинга.
     */
    private JSONArray getArray(String in) throws ParseException {
        Object albumParser = new JSONParser().parse(in);
        return (JSONArray) albumParser;
    }

    /**
     * Парсит строку JSON в JSONObject.
     *
     * @param in JSON строка.
     * @return JSONObject.
     * @throws ParseException ошибка парсинга.
     */
    private JSONObject getObject(String in) throws ParseException {
        Object albumParser = new JSONParser().parse(in);
        return  (JSONObject) albumParser;
    }

    /**
     * Парсит и возвращает значение по ключу из JSONObject в виде строки.
     *
     * @param in  JSON строка.
     * @param key ключ для поиска.
     * @return значение в виде строки.
     * @throws ParseException ошибка парсинга.
     */
    private String parseObject(String in, String key) throws ParseException {
        JSONObject albumObject = getObject(in);
        return albumObject.get(key).toString();
    }

    /**
     * Парсит JSONArray и возвращает элемент по индексу в виде строки.
     *
     * @param in  JSON строка с массивом.
     * @param key индекс элемента.
     * @return значение в виде строки.
     * @throws ParseException ошибка парсинга.
     */
    private String parseArray(String in, int key) throws ParseException {
        JSONArray albumObject = getArray(in);
        return albumObject.get(key).toString();
    }
}