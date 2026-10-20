// import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {

    public Views view;
    public Models model;

    public Controller(Views view, Models model) {

        this.view = view;
        this.model = model;

        view.btnadd.addActionListener(this);
        view.btnsub.addActionListener(this);
        view.btnmul.addActionListener(this);
        view.btndiv.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {

            double num1 = Double.parseDouble(view.txtnum1.getText());
            double num2 = Double.parseDouble(view.txtnum2.getText());

            double result = 0;

            if (e.getSource() == view.btnadd) {
                result = model.add(num1, num2);
            }
            else if (e.getSource() == view.btnsub) {
                result = model.subtract(num1, num2);
            }
            else if (e.getSource() == view.btnmul) {
                result = model.multiply(num1, num2);
            }
            else if (e.getSource() == view.btndiv) {
                result = model.divide(num1, num2);
            }

            view.txtresult.setText(String.valueOf(result));

        } catch (NumberFormatException ex) {

            view.txtresult.setText("Invalid Input");

        } catch (ArithmeticException ex) {

            view.txtresult.setText(ex.getMessage());
        }
    }
}


