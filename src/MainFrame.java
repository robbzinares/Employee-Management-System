import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private JTable employeeTable;
    private EmployeeTableModel tableModel;
    private JTextField lastNameField, firstNameField, sssNoField, philHealthNoField, tinField, pagibigNoField;
    private JButton updateButton;

    public MainFrame() {
        setTitle("Employee Management System");
        setLayout(new BorderLayout());

        tableModel = new EmployeeTableModel();
        employeeTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton viewButton = new JButton("View Employee");
        JButton deleteButton = new JButton("Delete Employee");

        gbc.gridx = 0;
        buttonPanel.add(viewButton, gbc);

        gbc.gridx = 1;
        updateButton = new JButton("Update Employee");
        updateButton.setEnabled(false);
        buttonPanel.add(updateButton, gbc);

        gbc.gridx = 2;
        buttonPanel.add(deleteButton, gbc);

        add(buttonPanel, BorderLayout.SOUTH);

        viewButton.addActionListener(e -> viewEmployee());
        updateButton.addActionListener(e -> updateEmployee());
        deleteButton.addActionListener(e -> deleteEmployee());

        employeeTable.getSelectionModel().addListSelectionListener(e -> updateFields());

        JPanel detailPanel = new JPanel(new GridBagLayout());
        add(detailPanel, BorderLayout.EAST);

        addLabelAndField(detailPanel, "Last Name:", lastNameField = new JTextField(10), 0);
        addLabelAndField(detailPanel, "First Name:", firstNameField = new JTextField(10), 1);
        addLabelAndField(detailPanel, "SSS No.:", sssNoField = new JTextField(10), 2);
        addLabelAndField(detailPanel, "PhilHealth No.:", philHealthNoField = new JTextField(10), 3);
        addLabelAndField(detailPanel, "TIN:", tinField = new JTextField(10), 4);
        addLabelAndField(detailPanel, "Pagibig No.:", pagibigNoField = new JTextField(10), 5);

        setSize(900, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateFields() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow >= 0) {
            String[] employeeDetails = tableModel.getEmployeeDetails((String) tableModel.getValueAt(selectedRow, 0));
            lastNameField.setText(employeeDetails[1]);
            firstNameField.setText(employeeDetails[2]);
            sssNoField.setText(employeeDetails[3]);
            philHealthNoField.setText(employeeDetails[4]);
            tinField.setText(employeeDetails[5]);
            pagibigNoField.setText(employeeDetails[6]);
            updateButton.setEnabled(true);
        } else {
            clearFields();
            updateButton.setEnabled(false);
        }
    }

    private void clearFields() {
        lastNameField.setText("");
        firstNameField.setText("");
        sssNoField.setText("");
        philHealthNoField.setText("");
        tinField.setText("");
        pagibigNoField.setText("");
    }

    private void addLabelAndField(JPanel panel, String labelText, JTextField textField, int row) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        panel.add(textField, gbc);
    }

    private void viewEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow >= 0) {
            String employeeNumber = (String) tableModel.getValueAt(selectedRow, 0);
            new EmployeeDetailsFrame(employeeNumber).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Please select an employee to view.");
        }
    }

    private void updateEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow >= 0) {
            String employeeNumber = (String) tableModel.getValueAt(selectedRow, 0);
            String lastName = lastNameField.getText();
            String firstName = firstNameField.getText();
            String sssNo = sssNoField.getText();
            String philHealthNo = philHealthNoField.getText();
            String tin = tinField.getText();
            String pagibigNo = pagibigNoField.getText();

            if (validateInput(lastName, firstName, sssNo, philHealthNo, tin, pagibigNo)) {
                String[] updatedDetails = {lastName, firstName, sssNo, philHealthNo, tin, pagibigNo};
                tableModel.updateEmployeeDetails(employeeNumber, updatedDetails);
                clearFields();
                updateButton.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields correctly.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an employee to update.");
        }
    }

    private boolean validateInput(String lastName, String firstName, String sssNo, String philHealthNo, String tin, String pagibigNo) {
        return !lastName.isEmpty() && !firstName.isEmpty() && !sssNo.isEmpty() && !philHealthNo.isEmpty() && !tin.isEmpty() && !pagibigNo.isEmpty();
    }

    private void deleteEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow >= 0) {
            String employeeNumber = (String) tableModel.getValueAt(selectedRow, 0);
            tableModel.deleteEmployee(employeeNumber);
            clearFields();
            updateButton.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(this, "Please select an employee to delete.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}
