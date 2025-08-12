public class Main {
    public static void main(String[] args) {
        BankingService service = new BankingService();
        java.util.Scanner sc = new java.util.Scanner(System.in);

        while (true) {
            System.out.println("\n=== E-Banking Portal ===");
            System.out.println("1. Create Account");
            System.out.println("2. View Balance");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: service.createAccount(); break;
                case 2: service.viewBalance(); break;
                case 3: service.deposit(); break;
                case 4: service.withdraw(); break;
                case 5: System.out.println("Bye!"); System.exit(0);
                default: System.out.println("Invalid choice..!!");
            }
        }
    }
}
