import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class BattleshipGameGUI extends JFrame {
    private final JButton[][] buttons = new JButton[4][4];
    private final boolean[][] ships = new boolean[4][4];
    private int hits = 0;
    private int turns = 0;
    private final JLabel statusLabel = new JLabel("Hits: 0 | Turns: 0");
    private final JButton restartButton = new JButton("Restart");

    public BattleshipGameGUI() {
        setTitle("Battleship Game");
        setSize(400, 470); // Slightly increased height
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // Add some vertical spacing
        getContentPane().setBackground(Color.WHITE);

        // Grid panel
        JPanel gridPanel = new JPanel(new GridLayout(4, 4, 5, 5));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        Font buttonFont = new Font("Arial", Font.BOLD, 22); // Bigger font

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                JButton btn = new JButton();
                btn.setFont(buttonFont);
                buttons[row][col] = btn;

                int finalRow = row;
                int finalCol = col;

                btn.addActionListener(e -> handleClick(finalRow, finalCol));
                gridPanel.add(btn);
            }
        }

        add(gridPanel, BorderLayout.CENTER);

        // Bottom panel (status + restart)
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        bottomPanel.add(statusLabel, BorderLayout.NORTH);

        restartButton.setFont(new Font("Arial", Font.BOLD, 16));
        restartButton.setVisible(false);
        restartButton.addActionListener(e -> restartGame());
        bottomPanel.add(restartButton, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);

        placeRandomShips();
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    private void placeRandomShips() {
        Random rand = new Random();
        int placed = 0;

        while (placed < 4) {
            int row = rand.nextInt(4);
            int col = rand.nextInt(4);
            if (!ships[row][col]) {
                ships[row][col] = true;
                placed++;
            }
        }
    }

    private void handleClick(int row, int col) {
        JButton btn = buttons[row][col];

        if (btn.isEnabled()) {
            turns++;

            if (ships[row][col]) {
                btn.setBackground(Color.RED);
                btn.setText("Hit");
                ships[row][col] = false;
                hits++;
            } else {
                btn.setBackground(Color.LIGHT_GRAY);
                btn.setText("Miss");
            }

            btn.setEnabled(false);
            updateStatus();

            if (hits == 4) {
                JOptionPane.showMessageDialog(this, "Victory! You sank all ships in " + turns + " turns!");
                disableAllButtons();
                restartButton.setVisible(true);
            }
        }
    }

    private void updateStatus() {
        statusLabel.setText("Hits: " + hits + " | Turns: " + turns);
    }

    private void disableAllButtons() {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                buttons[row][col].setEnabled(false);
            }
        }
    }

    private void restartGame() {
        // Reset state
        hits = 0;
        turns = 0;
        restartButton.setVisible(false);
        statusLabel.setText("Hits: 0 | Turns: 0");

        // Reset board
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                ships[row][col] = false;
                JButton btn = buttons[row][col];
                btn.setText("");
                btn.setEnabled(true);
                btn.setBackground(null);
            }
        }

        placeRandomShips();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BattleshipGameGUI::new);
    }
}
