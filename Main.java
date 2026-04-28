/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package u25020880_u24702791_chinook;

/**
 *
 * @author DELL LATITUDE 7200
 */

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("u25020880_u24702791_chinook");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 700);

            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.addTab("Employees", new EmployeesPanel());

            frame.add(tabbedPane);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}