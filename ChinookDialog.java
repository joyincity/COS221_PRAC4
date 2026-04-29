/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package u25020880_u24702791_chinook;

/**
 *
 * @author DELL LATITUDE 7200
 */

import java.sql.*;
import javax.swing.*;
import java.awt.*;

public class ChinookDialog extends JDialog {
    private JComboBox<String> albumCombo, genreCombo, mediaTypeCombo;
    private JTextField nameField, composerField, millisecondsField, bytesField, trackIdField;
    private JButton saveButton;

    public ChinookDialog(Frame parent, boolean modal) {
        super(parent, modal);
        setTitle("Add New Track");
        setLayout(new BorderLayout());
        initUI();
        loadDropdowns();
        pack();
        setLocationRelativeTo(parent);
    }

    private void initUI() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0: Track ID
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Track ID:"), gbc);
        gbc.gridx = 1;
        trackIdField = new JTextField(15);
        formPanel.add(trackIdField, gbc);

        // Row 1: Track Name
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Track Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(15);
        formPanel.add(nameField, gbc);

        // Row 2: Album
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Album:"), gbc);
        gbc.gridx = 1;
        albumCombo = new JComboBox<>();
        formPanel.add(albumCombo, gbc);

        // Row 3: Media Type
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Media Type:"), gbc);
        gbc.gridx = 1;
        mediaTypeCombo = new JComboBox<>();
        formPanel.add(mediaTypeCombo, gbc);

        // Row 4: Genre
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Genre:"), gbc);
        gbc.gridx = 1;
        genreCombo = new JComboBox<>();
        formPanel.add(genreCombo, gbc);

        // Row 5: Composer
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Composer:"), gbc);
        gbc.gridx = 1;
        composerField = new JTextField(15);
        formPanel.add(composerField, gbc);

        // Row 6: Milliseconds (duration)
        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(new JLabel("Milliseconds:"), gbc);
        gbc.gridx = 1;
        millisecondsField = new JTextField(15);
        formPanel.add(millisecondsField, gbc);

        // Row 7: Bytes (file size)
        gbc.gridx = 0; gbc.gridy = 7;
        formPanel.add(new JLabel("Bytes:"), gbc);
        gbc.gridx = 1;
        bytesField = new JTextField(15);
        formPanel.add(bytesField, gbc);

        // Add button panel
        JPanel buttonPanel = new JPanel();
        saveButton = new JButton("Add Track");
        saveButton.addActionListener(e -> saveTrack());
        buttonPanel.add(saveButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadDropdowns() {
        loadCombo("SELECT AlbumId, Title FROM Album", albumCombo);
        loadCombo("SELECT GenreId, Name FROM Genre", genreCombo);
        loadCombo("SELECT MediaTypeId, Name FROM MediaType", mediaTypeCombo);
    }

    private void loadCombo(String query, JComboBox<String> combo) {
        try (Connection con = Database.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            combo.removeAllItems();
            while (rs.next()) {
                combo.addItem(rs.getInt(1) + " - " + rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }

    private void saveTrack() {
        try {
            // Validate required fields
            if (trackIdField.getText().trim().isEmpty() || nameField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Track ID and Name are required.");
                return;
            }

            int trackId = Integer.parseInt(trackIdField.getText().trim());
            String name = nameField.getText().trim();
            int albumId = Integer.parseInt(albumCombo.getSelectedItem().toString().split(" - ")[0]);
            int mediaTypeId = Integer.parseInt(mediaTypeCombo.getSelectedItem().toString().split(" - ")[0]);
            int genreId = Integer.parseInt(genreCombo.getSelectedItem().toString().split(" - ")[0]);
            String composer = composerField.getText().trim();
            int milliseconds = millisecondsField.getText().trim().isEmpty() ? 0 : Integer.parseInt(millisecondsField.getText().trim());
            int bytes = bytesField.getText().trim().isEmpty() ? 0 : Integer.parseInt(bytesField.getText().trim());
            double unitPrice = 0.99; // default price

            String sql = "INSERT INTO Track (TrackId, Name, AlbumId, MediaTypeId, GenreId, Composer, Milliseconds, Bytes, UnitPrice) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (Connection con = Database.getConnection();
                 PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setInt(1, trackId);
                pstmt.setString(2, name);
                pstmt.setInt(3, albumId);
                pstmt.setInt(4, mediaTypeId);
                pstmt.setInt(5, genreId);
                pstmt.setString(6, composer.isEmpty() ? null : composer);
                pstmt.setInt(7, milliseconds);
                pstmt.setInt(8, bytes);
                pstmt.setDouble(9, unitPrice);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Track added successfully!");
                dispose();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number format. Please check Track ID, Milliseconds, and Bytes.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        }
    }
}
