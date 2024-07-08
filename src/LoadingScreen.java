import java.awt.BorderLayout;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class LoadingScreen {
    private Runnable onComplete;

    public LoadingScreen(Runnable onComplete) {
        this.onComplete = onComplete;
    }

    public void showLoadingScreen() {
        JFrame frame = new JFrame("Loading...");
        frame.setSize(300, 100);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        panel.add(progressBar, BorderLayout.CENTER);

        JLabel label = new JLabel("Loading: 0%", JLabel.CENTER);
        panel.add(label, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int counter = 0;

            @Override
            public void run() {
                counter++;
                progressBar.setValue(counter);
                label.setText("Loading: " + counter + "%");
                if (counter >= 100) {
                    timer.cancel();
                    frame.dispose();
                    onComplete.run();
                }
            }
        }, 0, 100);
    }
}
