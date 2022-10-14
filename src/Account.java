import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Account {

    File accountFile = new File("C:\\Users\\Student\\Documents\\HomeWorkspace\\budget-monitoring-tool\\accounts.csv");
    Map<String, String> accountName = new HashMap<>();
    Map<String, String> accountInfo = new HashMap<>();
    Map<String, BigDecimal> accountBalance = new HashMap<>();



    public Account() {

        try (Scanner currentAccounts = new Scanner(accountFile)){

            while (currentAccounts.hasNextLine()) {

                String currentLine = currentAccounts.nextLine();
                String[] accountInfoString = currentLine.split("\\|");
                BigDecimal balance = new BigDecimal(accountInfoString[3]);

                accountInfo.put(accountInfoString[0], currentLine);
                accountName.put(accountInfoString[0], accountInfoString[1]);
                accountBalance.put(accountInfoString[0], balance);





            }

        } catch (FileNotFoundException e) {
            System.out.println("error");;
        }



    }

    public void openNewAccount() {




        try(FileWriter openNewAccount = new FileWriter(accountFile, true);

            BufferedWriter createAccount = new BufferedWriter(openNewAccount);
            PrintWriter out = new PrintWriter(createAccount)) {




            out.println("|fye|foe|fum");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }










}
