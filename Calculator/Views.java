import javax.swing.*;
public class Views extends JFrame {

    JTextField txtnum1, txtnum2, txtresult; 
    JButton btnadd, btnsub, btnmul, btndiv;
    
    public Views(){
        // title 
        setTitle("Calculator");
        setSize(300, 200);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // number 1 
        JLabel lblnum1 = new JLabel("Number 1:");
        lblnum1.setBounds(30, 30, 80, 25);
        add(lblnum1);
        txtnum1 = new JTextField();
        txtnum1.setBounds(120, 30, 100, 25);
        add(txtnum1);


        //number 2
        JLabel lblnum2 = new JLabel("Number 2:");
        lblnum2.setBounds(30, 60, 80, 25);
        add(lblnum2);
        txtnum2 = new JTextField();
        txtnum2.setBounds(120, 60, 100, 25);
        add(txtnum2);


        //buttons 
        btnadd = new JButton("Add");
        btnadd.setBounds(30, 100, 80, 25);
        add(btnadd);

        btnsub= new JButton("Subt ract");
        btnsub.setBounds(120, 100, 80, 25);
        add(btnsub);

        btnmul = new JButton("Multiply");
        btnmul.setBounds(210, 100, 80, 25);
        add(btnmul);

        btndiv = new JButton("Divide");
        btndiv.setBounds(300, 100, 80, 25);
        add(btndiv);


        // result
        JLabel lblresult = new JLabel("Result:");
        lblresult.setBounds(30, 140, 80, 25);
        add(lblresult);
        txtresult = new JTextField();
        txtresult.setBounds(120, 140, 100, 25);
        add(txtresult);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        }
    }
