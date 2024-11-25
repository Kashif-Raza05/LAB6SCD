package LabTasks;
public class Printer {
    private int pagesInTray = 10; // initial pages in the tray

    // Method to print the pages from the print job
    public synchronized void printPages(int pagesToPrint) {
        // Check if enough pages are available to print
        while (pagesInTray < pagesToPrint) {
            try {
                System.out.println("Not enough pages in tray. Waiting for more pages...");
                wait();  // Printing thread waits for notification
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Print the pages
        pagesInTray -= pagesToPrint;
        System.out.println("Printed " + pagesToPrint + " pages. Remaining pages in tray: " + pagesInTray);
    }

    // Method to add pages to the printer tray
    public synchronized void addPages(int pagesToAdd) {
        pagesInTray += pagesToAdd;
        System.out.println("Added " + pagesToAdd + " pages to the tray. Total pages in tray: " + pagesInTray);
        notify();  // Notify the printing thread that pages are available
    }
}


public class PrinterJobSimulation {
    public static void main(String[] args) {

        Printer printer = new Printer();  // Create a printer object

        // Thread for printing pages from the queue
        Thread printThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int pagesToPrint = 15;  // Job to print 15 pages
                printer.printPages(pagesToPrint);
            }
        });

        // Thread for adding pages to the tray
        Thread addPagesThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Adding pages in stages to simulate loading more pages
                    Thread.sleep(2000);  // Simulate delay before adding pages
                    printer.addPages(10); // Add 10 pages after some delay

                    Thread.sleep(2000);  // Another delay before adding more pages
                    printer.addPages(5);  // Add 5 more pages after a further delay
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Start both threads
        printThread.start();
        addPagesThread.start();

        try {
            // Wait for threads to finish execution
            printThread.join();
            addPagesThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}



