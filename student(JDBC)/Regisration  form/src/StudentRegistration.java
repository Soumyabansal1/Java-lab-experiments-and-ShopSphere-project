import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class StudentRegistration extends JFrame implements ActionListener {

    // Labels
    JLabel lblName, lblRoll, lblGender, lblBranch;q

    // Text Fields
    JTextField txtName, txtRoll, txtBranch;

    // Radio Buttons
    JRadioButton male, female;
    ButtonGroup genderGroup;

    // Check Box
    JCheckBox terms;

    // Buttons
    JButton submit, reset;

    // Constructor
    public StudentRegistration() {

        setTitle("Student Registration Form");
        setSize(450, 450);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Student Name
        lblName = new JLabel("Student Name");
        lblName.setBounds(30, 30, 120, 30);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(170, 30, 180, 30);
        add(txtName);

        // Roll Number
        lblRoll = new JLabel("Roll Number");
        lblRoll.setBounds(30, 80, 120, 30);
        add(lblRoll);

        txtRoll = new JTextField();
        txtRoll.setBounds(170, 80, 180, 30);
        add(txtRoll);

        // Gender
        lblGender = new JLabel("Gender");
        lblGender.setBounds(30, 130, 120, 30);
        add(lblGender);

        male = new JRadioButton("Male");
        male.setBounds(170, 130, 80, 30);
        add(male);

        female = new JRadioButton("Female");
        female.setBounds(260, 130, 100, 30);
        add(female);

        genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);

        // Branch
        lblBranch = new JLabel("Branch");
        lblBranch.setBounds(30, 180, 120, 30);
        add(lblBranch);

        txtBranch = new JTextField();
        txtBranch.setBounds(170, 180, 180, 30);
        add(txtBranch);

        // Terms and Conditions
        terms = new JCheckBox("I accept Terms & Conditions");
        terms.setBounds(30, 230, 250, 30);
        add(terms);

        // Submit Button
        submit = new JButton("Submit");
        submit.setBounds(80, 290, 100, 35);
        submit.addActionListener(this);
        add(submit);

        // Reset Button
        reset = new JButton("Reset");
        reset.setBounds(220, 290, 100, 35);
        reset.addActionListener(this);
        add(reset);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Reset Button
        if (e.getSource() == reset) {

            txtName.setText("");
            txtRoll.setText("");
            txtBranch.setText("");

            genderGroup.clearSelection();
            terms.setSelected(false);

            return;
        }

        // Student Name Validation
        if (txtName.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Student Name"
            );

            return;
        }

        // Roll Number Validation
        if (txtRoll.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Roll Number"
            );

            return;
        }

        // Gender Validation
        if (!male.isSelected() && !female.isSelected()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select Gender"
            );

            return;
        }

        // Branch Validation
        if (txtBranch.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Branch"
            );

            return;
        }

        // Terms Validation
        if (!terms.isSelected()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please accept Terms & Conditions"
            );

            return;
        }

        // Get Gender
        String gender;

        if (male.isSelected()) {
            gender = "Male";
        } else {
            gender = "Female";
        }

        // JDBC INSERT
        try {

            // IMPORTANT:
            // Your connection class is DBConnectionStudent
            Connection con = DBConnectionStudent.getConnection();

            if (con == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Database connection failed!"
                );

                return;
            }

            // SQL query
            String sql =
                    "INSERT INTO student (roll_number, name, gender, branch) "
                    + "VALUES (?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            // Set values
            ps.setInt(
                    1,
                    Integer.parseInt(txtRoll.getText().trim())
            );

            ps.setString(
                    2,
                    txtName.getText().trim()
            );

            ps.setString(
                    3,
                    gender
            );

            ps.setString(
                    4,
                    txtBranch.getText().trim()
            );

            // Execute INSERT
            ps.executeUpdate();

            // Success message
            JOptionPane.showMessageDialog(
                    this,
                    "Registration Successful!\n\n"
                    + "Name : " + txtName.getText()
                    + "\nRoll Number : " + txtRoll.getText()
                    + "\nGender : " + gender
                    + "\nBranch : " + txtBranch.getText()
            );

            // Close resources
            ps.close();
            con.close();

            // Clear form after successful registration
            txtName.setText("");
            txtRoll.setText("");
            txtBranch.setText("");
            genderGroup.clearSelection();
            terms.setSelected(false);

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Roll Number must be a number"
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error: " + ex.getMessage()
            );

            ex.printStackTrace();
        }
    }

    // Main Method
    public static void main(String[] args) {

        new StudentRegistration();
    }
}