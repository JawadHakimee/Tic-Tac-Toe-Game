import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class TicTacToe implements ActionListener {
    private final JFrame frame;
    private final JPanel panel;
    private final JButton[] buttons = new JButton[9];
    private boolean xTurn = true; // true = User's turn (X), false = Computer's turn (O)
    private boolean isDarkMode = false; // Track whether dark mode is enabled
    private boolean isComputerMode = false; // Check if playing with computer
    private final JButton modeToggleButton, gameModeButton; // Buttons for mode toggles
    private final Random rand;

    // Variables to track the first and second player
    private final String firstPlayer = "";
    private final String secondPlayer = "";

    public TicTacToe() {
        frame = new JFrame("Tic-Tac-Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create the buttons
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 40));
            buttons[i].addActionListener(this);
            panel.add(buttons[i]);
        }

        // Add mode toggle button and game mode button
        modeToggleButton = new JButton("Toggle Dark/Light Mode");
        modeToggleButton.addActionListener(e -> toggleMode());
        gameModeButton = new JButton("Play with Partner");
        gameModeButton.addActionListener(e -> toggleGameMode());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 2));
        bottomPanel.add(gameModeButton);
        bottomPanel.add(modeToggleButton);

        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.setSize(400, 450); // Adjusted size for buttons at the bottom
        frame.setVisible(true);
        
        rand = new Random();

        updateMode(); // Apply initial mode
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();

        if (button.getText().equals("") && !isGameOver()) {
            if (xTurn) {
                button.setText("X");
            } else {
                button.setText("O");
            }
            button.setEnabled(false);
            xTurn = !xTurn;

            checkForWinner();

            if (isComputerMode && !xTurn && !isGameOver()) {
                computerMove();
            }
        }
    }

    public void checkForWinner() {
        // Check rows, columns, and diagonals
        for (int i = 0; i < 9; i += 3) {
            if (buttons[i].getText().equals(buttons[i + 1].getText()) && buttons[i].getText().equals(buttons[i + 2].getText()) && !buttons[i].isEnabled()) {
                switch (buttons[i].getText()) {
                    case "X":
                        JOptionPane.showMessageDialog(frame, firstPlayer + " (First Player) wins!");
                        break;
                    case "O":
                        JOptionPane.showMessageDialog(frame, secondPlayer + " (Second Player) wins!");
                        break;
                }
                resetGame();
                return;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (buttons[i].getText().equals(buttons[i + 3].getText()) && buttons[i].getText().equals(buttons[i + 6].getText()) && !buttons[i].isEnabled()) {
                switch (buttons[i].getText()) {
                    case "X":
                        JOptionPane.showMessageDialog(frame, firstPlayer + " (First Player) wins!");
                        break;
                    case "O":
                        JOptionPane.showMessageDialog(frame, secondPlayer + " (Second Player) wins!");
                        break;
                }
                resetGame();
                return;
            }
        }
        if (buttons[0].getText().equals(buttons[4].getText()) && buttons[0].getText().equals(buttons[8].getText()) && !buttons[0].isEnabled()) {
            switch (buttons[0].getText()) {
                case "X":
                    JOptionPane.showMessageDialog(frame, firstPlayer + " (First Player) wins!");
                    break;
                case "O":
                    JOptionPane.showMessageDialog(frame, secondPlayer + " (Second Player) wins!");
                    break;
            }
            resetGame();
            return;
        }
        if (buttons[2].getText().equals(buttons[4].getText()) && buttons[2].getText().equals(buttons[6].getText()) && !buttons[2].isEnabled()) {
            switch (buttons[2].getText()) {
                case "X":
                    JOptionPane.showMessageDialog(frame, firstPlayer + " (First Player) wins!");
                    break;
                case "O":
                    JOptionPane.showMessageDialog(frame, secondPlayer + " (Second Player) wins!");
                    break;
            }
            resetGame();
            return;
        }

        // Check for tie
        boolean tie = true;
        for (int i = 0; i < 9; i++) {
            if (buttons[i].isEnabled()) {
                tie = false;
                break;
            }
        }
        if (tie) {
            JOptionPane.showMessageDialog(frame, "Tie game!");
            resetGame();
        }
    }

    private boolean isGameOver() {
        // Check if the game is over
        for (int i = 0; i < 9; i++) {
            if (buttons[i].isEnabled()) {
                return false;
            }
        }
        return true;
    }

    private void computerMove() {
        // Computer makes a random valid move
        if (isGameOver()) return;

        int index = rand.nextInt(9);
        while (!buttons[index].getText().equals("")) {
            index = rand.nextInt(9);
        }

        buttons[index].setText("O");
        buttons[index].setEnabled(false);
        xTurn = true;
        checkForWinner();
    }

    public void resetGame() {
        for (int i = 0; i < 9; i++) {
            buttons[i].setText("");
            buttons[i].setEnabled(true);
        }
        xTurn = true; // X starts first
    }

    private void toggleMode() {
        isDarkMode = !isDarkMode;
        updateMode();
    }

    private void updateMode() {
        if (isDarkMode) {
            frame.getContentPane().setBackground(Color.DARK_GRAY);
            panel.setBackground(Color.DARK_GRAY);
            gameModeButton.setBackground(Color.BLACK);
            gameModeButton.setForeground(Color.WHITE);
            modeToggleButton.setBackground(Color.BLACK);
            modeToggleButton.setForeground(Color.WHITE);
            for (JButton button : buttons) {
                button.setBackground(Color.GRAY);
                button.setForeground(Color.WHITE);
            }
        } else {
            frame.getContentPane().setBackground(Color.WHITE);
            panel.setBackground(Color.WHITE);
            gameModeButton.setBackground(Color.LIGHT_GRAY);
            gameModeButton.setForeground(Color.BLACK);
            modeToggleButton.setBackground(Color.LIGHT_GRAY);
            modeToggleButton.setForeground(Color.BLACK);
            for (JButton button : buttons) {
                button.setBackground(Color.LIGHT_GRAY);
                button.setForeground(Color.BLACK);
            }
        }
    }

    private void toggleGameMode() {
        if (isComputerMode) {
            isComputerMode = false;
            gameModeButton.setText("Play with Partner");
        } else {
            isComputerMode = true;
            gameModeButton.setText("Play with Computer");
        }
        resetGame();
    }

    public static void main(String[] args) {
        TicTacToe TicTacToe = new TicTacToe(); /*ticTacToe*/
    }
}
