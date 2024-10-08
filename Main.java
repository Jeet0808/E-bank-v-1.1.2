package ebank;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
public class Main {
    public static void main(String[] args) {
        // Stylish Welcome Message
        System.out.println("╔═════════════════════════════════════════════════════════════════╗");
        System.out.println("║        ✨  You are lucky that you are going to witness  ✨      ║");
        System.out.println("║    🌍   Welcome to the World's First E-Bank by JLSS & Co.   🌍  ║");
        System.out.println("╚═════════════════════════════════════════════════════════════════╝");

        // Enhanced Loading Bar
        System.out.print("\nJust a moment, we're setting things up for you");
        System.out.print(" ");

        CountDownLatch latch = new CountDownLatch(1);
        Interviewwait.startInterviewwaitTimer(latch);

        try {
            latch.await(); // Await until the loading bar finishes
        } catch (InterruptedException e) {
            System.out.println("Process interrupted.");
        }

        // Proceed to the main application
        Bank bank = new Bank();
        bank.AppStart();
    }

    static class Interviewwait {
        public static void startInterviewwaitTimer(CountDownLatch latch) {
            Timer timer = new Timer();
            int delay = 0;
            int period = 150; // Delay between each dot
            int[] counter = {20}; // Adjust the length of the loading bar

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if (counter[0] > 0) {
                        System.out.print("."); // Print dots
                        counter[0]--;
                    } else {
                        System.out.println("✅ Setup Complete!");
                        timer.cancel();
                        latch.countDown(); // Release latch when done
                    }
                }
            };

            // Schedule the task with the timer
            timer.scheduleAtFixedRate(task, delay, period);
        }
    }
}

// now the task n is to coonect rest api with app

