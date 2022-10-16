import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Account {

    File accountFile = new File("C:\\Users\\Student\\Documents\\HomeWorkspace\\budget-monitoring-tool\\accounts.csv");
    Map<String, String> accountInfo = new HashMap<>();
    Map<String, String> accountName = new HashMap<>();
    Map<String, String> accountType = new HashMap<>();
    Map<String, BigDecimal> accountBalance = new HashMap<>();




    public Account() {

        try (Scanner currentAccounts = new Scanner(accountFile)){

            while (currentAccounts.hasNextLine()) {

                String currentLine = currentAccounts.nextLine();
                String[] accountInfoString = currentLine.split("\\|");
                BigDecimal balance = new BigDecimal(accountInfoString[3]);

                accountInfo.put(accountInfoString[0], currentLine);
                accountName.put(accountInfoString[0], accountInfoString[1]);
                accountType.put(accountInfoString[0], accountInfoString[2]);
                accountBalance.put(accountInfoString[0], balance);

            }
        } catch (FileNotFoundException e) {
            System.out.println("error");;
        }

    }

    // ##########################################################################################
    // viewAccount
    // - Loops through all accounts twice
    // - Prints all cash accounts first and debt accounts last
    // ##########################################################################################

    public void viewAccount() {

        System.out.println("-- Cash Accounts --");

        for ( Map.Entry<String, String> account : accountInfo.entrySet() ) {
            String[] accountInfoString = account.getValue().split("\\|");

            if (accountInfoString[2].equals("cash")) {
                System.out.println(accountInfoString[0] + ". " +  accountInfoString[1] + " (" + accountInfoString[2] + ")");
                System.out.println("    Amount: $" + accountInfoString[3] + "\n" );
            }
        }

        System.out.println("-- Debt Accounts --");

        for ( Map.Entry<String, String> account : accountInfo.entrySet() ) {
            String[] accountInfoString = account.getValue().split("\\|");

            if (accountInfoString[2].equals("debt")) {
                System.out.println(accountInfoString[0] + ". " + accountInfoString[1] + " (" + accountInfoString[2] + ")");
                System.out.println("   Amount: -$" + accountInfoString[3] + "\n");
            }
        }


    }


    public void openNewAccount(String accountNameInput, String accountTypeInput, String startingBalanceInput) {

        int lastAccountNumber = 1;
        for (String account : accountInfo.keySet()) {
            if (Integer.parseInt(account) >= lastAccountNumber) {
                lastAccountNumber = Integer.parseInt(account) + 1;
            }
        }
        String accountNumber = Integer.toString(lastAccountNumber);

        BigDecimal balance = validateMoneyInput(startingBalanceInput);
        String accountInfoString = Integer.toString(lastAccountNumber) + "|" + accountNameInput + "|"
                + accountTypeInput + "|" + balance;

        accountInfo.put(accountNumber, accountInfoString);
        accountName.put(accountNumber, accountNameInput);
        accountType.put(accountNumber, accountTypeInput);
        accountBalance.put(accountNumber, balance);


        try(FileWriter openNewAccount = new FileWriter(accountFile, true);
            BufferedWriter createAccount = new BufferedWriter(openNewAccount);
            PrintWriter out = new PrintWriter(createAccount)) {

            out.println(accountInfoString);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    // ###############################  deleteAccount  ##########################################
    // - Checks if account number input exists
    // - Updates all maps containing info about the accounts
    // - Repopulates the .csv file containing info about the accounts
    // ##########################################################################################

    public void deleteAccount(String accountNumberInput) {
            // Checks if account number exists
            if (accountInfo.containsKey(accountNumberInput)) {
                // Updates all maps
                accountInfo.remove(accountNumberInput);
                accountName.remove(accountNumberInput);
                accountType.remove(accountNumberInput);
                accountBalance.remove(accountNumberInput);
                // Repopulates .csv file
                try(FileWriter repopulateRecordsFile = new FileWriter(accountFile, false);
                    BufferedWriter updateRecords = new BufferedWriter(repopulateRecordsFile);
                    PrintWriter out = new PrintWriter(repopulateRecordsFile)) {

                    for (Map.Entry<String, String> accountInfo : accountInfo.entrySet()) {
                        out.println(accountInfo.getValue());
                    }

                } catch (IOException e) {
                    System.out.println("Error deleting account");
                    e.printStackTrace();
                }

            }

        }


    // ##############################  updateAccount  ###########################################
    // - Takes inputs for number, name, type, and balance of an account
    // - if account exists, it will update all maps relating to account info and repopulate the .csv file
    // ##########################################################################################

        public void updateAccount(String accountNumber, String accountNameInput, String accountTypeInput, String accountBalanceInput) {

        if(accountInfo.containsKey(accountNumber)) {

            accountName.put(accountNumber, accountNameInput);
            accountType.put(accountNumber, accountTypeInput);
            accountBalance.put(accountNumber, validateMoneyInput(accountBalanceInput));

            String newBalance = validateMoneyInput(accountBalanceInput).toString();
            String accountInfoString = accountNumber + "|" + accountNameInput + "|" + accountTypeInput + "|" + newBalance;
            accountInfo.put(accountNumber, accountInfoString);

            try(FileWriter repopulateRecordsFile = new FileWriter(accountFile, false);
                BufferedWriter updateRecords = new BufferedWriter(repopulateRecordsFile);
                PrintWriter out = new PrintWriter(repopulateRecordsFile)) {

                for (Map.Entry<String, String> accountInfo : accountInfo.entrySet()) {
                    out.println(accountInfo.getValue());
                }

            } catch (IOException e) {
                System.out.println("Error updating account");
                e.printStackTrace();
            }
        }

        }






    public BigDecimal validateMoneyInput(String input) {
        int error;
        BigDecimal depositAttempt = new BigDecimal("0");

        try {
            depositAttempt = new BigDecimal(input);
            error = 0;
        } catch (Exception e) {
            System.out.println("\nInvalid input. Balance set to 0");
            error = 1;
        }

        if (error == 0) {
            return depositAttempt;
        } else {
            return new BigDecimal("0");
        }
    }










}
