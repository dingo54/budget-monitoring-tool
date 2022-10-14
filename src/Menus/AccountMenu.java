package Menus;

public class AccountMenu extends Menu{

    public AccountMenu() { }

    @Override
    public void displayMenu() {
        System.out.println("\nWhat would you like to do: ");
        System.out.println("(1) View Accounts\n(2) Create Account\n(3) Delete Account\n" +
                           "(4) Update Value" +
                           "Enter the number of your choice: ");
    }


}
