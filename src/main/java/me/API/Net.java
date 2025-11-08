package me.API;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Класс Net обеспечивает отправку HTTP GET-запросов к API сервиса dubolt.com.
 * <p>
 * Используется для поиска треков и получения результатов поиска через HTTP вызовы.
 * <p>
 * INSTANCE:
 * <p>
 * {@code Net.netty} — статический синглтон для удобного использования класса.
 * <p>
 * МЕТОДЫ:
 * <ul>
 *   <li>{@link #sendGETForFindRequest(String)} — выполняет GET-запрос для поиска по имени трека с параметрами по умолчанию.</li>
 *   <li>{@link #sendGETForFindRequest(String, Params)} — то же, но c передачей параметров {@link Params}.</li>
 *   <li>{@link #sendGETRequest(String)} — базовый метод выполнения HTTP GET-запроса по URL и возврата ответа в виде строки.</li>
 * </ul>
 * <p>
 * ОСОБЕННОСТИ:
 * <ul>
 *   <li>Метод {@link #openConnection(String)} выделен для возможности переопределения и мокирования в тестах.</li>
 *   <li>Используется стандартный класс {@link HttpURLConnection} из JDK.</li>
 *   <li>Ответ возвращается как строка, прочитанная из потока {@link BufferedReader}.</li>
 * </ul>
 * <p>
 * ПРИМЕР ИСПОЛЬЗОВАНИЯ:
 * <pre>{@code
 * String result = Net.netty.sendGETForFindRequest("Some Artist");
 * System.out.println(result);
 * }</pre>
 *
 * @author Ebanina Std.
 * @version 1.0
 * @since 1.0
 */
public class Net {
    /**
     * Статический экземпляр класса для удобства использования.
     */
    public static Net netty = new Net();

    /**
     * Открывает HTTP-соединение для заданного URL.
     * Этот метод можно переопределить для мокирования в тестах.
     *
     * @param url строка URL, к которому нужно подключиться
     * @return объект соединения {@link HttpURLConnection}
     * @throws IOException если возникла ошибка при создании соединения
     */
    public HttpURLConnection openConnection(String url) throws IOException {
        URL obj = new URL(url);
        return (HttpURLConnection) obj.openConnection();
    }

    /**
     * Отправляет GET-запрос для поиска трека по имени с параметрами по умолчанию.
     *
     * @param nameTrack имя трека для поиска
     * @return ответ сервера в виде строки JSON
     * @throws IOException ошибка ввода-вывода при выполнении запроса
     */
    public String sendGETForFindRequest(String nameTrack) throws IOException {
        return sendGETForFindRequest(nameTrack, new Params(20));
    }

    /**
     * Отправляет GET-запрос для поиска трека по имени с заданными параметрами.
     *
     * @param nameTrack имя трека для поиска
     * @param par       объект параметров настройки поиска {@link Params}
     * @return ответ сервера в виде строки JSON
     * @throws IOException ошибка ввода-вывода при выполнении запроса
     */
    public String sendGETForFindRequest(String nameTrack, Params par) throws IOException {
        return sendGETRequest(
                ("https://dubolt.com/api/search/?query="+ nameTrack + "&type=track&limit=" + par.limit)
                        .replaceAll(" ", "%20")
        );
    }

    /**
     * Выполняет HTTP GET-запрос по заданному URL и возвращает ответ как строку.
     *
     * @param url строка URL для запроса
     * @return тело ответа сервера в виде строки
     * @throws IOException ошибка ввода-вывода при выполнении запроса
     */
    public String sendGETRequest(String url) throws IOException {
        HttpURLConnection connection = openConnection(url);
        connection.setRequestMethod("GET");
        connection.setUseCaches(false);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        return response.toString();
    }
}