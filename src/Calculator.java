import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;



public class Calculator {

    int boardW = 400;
    int boardH = 540;

    String A = "0";
    String operation = null; // + or - or / or *
    String B = null;

    Color galaxyBlack = new Color(42, 42, 45);
    Color goldMystic = new Color(177, 150, 63);
    Color hyacinthMagenta = new Color(149, 38, 132);
    Color funBlue = new Color(32, 88, 167);
    
    String[] buttons = {
        "AC", "<", "+/-", "%", "√", 
        "7", "8", "9", "÷", "MC",
        "4", "5", "6", "×", "M1",
        "1", "2", "3", "-", "M2",
        "0", "00", ".", "+", "="
    };

    String[] operatorsButtons = {"÷", "×", "-", "+", "="};
    String[] actionsButtons = {"AC", "<", "+/-", "%", "√"};
    String[] memoryButtons = {"MC", "M1", "M2"};

    String memory1 = "0";
    String memory2 = "0";
    boolean memory1Set = false;
    boolean memory2Set = false;

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
        labelArea.setFont(new Font("Areal", Font.PLAIN, 50));
        labelArea.setHorizontalAlignment(JLabel.RIGHT);
        labelArea.setText("0");
        labelArea.setOpaque(true);

        panelArea.setLayout(new BorderLayout());
        panelArea.add(labelArea);
        frame.add(panelArea, BorderLayout.NORTH);

        buttonArea.setLayout(new GridLayout(5, 5));
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
            else if (Arrays.asList(memoryButtons).contains(value)) {
                button.setBackground(new Color(140, 30, 30));
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
            button.addActionListener(e -> pressButton(value));
        }

        frame.setVisible(true);
    }

    private void pressButton(String buttonValue) {
        if (Arrays.asList(operatorsButtons).contains(buttonValue)) {
            if (buttonValue.equals("=")) {
                if (A != null && operation != null) {
                    B = labelArea.getText().replace(A + operation, "");
                    if (B.isEmpty()) {
                        B = "0";
                    }
                    double aNumber = Double.parseDouble(A);
                    double bNumber = Double.parseDouble(B);

                    if (operation.equals("+")) {
                        labelArea.setText(checkNum(aNumber + bNumber));
                    }
                    else if (operation.equals("-")) {
                        labelArea.setText(checkNum(aNumber - bNumber));
                    }
                    else if (operation.equals("×")) {
                        labelArea.setText(checkNum(aNumber * bNumber));
                    }
                    else if (operation.equals("÷")) {
                        labelArea.setText(checkNum(aNumber / bNumber));
                    }
                    clearDisplay();
                }
            }
            else if ("+-×÷".contains(buttonValue)) {
                if (operation == null) {
                    A = labelArea.getText();
                    operation = buttonValue;
                    B = "";
                    labelArea.setText(A + operation);
                } else {
                    operation = buttonValue;
                    labelArea.setText(A + operation + (B != null ? B : ""));
                }
            }
        }
        else if (Arrays.asList(memoryButtons).contains(buttonValue)) {
            if (buttonValue.equals("MC")) {
                memory1 = "0";
                memory2 = "0";
                memory1Set = false;
                memory2Set = false;
            }
            else if (buttonValue.equals("M1")) {
                if (memory1Set && labelArea.getText().equals("0")) {
                    labelArea.setText(memory1);
                } else {
                    memory1 = labelArea.getText();
                    memory1Set = true;
                }
            }
            else if (buttonValue.equals("M2")) {
                if (memory2Set && labelArea.getText().equals("0")) {
                    labelArea.setText(memory2);
                } else {
                    memory2 = labelArea.getText();
                    memory2Set = true;
                }
            }
        }
        else if (Arrays.asList(actionsButtons).contains(buttonValue)) {
            if (buttonValue.equals("AC")) {
                clearDisplay();
                labelArea.setText("0");
            }
            else if (buttonValue.equals("<")) {
                String current = labelArea.getText();
                if (current.equals("Error")) {
                    clearDisplay();
                    labelArea.setText("0");
                } else if (current.length() <= 1) {
                    clearDisplay();
                    labelArea.setText("0");
                } else if (operation == null) {
                    String newText = current.substring(0, current.length() - 1);
                    if (newText.isEmpty() || newText.equals("-")) {
                        newText = "0";
                    }
                    labelArea.setText(newText);
                    A = newText;
                } else {
                    String prefix = A + operation;
                    if (current.equals(prefix)) {
                        operation = null;
                        labelArea.setText(A);
                    } else if (current.startsWith(prefix)) {
                        String currentB = current.substring(prefix.length());
                        if (currentB.length() <= 1) {
                            B = "";
                            labelArea.setText(prefix);
                        } else {
                            B = currentB.substring(0, currentB.length() - 1);
                            labelArea.setText(prefix + B);
                        }
                    }
                }
            }
            else if (buttonValue.equals("+/-")) {
                double num = Double.parseDouble(labelArea.getText());
                num *= -1;
                labelArea.setText(checkNum(num));
            }
            else if (buttonValue.equals("%")) {
                double num = Double.parseDouble(labelArea.getText());
                num /= 100;
                labelArea.setText(checkNum(num));
            }
            else if (buttonValue.equals("√")) {
                double num = Double.parseDouble(labelArea.getText());
                if (num >= 0) {
                    num = Math.sqrt(num);
                    labelArea.setText(checkNum(num));
                } else {
                    labelArea.setText("Error");
                }
            }
        }
        else {
            if (buttonValue.equals(".")) {
                if (operation == null) {
                    if (!labelArea.getText().contains(buttonValue)) {
                        labelArea.setText(labelArea.getText() + buttonValue);
                    }
                } else {
                    String currentB = labelArea.getText().replace(A + operation, "");
                    if (!currentB.contains(buttonValue)) {
                        if (currentB.isEmpty()) {
                            currentB = "0";
                        }
                        labelArea.setText(A + operation + currentB + buttonValue);
                    }
                }
            }
            else if ("0123456789".contains(buttonValue) || buttonValue.equals("00")) {
                String addValue = buttonValue.equals("00") ? "00" : buttonValue;
                if (operation == null) {
                    if (labelArea.getText().equals("0")) {
                        labelArea.setText(addValue.equals("00") ? "0" : addValue);
                    }
                    else {
                        labelArea.setText(labelArea.getText() + addValue);
                    }
                } else {
                    String currentB = labelArea.getText().replace(A + operation, "");
                    if (currentB.equals("0") || currentB.isEmpty()) {
                        currentB = addValue.equals("00") ? "0" : addValue;
                    } else {
                        currentB = currentB + addValue;
                    }
                    labelArea.setText(A + operation + currentB);
                }
            }
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
