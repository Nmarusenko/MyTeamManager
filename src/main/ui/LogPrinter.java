package ui;

import model.Event;
import model.EventLog;

public class LogPrinter extends Thread {
    private EventLog finalPrint;

    public LogPrinter(EventLog finalPrint) {
        this.finalPrint = finalPrint;
        printLog();
    }

    public void printLog() {
        for (Event next : finalPrint) {
            System.out.println(next.toString() + "\n\n");
        }
    }
}
