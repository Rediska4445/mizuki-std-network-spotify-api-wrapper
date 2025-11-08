package me.API;

/**
 * Класс Params представляет набор настроек для рекомендаций Spotify.
 * <p>
 * Он содержит параметры, влияющие на фильтрацию и настройку рекомендаций:
 * лимит количества треков, диапазоны темпа, популярности и другие параметры аудиоанализа.
 * </p>
 *
 * <h2>Поля класса</h2>
 * <ul>
 *   <li>limit — максимальное количество треков (по умолчанию 25, если введено неверное значение)</li>
 *   <li>min_temp, max_temp — минимальный и максимальный темп</li>
 *   <li>min_popularity, max_popularity — минимальная и максимальная популярность</li>
 *   <li>min_energy, max_energy — минимальный и максимальный уровень энергии (строковые значения от "0" до "1")</li>
 *   <li>min_danceability, max_danceability — минимальная и максимальная танцевальная способность (строковые значения)</li>
 *   <li>min_valence, max_valence — минимальный и максимальный валентность</li>
 *   <li>min_instrumentalness, max_instrumentalness — доля инструментальной составляющей</li>
 *   <li>min_acousticness, max_acousticness — акустические характеристики</li>
 * </ul>
 *
 * <h2>Конструкторы</h2>
 * <ul>
 *   <li>{@link #Params(int)} — конструктор с заданием лимита, остальные параметры по умолчанию</li>
 *   <li>{@link #Params(int, int, int, int, int, String, String, String, String, String, String, String, String, String, String)} — полный конструктор для тонкой настройки</li>
 * </ul>
 *
 * <h2>Пример использования</h2>
 * <pre>{@code
 * Params p = new Params(20);
 * p.min_temp = 60;
 * p.max_temp = 120;
 * p.min_popularity = 10;
 * p.max_popularity = 80;
 * }</pre>
 *
 * @author Ebanina Std.
 * @version 1.2
 * @since 1.0
 */
public final class Params {
    /**
     * Максимальное количество возвращаемых треков в рекомендации.
     * Значение по умолчанию — 25, если передано менее 0 или более 100.
     */
    public int limit;

    /**
     * Минимальное значение tempo (темп) в BPM.
     */
    public int min_temp;

    /**
     * Максимальное значение tempo (темп) в BPM.
     */
    public int max_temp;

    /**
     * Минимальное значение популярности трека (от 0 до 100).
     */
    public int min_popularity;

    /**
     * Максимальное значение популярности трека (от 0 до 100).
     */
    public int max_popularity;

    /**
     * Минимальное значение энергии трека, представлено строкой от "0" до "1".
     */
    public String min_energy;

    /**
     * Максимальное значение энергии трека, представлено строкой от "0" до "1".
     */
    public String max_energy;

    /**
     * Минимальное значение danceability (танцевальности), строка от "0" до "1".
     */
    public String min_danceability;

    /**
     * Максимальное значение danceability (танцевальности), строка от "0" до "1".
     */
    public String max_danceability;

    /**
     * Минимальное значение valence (оптимизма/радости трека), строка от "0" до "1".
     */
    public String min_valence;

    /**
     * Максимальное значение valence (оптимизма/радости трека), строка от "0" до "1".
     */
    public String max_valence;

    /**
     * Минимальное значение instrumentalness (инструментальной составляющей), строка от "0" до "1".
     */
    public String min_instrumentalness;

    /**
     * Максимальное значение instrumentalness (инструментальной составляющей), строка от "0" до "1".
     */
    public String max_instrumentalness;

    /**
     * Минимальное значение acousticness (акустичности), строка от "0" до "1".
     */
    public String min_acousticness;

    /**
     * Максимальное значение acousticness (акустичности), строка от "0" до "1".
     */
    public String max_acousticness;

    /**
     * Полный конструктор для задания всех параметров настройки рекомендаций.
     *
     * @param limit                Максимальное количество треков (по умолчанию 25, если значение за пределами 0-100)
     * @param min_temp             Минимальный темп (BPM) трека
     * @param max_temp             Максимальный темп (BPM) трека
     * @param min_popularity       Минимальная популярность трека (0-100)
     * @param max_popularity       Максимальная популярность трека (0-100)
     * @param min_energy           Минимальное значение энергии трека ("0"-"1")
     * @param max_energy           Максимальное значение энергии трека ("0"-"1")
     * @param min_danceability     Минимальное значение танцевальности трека ("0"-"1")
     * @param max_danceability     Максимальное значение танцевальности трека ("0"-"1")
     * @param min_valence          Минимальное значение валентности трека ("0"-"1")
     * @param max_valence          Максимальное значение валентности трека ("0"-"1")
     * @param min_instrumentalness Минимальное значение инструментальности трека ("0"-"1")
     * @param max_instrumentalness Максимальное значение инструментальности трека ("0"-"1")
     * @param min_acousticness     Минимальное значение акустичности трека ("0"-"1")
     * @param max_acousticness     Максимальное значение акустичности трека ("0"-"1")
     */
    public Params(
          int limit,
          int min_temp,
          int max_temp,
          int min_popularity,
          int max_popularity,
          String min_energy,
          String max_energy,
          String min_danceability,
          String max_danceability,
          String min_valence,
          String max_valence,
          String min_instrumentalness,
          String max_instrumentalness,
          String min_acousticness,
          String max_acousticness
    ) {
        this.limit = limit < 0 || limit > 100 ? 25 : limit;
        this.min_temp = min_temp;
        this.max_temp = max_temp;

        this.min_energy = min_energy;
        this.max_energy = max_energy;

        this.min_danceability = min_danceability;
        this.max_danceability = max_danceability;

        this.min_popularity = min_popularity;
        this.max_popularity = max_popularity;

        this.min_valence = min_valence;
        this.max_valence = max_valence;

        this.min_instrumentalness = min_instrumentalness;
        this.max_instrumentalness = max_instrumentalness;

        this.min_acousticness = min_acousticness;
        this.max_acousticness = max_acousticness;
    }

    /**
     * Конструктор с одним параметром для указания лимита треков.
     * Остальные параметры инициализируются значениями по умолчанию.
     *
     * @param limit Максимальное количество треков (по умолчанию 25, если значение за пределами 0-100)
     */
    public Params(int limit) {
        this(limit, 20, 250, 0, 100,
                "0", "1", "0", "1",
                "0", "1", "0", "1",
                "0", "1");
    }
}