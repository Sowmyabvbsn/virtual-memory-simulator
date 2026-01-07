import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * A custom JFrame that displays a bar chart comparing page fault counts
 * across different algorithms.
 */
public class BarChartFrame extends JFrame {
    private int[] faultCounts;
    private String[] algorithms;

    /**
     * Creates a bar chart frame to display algorithm comparison.
     * 
     * @param algorithms Array of algorithm names
     * @param faultCounts Array of corresponding fault counts
     */
    public BarChartFrame(String[] algorithms, int[] faultCounts) {
        this.algorithms = algorithms;
        this.faultCounts = faultCounts;

        setTitle("Algorithm Comparison (Page Fault Counts)");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = 100;
        int maxHeight = 300;
        int max = Arrays.stream(faultCounts).max().orElse(1);
        int baseY = 380;

        int x = 80;
        Color[] colors = {new Color(52, 152, 219), new Color(46, 204, 113), 
                         new Color(155, 89, 182), new Color(241, 196, 15)};

        // Draw title
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.drawString("Page Fault Comparison", 180, 50);

        // Draw bars
        for (int i = 0; i < faultCounts.length; i++) {
            int barHeight = (int) ((faultCounts[i] / (double) max) * maxHeight);
            
            // Draw shadow
            g2d.setColor(Color.GRAY);
            g2d.fillRect(x + 3, baseY - barHeight + 3, width, barHeight);
            
            // Draw bar
            g2d.setColor(colors[i % colors.length]);
            g2d.fillRect(x, baseY - barHeight, width, barHeight);

            // Draw border
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, baseY - barHeight, width, barHeight);
            
            // Draw algorithm name
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            g2d.drawString(algorithms[i], x + 30, baseY + 20);
            
            // Draw fault count
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.drawString(String.valueOf(faultCounts[i]), x + 35, baseY - barHeight - 10);

            x += 130;
        }

        // Draw axis
        g2d.setColor(Color.BLACK);
        g2d.drawLine(50, baseY, 550, baseY);
        g2d.drawLine(50, baseY, 50, 80);
    }
}
