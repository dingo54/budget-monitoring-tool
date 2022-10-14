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

    public void openNewAccount(String accountNameInput, String accountTypeInput, String startingBalanceInput) {

        int numberOfAccounts = 0;
        for (String account : accountInfo.keySet()) {
            numberOfAccounts++;
        }
        String accountNumber = Integer.toString(numberOfAccounts);

        BigDecimal balance = validateMoneyInput(startingBalanceInput);
        String accountInfoString = Integer.toString(numberOfAccounts+1) + "|" + accountNameInput + "|"
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
