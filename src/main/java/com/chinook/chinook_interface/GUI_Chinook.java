/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.chinook.chinook_interface;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Felicity Unathi Moyo
 */
public class GUI_Chinook extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GUI_Chinook.class.getName());

    /**
     * Creates new form GUI_Chinook
     */
    public GUI_Chinook() {
        initComponents();
      getTracks();
      getReport();
      getCustomers();
    }
    private void getTracks(){
        try{
        Connection con = Database.getDBConnection();

    String query= "SELECT TrackId, Name, AlbumId,MediaTypeId, GenreId,Composer,Milliseconds,Bytes, UnitPrice FROM Track";

    PreparedStatement smt = con.prepareStatement(query);
    ResultSet  result= smt.executeQuery();
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0);

    while (result.next()) {
        model.addRow(new Object[]{
            result.getInt("TrackId"),
            result.getString("Name"),
            result.getInt("AlbumId"),
            result.getInt("GenreId"),
            result.getInt("MediaTypeId"),
            result.getString("Composer"),
            result.getInt("Milliseconds"),
            result.getInt("Bytes"),
            result.getDouble("UnitPrice")
        });
    }}catch(Exception e){
    e.getMessage();
    }
} 
    private void getReport(){
        try{
        Connection con = Database.getDBConnection();

        String query= "SELECT Genre.Name,sum(invoice.Total)as InvoiceTotals from Genre "
                + " join Track on Genre.GenreId=Track.GenreId "
                + "join invoiceline on Track.TrackId =invoiceline.TrackId "
                + " join invoice on invoiceline.InvoiceId = invoice.InvoiceId "
                + " group by Genre.Name "
                + "Order by InvoiceTotals desc";

        PreparedStatement smt = con.prepareStatement(query);
        ResultSet  result= smt.executeQuery();
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
    model.setRowCount(0);

    while (result.next()) {
        model.addRow(new Object[]{
            result.getString("Name"),
            result.getDouble("InvoiceTotals")
        });
    }
        }catch(Exception e){
            e.printStackTrace();
    javax.swing.JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void getCustomers(){
             try{
        Connection con = Database.getDBConnection();

    String query= "SELECT CustomerId, FirstName, LastName,Country,Phone,Email FROM Customer";

    PreparedStatement smt = con.prepareStatement(query);
    ResultSet  result= smt.executeQuery();
    DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
    model.setRowCount(0);

    while (result.next()) {
        model.addRow(new Object[]{
            result.getInt("CustomerId"),
            result.getString("FirstName"),
            result.getString("LastName"),
            result.getString("Country"),
            result.getString("Phone"),
            result.getString("Email"),
        });
    }}catch(Exception e){
    e.getMessage();
    }
    }
    private void loadInactiveCustomers(){
    try {
        Connection con = Database.getDBConnection();

        String sql = "SELECT c.CustomerId, c.FirstName, c.LastName,c.Country,C.Phone, c.Email, " +
                     "MAX(i.InvoiceDate) AS LastInvoiceDate " +
                     "FROM Customer c " +
                     "LEFT JOIN Invoice i ON c.CustomerId = i.CustomerId " +
                     "GROUP BY c.CustomerId, c.FirstName, c.LastName,c.Country,C.Phone, c.Email " +
                     "HAVING LastInvoiceDate IS NULL " +
                     "OR LastInvoiceDate < DATE_SUB(CURDATE(), INTERVAL 2 YEAR)";

        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        DefaultTableModel model = (DefaultTableModel) jTable4.getModel();
        model.setRowCount(0);

        while(rs.next()){
            model.addRow(new Object[]{
                rs.getInt("CustomerId"),
                rs.getString("FirstName"),
                rs.getString("LastName"),
                rs.getString("Email"),
                rs.getString("LastInvoiceDate")
            });
        }

    } catch(Exception e){
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, e.getMessage());
    }
}
    private void searchInactive(){
    String text = SearchText.getText().trim();

    try {
        Connection con = Database.getDBConnection();

        String sql = "SELECT c.CustomerId, c.FirstName, c.LastName,c.Country,C.Phone, c.Email, " +
                     "MAX(i.InvoiceDate) AS LastInvoiceDate " +
                     "FROM Customer c " +
                     "LEFT JOIN Invoice i ON c.CustomerId = i.CustomerId " +
                     "GROUP BY c.CustomerId, c.FirstName, c.LastName,c.Country,C.Phone, c.Email " +
                     "HAVING (LastInvoiceDate IS NULL OR LastInvoiceDate < DATE_SUB(CURDATE(), INTERVAL 2 YEAR)) " +
                     "AND (c.FirstName = ? OR c.LastName = ?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, text);
        ps.setString(2, text);

        ResultSet rs = ps.executeQuery();

        DefaultTableModel model = (DefaultTableModel) jTable4.getModel();
        model.setRowCount(0);

        while(rs.next()){
            model.addRow(new Object[]{
                rs.getInt("CustomerId"),
                rs.getString("FirstName"),
                rs.getString("LastName"),
                rs.getString("Email"),
                rs.getString("LastInvoiceDate")
            });
        }

    } catch(Exception e){
        e.printStackTrace();
    }
}
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        NameText = new javax.swing.JTextField();
        SurnameText = new javax.swing.JTextField();
        EmailText = new javax.swing.JTextField();
        PhoneText = new javax.swing.JTextField();
        CountryText = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        IdText = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        SearchText = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 703, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 571, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Employees", jPanel1);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "TrackId", "Name", "AlbumId", "MediaTypeId", "GenreId", "Composer", "Milliseconds", "Bytes", "Unit Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Add New Tracks");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 697, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(123, 123, 123)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jButton1)
                .addContainerGap(137, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Tracks", jPanel2);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Genre Name", "Invoice Totals"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 251, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 144, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Report", jPanel3);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 703, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 571, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Customer Recommendations", jPanel5);

        SurnameText.addActionListener(this::SurnameTextActionPerformed);

        CountryText.addActionListener(this::CountryTextActionPerformed);

        jLabel1.setText("Name");

        jLabel2.setText("Last Name");

        jLabel3.setText("Email");

        jLabel4.setText("Phone");

        jLabel5.setText("Country");

        jButton2.setText("INSERT");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jButton3.setText("UPDATE");
        jButton3.addActionListener(this::jButton3ActionPerformed);

        jButton4.setText("SELECT");
        jButton4.addActionListener(this::jButton4ActionPerformed);

        jButton5.setText("DELETE");
        jButton5.addActionListener(this::jButton5ActionPerformed);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "CustomerId", "First Name", "LastName", "Country", "Phone", "Email"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable3);

        jLabel6.setText("CustomerId");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jButton2)
                        .addGap(75, 75, 75)
                        .addComponent(jButton3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(81, 81, 81)
                                .addComponent(jLabel1))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(IdText, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(NameText, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(54, 54, 54)
                                .addComponent(jLabel2)
                                .addGap(51, 51, 51)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(SurnameText, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(EmailText, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addComponent(jButton5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 584, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(89, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(PhoneText, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(CountryText, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jButton4)
                                .addGap(197, 197, 197))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(87, 87, 87)))
                        .addComponent(jLabel5)
                        .addGap(47, 47, 47))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SurnameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EmailText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PhoneText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CountryText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IdText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Notifications", jPanel4);

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                " CustomerId", "First Name", "Last Name", "Country", "Phone", "Email", "InvoiceDate"
            }
        ));
        jScrollPane4.setViewportView(jTable4);

        SearchText.setText("Search");
        SearchText.addActionListener(this::SearchTextActionPerformed);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 627, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(284, 284, 284)
                        .addComponent(SearchText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(SearchText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );

        jTabbedPane1.addTab("Inactive", jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ChinookDialog dialog = new ChinookDialog(this, true);
    dialog.setVisible(true);
    getTracks();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try{
        Connection con= Database.getDBConnection();

    String query = "INSERT INTO Customer (CustomerId,FirstName, LastName, Country,Phone,Email) VALUES (?, ?, ?, ?, ?,? )";

    PreparedStatement smt = con.prepareStatement(query);
    
    smt.setInt(1, Integer.parseInt(IdText.getText().trim()));
    smt.setString(2, NameText.getText().trim());
    smt.setString(3, SurnameText.getText().trim());
    smt.setString(4, CountryText.getText().trim());
    smt.setString(5, PhoneText.getText().trim());
    smt.setString(6, EmailText.getText().trim());


    smt.executeUpdate();
    getCustomers();
       }catch(Exception e){
           e.printStackTrace();
    javax.swing.JOptionPane.showMessageDialog(this, e.getMessage());
       }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        try{
        Connection con= Database.getDBConnection();

    String query = "delete from Customer "
            + "where CustomerId=?";

    PreparedStatement smt = con.prepareStatement(query);
    
    smt.setInt(1, Integer.parseInt(IdText.getText().trim()));
   
    
 
    smt.executeUpdate();
    getCustomers();
       }catch(Exception e){
           e.printStackTrace();
    javax.swing.JOptionPane.showMessageDialog(this, e.getMessage());
       }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try{
        Connection con= Database.getDBConnection();

    String query = "Select CustomerId,FirstName, LastName, Country,Phone,Email from Customer "
            + "where CustomerId=? or FirstName=? or LastName=? or Country=? or Phone=? or Email=?" ;
            

    PreparedStatement smt = con.prepareStatement(query);
    
    smt.setInt(1, Integer.parseInt(IdText.getText().trim()));
    smt.setString(2, NameText.getText().trim());
    smt.setString(3, SurnameText.getText().trim());
    smt.setString(4, CountryText.getText().trim());
    smt.setString(5, PhoneText.getText().trim());
    smt.setString(6, EmailText.getText().trim());
   
     ResultSet  result= smt.executeQuery();
    DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
    model.setRowCount(0);

    while (result.next()) {
        model.addRow(new Object[]{
            result.getInt("CustomerId"),
            result.getString("FirstName"),
            result.getString("LastName"),
            result.getString("Country"),
            result.getString("Phone"),
            result.getString("Email"),
        });
    
       }}catch(Exception e){
           e.printStackTrace();
    javax.swing.JOptionPane.showMessageDialog(this, e.getMessage());
               }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
              try{
        Connection con= Database.getDBConnection();

    String query = "Update Customer Set FirstName=?, LastName=?, Country=?,Phone=?,Email=? "
            + "where CustomerId=?";

    PreparedStatement smt = con.prepareStatement(query);
    
    smt.setString(1, NameText.getText().trim());
    smt.setString(2, SurnameText.getText().trim());
    smt.setString(3, CountryText.getText().trim());
    smt.setString(4, PhoneText.getText().trim());
    smt.setString(5, EmailText.getText().trim());
    smt.setInt(6, Integer.parseInt(IdText.getText().trim()));
   


    smt.executeUpdate();
    getCustomers();
       }catch(Exception e){
           e.printStackTrace();
    javax.swing.JOptionPane.showMessageDialog(this, e.getMessage());
       }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void CountryTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CountryTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CountryTextActionPerformed

    private void SurnameTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SurnameTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SurnameTextActionPerformed

    private void SearchTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchTextActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new GUI_Chinook().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField CountryText;
    private javax.swing.JTextField EmailText;
    private javax.swing.JTextField IdText;
    private javax.swing.JTextField NameText;
    private javax.swing.JTextField PhoneText;
    private javax.swing.JTextField SearchText;
    private javax.swing.JTextField SurnameText;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    // End of variables declaration//GEN-END:variables
}
