package me.API.Album;

import java.util.Objects;

/**
 * Класс Art представляет изображение для альбома с URL и размерами.
 * <p>
 * Этот класс является неизменяемым по структуре (final), однако поля доступны для модификации через методы-сеттеры.
 * <p>
 * Основные поля:
 * <ul>
 *   <li>{@code url} — URL изображения.</li>
 *   <li>{@code height} — высота изображения в пикселях.</li>
 *   <li>{@code width} — ширина изображения в пикселях.</li>
 * </ul>
 * <p>
 * Переопределены методы {@link #equals(Object)}, {@link #hashCode()} и {@link #toString()} для корректного сравнения и отображения.
 * Методы-сеттеры возвращают текущий объект для возможности цепочного вызова (fluent interface).
 * </p>
 *
 * @author Ebanina Std.
 * @version 1.0
 * @since 1.0
 */
public final class Art {
    private String url;
    private int height;
    private int width;

    /**
     * Конструктор создания объекта Art с обязательным URL.
     *
     * @param url URL изображения.
     */
    public Art(String url) {
        this.url = url;
    }

    /**
     * Возвращает URL изображения.
     *
     * @return URL в виде строки.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Устанавливает URL изображения.
     *
     * @param url URL в виде строки.
     * @return текущий объект для цепочки вызовов.
     */
    public Art setUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * Возвращает высоту изображения в пикселях.
     *
     * @return высота изображения.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Устанавливает высоту изображения.
     *
     * @param height высота в пикселях.
     * @return текущий объект для цепочки вызовов.
     */
    public Art setHeight(int height) {
        this.height = height;
        return this;
    }

    /**
     * Возвращает ширину изображения в пикселях.
     *
     * @return ширина изображения.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Устанавливает ширину изображения.
     *
     * @param width ширина в пикселях.
     * @return текущий объект для цепочки вызовов.
     */
    public Art setWidth(int width) {
        this.width = width;
        return this;
    }

    /**
     * Проверяет равенство объектов Art по URL изображения.
     *
     * @param o объект для сравнения.
     * @return {@code true}, если равны по URL.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Art art = (Art) o;
        return url.equals(art.url);
    }

    /**
     * Вычисляет хэш-код на основе URL.
     *
     * @return хэш-код объекта.
     */
    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    /**
     * Возвращает строковое представление объекта для отладки.
     *
     * @return строка с информацией об URL, высоте и ширине.
     */
    @Override
    public String toString() {
        return "Art{" +
                "url='" + url + '\'' +
                ", height=" + height +
                ", width=" + width +
                '}';
    }
}
