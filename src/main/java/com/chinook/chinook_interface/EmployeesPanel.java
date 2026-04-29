/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chinook.chinook_interface;

/**
 *
 * @author DELL LATITUDE 7200
 */
import javax.swing.*; /*swing GUI components such as the JPanel,JTable,JScrollPane*/
import javax.swing.border.EmptyBorder;/*empty space around thee panel*/
import javax.swing.table.DefaultTableModel;/*data manager for JTABLE??????*/
import java.awt.*;/*layout managers eg border layout*/
import java.sql.*;/*database classes connection,statement,resulset,SQLException*/
import java.util.ArrayList;

public class EmployeesPanel extends JPanel {/*suvlass of JPanel so can add to a tab or window*/
    private JTable employeeTable;
    private DefaultTableModel tableModel;/*data model behind the table,holds rows and column names etc*/
    private JTextField filterTextField;
    private   JButton filterButton;

    public EmployeesPanel() {
        /*builds the visisuals being the table*/
        initUI();
        /*contacts the database and fills the table with rows*/
        loadEmployeeData();
    }

    private void initUI() {
        /*arranges coponents in five areas North,South,East,West,Center*/
        setLayout(new BorderLayout());
        /*adds 10 pixels of empty space on all sides*/
        setBorder(new EmptyBorder(10, 10, 10, 10));
        
        /*create panel to hold search*/
           // --- This is the new filter panel section ---
    // Create a panel to hold the search components
    JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

    // Add a label, a text field, and a button to the filter panel
        filterPanel.add(new JLabel("Search by Name or City:"));
        filterTextField = new JTextField(20);
        filterButton = new JButton("Apply Filter");
        filterPanel.add(filterTextField);
        filterPanel.add(filterButton);
        
        filterButton.addActionListener(e->loadEmployeeData());
        
        
        String[] columns = {"First Name", "Last Name", "Title", "City",
                            "Country", "Phone", "Reports To", "Active"};
        /*stores data we load from the dataabase*/
        tableModel = new DefaultTableModel(columns, 0);/*column names and initial number of rows*/
        /*visible grid*/
        employeeTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(employeeTable);/*wrapped in scroll pane so if many users you can scroll*/
        add(scrollPane, BorderLayout.CENTER);/*adds scrollpane which has the table to the center of the panel*/
        
        add(filterPanel,BorderLayout.NORTH);
        add(scrollPane,BorderLayout.CENTER);
    }

    private void loadEmployeeData() {
        String searchTerm=filterTextField.getText().trim();

        String sql = "SELECT e.FirstName, e.LastName, e.Title, e.City, e.Country, e.Phone, " +
                     "m.FirstName AS MgrFirst, m.LastName AS MgrLast " +
                     "FROM Employee e " +
                     "LEFT JOIN Employee m ON e.ReportsTo = m.EmployeeId";
        
         if (searchTerm != null && !searchTerm.isEmpty()) {
        sql += " WHERE CONCAT(e.FirstName, ' ', e.LastName) LIKE ? OR e.City LIKE ?";
    }   
        try (Connection conn = Database.getDBConnection();
             PreparedStatement pstmt=conn.prepareStatement(sql)){
            if(!searchTerm.isEmpty()){
                String pattern="%"+searchTerm+"%";
                pstmt.setString(1,pattern);
                pstmt.setString(2,pattern);
            }
           
            try(ResultSet rs = pstmt.executeQuery()){
                tableModel.setRowCount(0);
               while (rs.next()) {
                   // Use ArrayList to build the row dynamically
                   ArrayList<Object> rowList = new ArrayList<>();
                   rowList.add(rs.getString("FirstName"));
                   rowList.add(rs.getString("LastName"));
                   rowList.add(rs.getString("Title"));
                   rowList.add(rs.getString("City"));
                   rowList.add(rs.getString("Country"));
                   rowList.add(rs.getString("Phone"));

                   // Supervisor name (or "None")
                   String mgrFirst = rs.getString("MgrFirst");
                   String mgrLast = rs.getString("MgrLast");
                   String supervisor = (mgrFirst != null) ? mgrFirst + " " + mgrLast : "None";
                   rowList.add(supervisor);


                   rowList.add("Yes");

                   // Convert ArrayList to Object[] and add to table model
                   Object[] rowArray = rowList.toArray();
                   tableModel.addRow(rowArray);
                }
            }
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Database error: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}

