package ui;

import model.Event;
import model.EventLog;


// Prints what is in the LogPrinter to console
public class LogPrinter extends Thread {
    private EventLog finalPrint;

    public LogPrinter(EventLog finalPrint) {
        this.finalPrint = finalPrint;
        printLog();
    }

    // EFFECTS: Iterates through the events in the EventLog and prints out their string
    public void printLog() {
        for (Event next : finalPrint) {
            System.out.println(next.toString() + "\n\n");
        }
    }
}
