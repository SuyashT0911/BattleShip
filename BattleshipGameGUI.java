import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class BattleshipGameGUI extends JFrame {
    private final JButton[][] buttons = new JButton[4][4];
    private final boolean[][] ships = new boolean[4][4];
    private int hits = 0;
    private int turns = 0;
    private final JLabel statusLabel = new JLabel("Hits: 0 | Turns: 0");

    public BattleshipGameGUI() {
        setTitle("Battleship Game");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel to hold grid
        JPanel gridPanel = new JPanel(new GridLayout(4, 4));
        Font buttonFont = new Font("Arial", Font.BOLD, 20);

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

        // Bottom panel for status
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(statusLabel, BorderLayout.SOUTH);

        placeRandomShips(); // Randomly place 4 ships
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BattleshipGameGUI::new);
    }
}
