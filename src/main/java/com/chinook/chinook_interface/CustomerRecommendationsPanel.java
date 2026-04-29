/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chinook.chinook_interface;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author DELL LATITUDE 7200
 */

public class CustomerRecommendationsPanel extends JPanel {
    private JComboBox<String> customerCombo;          // dropdown for selecting customer
    private JTextArea spendingArea;                   // shows total spent, purchases, last purchase
    private JLabel favouriteGenreLabel;               // shows favourite genre
    private JTable recommendationsTable;              // table of recommended tracks
    private DefaultTableModel recommendationsModel;

    public CustomerRecommendationsPanel() {
        initUI();
        loadCustomerDropdown();     
        customerCombo.addActionListener(e -> loadDataForSelectedCustomer());
        if (customerCombo.getItemCount() > 0) {
            loadDataForSelectedCustomer();
        }
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));

       //Top-Customer
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Select Customer:"));
        customerCombo = new JComboBox<>();
        customerCombo.setPreferredSize(new Dimension(300, 25));
        topPanel.add(customerCombo);
        add(topPanel, BorderLayout.NORTH);

        //Middle Spending and FavvouriteGenre
        JPanel middlePanel = new JPanel(new GridLayout(1, 2, 10, 10));
        
        // Spending summary panel
        JPanel spendingPanel = new JPanel(new BorderLayout());
        spendingPanel.setBorder(BorderFactory.createTitledBorder("Spending Summary"));
        spendingArea = new JTextArea();
        spendingArea.setEditable(false);
        spendingArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        spendingPanel.add(new JScrollPane(spendingArea), BorderLayout.CENTER);
        
        // Favourite genre panel
        JPanel genrePanel = new JPanel(new BorderLayout());
        genrePanel.setBorder(BorderFactory.createTitledBorder("Favourite Genre"));
        favouriteGenreLabel = new JLabel(" ", SwingConstants.CENTER);
        favouriteGenreLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        genrePanel.add(favouriteGenreLabel, BorderLayout.CENTER);
        
        middlePanel.add(spendingPanel);
        middlePanel.add(genrePanel);
        add(middlePanel, BorderLayout.CENTER);

        // Bottom-Recommendations
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Recommended Tracks (based on favourite genre, not yet purchased)"));
        String[] columns = {"Track Name", "Album", "Genre", "Composer"};
        recommendationsModel = new DefaultTableModel(columns, 0);
        recommendationsTable = new JTable(recommendationsModel);
        JScrollPane tableScroll = new JScrollPane(recommendationsTable);
        tableScroll.setPreferredSize(new Dimension(800, 250));
        bottomPanel.add(tableScroll, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadCustomerDropdown() {
        String sql = "SELECT CustomerId, FirstName, LastName FROM Customer ORDER BY LastName, FirstName";
        try (Connection conn = Database.getDBConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("CustomerId");
                String name = rs.getString("FirstName") + " " + rs.getString("LastName");
                customerCombo.addItem(id + " - " + name);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading customers: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private int getSelectedCustomerId() {
        String selected = (String) customerCombo.getSelectedItem();
        if (selected == null) return -1;
        // split at first space-dash-space: "123 - John Doe"
        String[] parts = selected.split(" - ");
        try {
            return Integer.parseInt(parts[0]);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /*load spenidng,favourite and recommendations*/
    private void loadDataForSelectedCustomer() {
        int customerId = getSelectedCustomerId();
        if (customerId == -1) return;

        // Load spending summary
        loadSpendingSummary(customerId);
        // Load favourite genre
        String favGenre = loadFavouriteGenre(customerId);
        // Load recommended tracks (based on favourite genre, excluding already purchased tracks)
        loadRecommendations(customerId, favGenre);
    }

    /** Query total spent, number of invoices, and last invoice date */
    private void loadSpendingSummary(int customerId) {
        String sql = "SELECT COUNT(InvoiceId) AS purchaseCount, SUM(Total) AS totalSpent, MAX(InvoiceDate) AS lastPurchase " +
                     "FROM Invoice WHERE CustomerId = ?";
        try (Connection conn = Database.getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("purchaseCount");
                    double total = rs.getDouble("totalSpent");
                    Date lastDate = rs.getDate("lastPurchase");
                    spendingArea.setText(String.format(
                        "Total spent: $%.2f\nNumber of purchases: %d\nLast purchase date: %s",
                        total, count, (lastDate != null ? lastDate.toString() : "Never")
                    ));
                } else {
                    spendingArea.setText("No purchase data found.");
                }
            }
        } catch (SQLException ex) {
            spendingArea.setText("Error loading spending data.");
            ex.printStackTrace();
        }
    }

    /** Determine the genre the customer has purchased most frequently (based on quantity of tracks). 
     *  Return genre name as String. */
    private String loadFavouriteGenre(int customerId) {
        String sql = "SELECT g.Name AS GenreName, COUNT(il.Quantity) AS purchaseCount " +
                     "FROM InvoiceLine il " +
                     "JOIN Track t ON il.TrackId = t.TrackId " +
                     "JOIN Genre g ON t.GenreId = g.GenreId " +
                     "JOIN Invoice i ON il.InvoiceId = i.InvoiceId " +
                     "WHERE i.CustomerId = ? " +
                     "GROUP BY g.GenreId, g.Name " +
                     "ORDER BY purchaseCount DESC, g.Name " +
                     "LIMIT 1";
        try (Connection conn = Database.getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String genre = rs.getString("GenreName");
                    favouriteGenreLabel.setText(genre);
                    return genre;
                } else {
                    favouriteGenreLabel.setText("No purchases yet");
                    return null;
                }
            }
        } catch (SQLException ex) {
            favouriteGenreLabel.setText("Error");
            ex.printStackTrace();
            return null;
        }
    }

    /** Recommend tracks from the given genre that the customer has not yet purchased.
     *  Shows top 20 tracks (by popularity) but only those not in customer's invoice lines.
     */
    private void loadRecommendations(int customerId, String favouriteGenre) {
        // Clear previous recommendations
        recommendationsModel.setRowCount(0);
        if (favouriteGenre == null || favouriteGenre.trim().isEmpty()) {
            return;
        }

        String sql = "SELECT t.Name AS TrackName, a.Title AS AlbumTitle, g.Name AS GenreName, t.Composer " +
                     "FROM Track t " +
                     "JOIN Album a ON t.AlbumId = a.AlbumId " +
                     "JOIN Genre g ON t.GenreId = g.GenreId " +
                     "WHERE g.Name = ? " +
                     "AND NOT EXISTS (SELECT 1 FROM InvoiceLine il " +
                     "                JOIN Invoice i ON il.InvoiceId = i.InvoiceId " +
                     "                WHERE i.CustomerId = ? AND il.TrackId = t.TrackId) " +
                     "ORDER BY t.TrackId " +  
                     "LIMIT 20";

        try (Connection conn = Database.getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, favouriteGenre);
            pstmt.setInt(2, customerId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ArrayList<Object> row = new ArrayList<>();
                    row.add(rs.getString("TrackName"));
                    row.add(rs.getString("AlbumTitle"));
                    row.add(rs.getString("GenreName"));
                    row.add(rs.getString("Composer") != null ? rs.getString("Composer") : "");
                    recommendationsModel.addRow(row.toArray());
                }
            }
            // If no recommendations, show a friendly message
            if (recommendationsModel.getRowCount() == 0) {
                recommendationsModel.addRow(new Object[]{"No recommendations available - try another customer", "", "", ""});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading recommendations: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
