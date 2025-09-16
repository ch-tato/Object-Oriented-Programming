import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class ClockGUI extends JPanel {
    // === Inner Class NumberDisplay ===
    public class NumberDisplay {
        private int curr;
        private int max;

        public NumberDisplay(int maxValue) {
            curr = 0;
            max = maxValue;
        }

        public void setValue(int value) {
            if (value >= 0 && value < max) curr = value;
        }

        public int getValue() {
            return curr;
        }

        public String getDisplay() {
            if (curr < 10) return "0" + curr;
            else return "" + curr;
        }

        public void increment() {
            curr = (curr + 1) % max;
        }
    }

    // === Inner Class ClockDisplay ===
    public class ClockDisplay {
        private NumberDisplay second, minute, hour, day, month, year;
        private String currTime, currDate, currInfo;

        public class Temp {
            private int min = 28, max = 33;
            private int value;
            private Random random = new Random();

            public Temp() {
                value = random.nextInt(max - min + 1) + min;
            }

            public int getValue() {
                value = random.nextInt(max - min + 1) + min;
                return value;
            }
        }

        private Temp tempValue;
        private int temperature;

        public ClockDisplay() {
            second = new NumberDisplay(60);
            minute = new NumberDisplay(60);
            hour = new NumberDisplay(24);
            month = new NumberDisplay(12);
            day = new NumberDisplay(31);
            year = new NumberDisplay(9999);
            tempValue = new Temp();
            temperature = tempValue.getValue();
            setTimeStr();
        }

        private void setTimeStr() {
            currTime = hour.getDisplay() + ":" + minute.getDisplay() + ":" + second.getDisplay();
            currDate = day.getDisplay() + "/" + month.getDisplay() + "/" + year.getDisplay();
            currInfo = currTime + "\n" + currDate + "\n" + temperature + "°C";
        }

        public void setTime(int hh, int mm, int ss, int dd, int mo, int yy, int t) {
            hour.setValue(hh);
            minute.setValue(mm);
            second.setValue(ss);
            day.setValue(dd);
            month.setValue(mo);
            year.setValue(yy);
            temperature = t;
            setTimeStr();
        }

        public String getTime() {
            return currInfo;
        }

        public void timeIncrement() {
            temperature = tempValue.getValue();
            second.increment();
            if (second.getValue() == 0) {
                minute.increment();
                if (minute.getValue() == 0) {
                    hour.increment();
                    if (hour.getValue() == 0) {
                        day.increment();
                        if (day.getValue() == 0) {
                            month.increment();
                            if (month.getValue() == 0) {
                                year.increment();
                            }
                        }
                    }
                }
            }
            setTimeStr();
        }
    }

    // === GUI Part ===
    private ClockDisplay clock;

    public ClockGUI() {
        clock = new ClockDisplay();
        clock.setTime(15, 18, 55, 16, 9, 2025, 30);

        // Timer update setiap 1 detik
        Timer timer = new Timer(1000, e -> {
            clock.timeIncrement();
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);

        g.setColor(Color.GREEN);
        g.setFont(new Font("Consolas", Font.BOLD, 40));
        g.drawString(clock.currTime, 50, 80);

        g.setFont(new Font("Consolas", Font.PLAIN, 30));
        g.drawString(clock.currDate, 50, 130);

        g.setFont(new Font("Consolas", Font.PLAIN, 25));
        g.drawString(clock.temperature + "°C", 50, 180);
    }

    // === Main Method ===
    public static void main(String[] args) {
        JFrame frame = new JFrame("Digital Clock");
        ClockGUI panel = new ClockGUI();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.add(panel);
        frame.setVisible(true);
    }
}
