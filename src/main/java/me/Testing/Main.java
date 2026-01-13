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

            JPanel topPanel = new JPanel(new BorderLayout(5, 0));
            topPanel.add(new JLabel("Enter track or artist:"), BorderLayout.WEST);
            topPanel.add(inputField, BorderLayout.CENTER);
            topPanel.add(searchButton, BorderLayout.EAST);

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
                        String searchResponse = Net.netty.sendGETForFindRequest(query);
                        String seed = Info.info.getSeedFromRequest(searchResponse);
                        Track[] tracks = Info.info.getRawSimilarTracks(seed, new Params(25));

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
