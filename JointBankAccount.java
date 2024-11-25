package LabTasks;

public class JointBankAccount {
	// Initial balance of the account
    private int balance = 50000;

    // Synchronise method to handle withdrawal to prevent race conditions
    public synchronized void withdraw(String user, int amount) {
        if (balance >= amount) {
            System.out.println(user + " is trying to withdraw " + amount);
            balance -= amount;
            System.out.println(user + " successfully withdrew " + amount);
            System.out.println("Remaining balance: " + balance);
        } else {
            System.out.println(user + " attempted to withdraw " + amount + " but insufficient funds.");
        }
    }

    public static void main(String[] args) {
        // Create an instance of the bank account
        JointBankAccount account = new JointBankAccount();

        // Create two threads representing two users
        Thread userA = new Thread(new Runnable() {
            @Override
            public void run() {
                account.withdraw("User A", 45000);
            }
        });

        Thread userB = new Thread(new Runnable() {
            @Override
            public void run() {
                account.withdraw("User B", 20000);
            }
        });

        // Start the threads
        userA.start();
        userB.start();

        try {
            // Wait for both threads to finish execution
            userA.join();
            userB.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
