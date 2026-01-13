package me.API.Album;

import java.io.Serial;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Класс Track представляет аудиотрек с метаданными,
 * такими как автор, название, изображения альбома, популярность, длительность и флаг явного контента.
 * <p>
 * Реализует интерфейсы {@link Serializable} и {@link Comparable}&lt;Track&gt; для сериализации
 * и сравнения по идентификатору.
 * </p>
 *
 * <p>Основные поля:</p>
 * <ul>
 *     <li>{@code id} — уникальный идентификатор трека.</li>
 *     <li>{@code author} — имя автора (исполнителя).</li>
 *     <li>{@code title} — название трека.</li>
 *     <li>{@code albumArt} — список объектов {@link Art} с изображениями альбома.</li>
 *     <li>{@code popularity} — популярность трека в диапазоне от 0 до 100.</li>
 *     <li>{@code duration} — длительность трека в миллисекундах.</li>
 *     <li>{@code isExplicit} — признак явного контента (цензуры).</li>
 * </ul>
 *
 * <p>Методы-сеттеры реализованы с возвратом {@code this} для цепочки вызовов (fluent style).</p>
 *
 * @author Ebanina Std.
 * @version 1.2
 * @since 1.2
 */
public class Track implements Serializable, Comparable<Track> {
    @Serial
    private static final long serialVersionUID = 3_7_4L;

    private String author;
    private String title;
    private List<Art> albumArt;
    private String id;
    private int popularity;
    private int duration;
    private boolean isExplicit;

    /**
     * Пустой конструктор.
     */
    public Track() {}

    /**
     * Возвращает признак явного контента.
     * @return {@code true} если трек содержит явный контент, иначе {@code false}.
     */
    public boolean isExplicit() {
        return isExplicit;
    }

    /**
     * Устанавливает признак явного контента.
     * @param explicit признак явного контента.
     * @return текущий объект для цепочки вызовов.
     */
    public Track setExplicit(boolean explicit) {
        isExplicit = explicit;
        return this;
    }

    /**
     * Возвращает длительность трека в миллисекундах.
     * @return длительность трека.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Устанавливает длительность трека в миллисекундах.
     * @param duration длительность.
     * @return текущий объект для цепочки вызовов.
     */
    public Track setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    /**
     * Возвращает популярность трека (0-100).
     * @return уровень популярности.
     */
    public int getPopularity() {
        return popularity;
    }

    /**
     * Устанавливает популярность трека.
     * @param popularity уровень популярности.
     * @return текущий объект для цепочки вызовов.
     */
    public Track setPopularity(int popularity) {
        this.popularity = popularity;
        return this;
    }

    /**
     * Возвращает уникальный идентификатор трека.
     * @return идентификатор.
     */
    public String getId() {
        return id;
    }

    /**
     * Устанавливает идентификатор трека.
     * @param id идентификатор.
     * @return текущий объект для цепочки вызовов.
     */
    public Track setId(String id) {
        this.id = id;
        return this;
    }

    /**
     * Возвращает список изображений альбома.
     * @return список объектов {@link Art}.
     */
    public List<Art> getAlbumArt() {
        return albumArt;
    }

    /**
     * Возвращает самую большую обложку альбома
     * @return обложка альбома {@link Art}.
     */
    public Art getAwesomeAlbumArt() {
        Optional<Art> maxHeightArt = albumArt.stream()
                .max(Comparator.comparingInt(Art::getHeight));

        return maxHeightArt.orElse(null);
    }

    /**
     * Устанавливает список изображений альбома.
     * @param albumArt список изображений.
     * @return текущий объект для цепочки вызовов.
     */
    public Track setAlbumArt(List<Art> albumArt) {
        this.albumArt = albumArt;
        return this;
    }

    /**
     * Возвращает имя автора (исполнителя).
     * @return имя автора.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Устанавливает имя автора.
     * @param author имя автора.
     * @return текущий объект для цепочки вызовов.
     */
    public Track setAuthor(String author) {
        this.author = author;
        return this;
    }

    /**
     * Возвращает название трека.
     * @return название.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Устанавливает название трека.
     * @param title название.
     * @return текущий объект для цепочки вызовов.
     */
    public Track setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Сравнивает два трека по их идентификаторам.
     *
     * @param o другой объект Track
     * @return результат сравнения по id
     */
    @Override
    public int compareTo(Track o) {
        return id.compareTo(o.getId());
    }

    /**
     * Проверяет равенство треков по автору, названию и идентификатору.
     * @param o объект для сравнения.
     * @return {@code true}, если равны.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Track track = (Track) o;

        return author.equals(track.author) && title.equals(track.title) && id.equals(track.id);
    }

    /**
     * Вычисляет хэш-код на основе автора, названия и идентификатора.
     * @return хэш-код.
     */
    @Override
    public int hashCode() {
        return Objects.hash(author, title, id);
    }

    /**
     * Строковое представление трека для отладки и логирования.
     * @return строка с данными о треке.
     */
    @Override
    public String toString() {
        return "Track{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", albumArt=" + albumArt +
                ", id='" + id + '\'' +
                ", popularity=" + popularity +
                ", duration=" + duration +
                ", isExplicit=" + isExplicit +
                '}';
    }
}