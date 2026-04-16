import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;



public class Calculator {

    int boardW = 360;
    int boardH = 540;

    String A = "0";
    String operation = null; // + or - or / or *
    String B = null;

    Color galaxyBlack = new Color(42, 42, 45);
    Color goldMystic = new Color(177, 150, 63);
    Color hyacinthMagenta = new Color(149, 38, 132);
    Color funBlue = new Color(32, 88, 167);
    
    String[] buttons = {
        "AC", "+/-", "%", "÷", 
        "7", "8", "9", "×", 
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "0", ".", "√", "="
    };

    String[] operatorsButtons = {"÷", "×", "-", "+", "="};
    String[] actionsButtons = {"AC", "+/-", "%"};


    JFrame frame = new JFrame("Calculator");
    JLabel labelArea = new JLabel();
    JPanel panelArea = new JPanel();
    JPanel buttonArea = new JPanel();

    Calculator() {
        //frame.setVisible(true);
        frame.setSize(boardW, boardH);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        labelArea.setBackground(galaxyBlack);
        labelArea.setForeground(Color.WHITE);
        labelArea.setFont(new Font("Areal", Font.PLAIN, 80));
        labelArea.setHorizontalAlignment(JLabel.RIGHT);
        labelArea.setText("0");
        labelArea.setOpaque(true);

        panelArea.setLayout(new BorderLayout());
        panelArea.add(labelArea);
        frame.add(panelArea, BorderLayout.NORTH);

        buttonArea.setLayout(new GridLayout(5, 4));
        buttonArea.setBackground(galaxyBlack);
        frame.add(buttonArea);

        setupButtons();
    }

    private void setupButtons() {
        for (int i = 0; i < buttons.length; i++) {
            JButton button = new JButton();
            String value = buttons[i];
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setText(value);
            button.setFocusable(false);
            button.setBorder(new LineBorder(galaxyBlack));

            if (Arrays.asList(actionsButtons).contains(value)) {
                button.setBackground(goldMystic);
                button.setForeground(Color.WHITE);
            }
            else if (Arrays.asList(operatorsButtons).contains(value)) {
                button.setBackground(hyacinthMagenta);
                button.setForeground(Color.WHITE);
            }
            else {
                button.setBackground(funBlue);
                button.setForeground(Color.WHITE);
            }
            buttonArea.add(button);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton) e.getSource();
                    String buttonValue = button.getText();

                    if (Arrays.asList(operatorsButtons).contains(buttonValue)) {
                        if (buttonValue == "=") {
                            if (A != null) {
                                B = labelArea.getText();
                                double aNumber = Double.parseDouble(A);
                                double bNumber = Double.parseDouble(B);

                                if (operation == "+") {
                                    labelArea.setText(checkNum(aNumber + bNumber));
                                    
                                }
                                else if (operation == "-") {
                                    labelArea.setText(checkNum(aNumber - bNumber));
                                    
                                }
                                else if (operation == "×") {
                                    labelArea.setText(checkNum(aNumber * bNumber));
                                    
                                }
                                else if (operation == "÷") {
                                    labelArea.setText(checkNum(aNumber / bNumber));
                                    
                                }
                                clearDisplay();
                            }
                        }
                        else if ("+-×÷".contains(buttonValue)) {
                            if (operation == null) {
                                A = labelArea.getText();
                                labelArea.setText("0");
                                B = "0";
                            }
                            operation = buttonValue;
                        }
                    }
                    else if (Arrays.asList(actionsButtons).contains(buttonValue)) {
                        if (buttonValue == "AC") {
                            clearDisplay();
                            labelArea.setText("0");
                        }
                        else if (buttonValue == "+/-") {
                            double num = Double.parseDouble(labelArea.getText());
                            num *= -1;
                            labelArea.setText(checkNum(num));
                        }
                        else if (buttonValue == "%") {
                            double num = Double.parseDouble(labelArea.getText());
                            num /= 100;
                            labelArea.setText(checkNum(num));
                        }
                    }
                    else {
                        if (buttonValue == ".") {
                           if (!labelArea.getText().contains(buttonValue)) {
                                labelArea.setText(labelArea.getText() + buttonValue);
                           } 
                        }
                        else if ("0123456789".contains(buttonValue)) {
                            if (labelArea.getText() == "0") {
                                labelArea.setText(buttonValue);
                            }
                            else {
                                labelArea.setText(labelArea.getText() + buttonValue);
                            }
                        }
                    }
                }
            });
            frame.setVisible(true);
        }
    }

    void clearDisplay() {
        A = "0";
        operation = null;
        B = null;
    }

    String checkNum(double num) {
        if (num % 1 == 0) {
            return Integer.toString((int) num);
        }
        return Double.toString(num);
    }
}
