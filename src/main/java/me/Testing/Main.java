package me.Testing;

import me.API.Info;
import me.API.Net;
import me.API.Params;
import me.API.Album.Track;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    private static final String[] tracksList = new String[] {
            "dvrst sleeprepeat",
            "dvrst close eyes",
            "dvrst falling stars",
            "dvrst until the stars collide",

            "kute avoid me",
            "kute anubis",
            "kute krush girl",
            "kute raven",
            "kute dead on arrival",

            "moondeity neon blade",
            "moondeity wake up",
            "moondeity one chance",
            "moondeity butterfly",
            "moondeity ghost"
    };

    public static void main(String[] args) throws IOException, ParseException {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Spotify API Main");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);

            JPanel panel = new JPanel(new BorderLayout(10, 10));
            JTextField inputField = new JTextField();
            JButton searchButton = new JButton("Search and Recommend");
            JTextArea resultsArea = new JTextArea();
            resultsArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(resultsArea);

            // выпадающий список с треками
            JComboBox<String> trackComboBox = new JComboBox<>(tracksList);
            trackComboBox.addActionListener(e -> {
                String selected = (String) trackComboBox.getSelectedItem();
                if (selected != null) {
                    inputField.setText(selected); // подставляем в поле ввода
                }
            });

            // верхняя панель: лейбл, поле, комбобокс, кнопка
            JPanel topPanel = new JPanel(new GridLayout(2, 1, 5, 5));
            JPanel upperRow = new JPanel(new BorderLayout(5, 0));
            upperRow.add(new JLabel("Enter track or artist:"), BorderLayout.WEST);
            upperRow.add(inputField, BorderLayout.CENTER);

            JPanel lowerRow = new JPanel(new BorderLayout(5, 0));
            lowerRow.add(new JLabel("Quick tracks:"), BorderLayout.WEST);
            lowerRow.add(trackComboBox, BorderLayout.CENTER);
            lowerRow.add(searchButton, BorderLayout.EAST);

            topPanel.add(upperRow);
            topPanel.add(lowerRow);

            panel.add(topPanel, BorderLayout.NORTH);
            panel.add(scrollPane, BorderLayout.CENTER);
            frame.setContentPane(panel);
            frame.setVisible(true);

            searchButton.addActionListener(e -> {
                String query = inputField.getText().trim();
                if (query.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter a search query.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                resultsArea.setText("Loading... Please wait.");

                new Thread(() -> {
                    try {
                        Track[] tracks = Info.info.getSimilarTracks(query, 25);

                        StringBuilder sb = new StringBuilder();
                        Arrays.stream(tracks).forEach(track -> sb.append("Title: ").append(track.getTitle())
                                .append("\nAuthor: ").append(track.getAuthor())
                                .append("\nPopularity: ").append(track.getPopularity())
                                .append("\nDuration (ms): ").append(track.getDuration())
                                .append("\nExplicit: ").append(track.isExplicit())
                                .append("\n\n"));

                        SwingUtilities.invokeLater(() -> resultsArea.setText(sb.toString()));

                    } catch (IOException | ParseException ex) {
                        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE));
                        SwingUtilities.invokeLater(() -> resultsArea.setText(""));
                    }
                }).start();
            });
        });
    }
}