package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class AccountingLedgerApp {
    //formatting date and time also making scanner
    static DateTimeFormatter dateStamp = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    static DateTimeFormatter timeStamp = DateTimeFormatter.ofPattern("HH:mm");
    static Scanner myScanner = new Scanner(System.in);

    public static void main(String[] args) {
        //creating an array
        ArrayList<Transaction> transactions = getTransaction();
        //switch statements
        boolean homeRunning = true;
        while(homeRunning){
            //taking main menu input and converting it to lowercase
            String mainMenuChoice = homeScreen().toLowerCase();
            switch(mainMenuChoice){
                case "d":
                    //calling file writer method to write the addDeposit method
                    fileWriter(addDeposit());
                    //updates the Array
                    transactions = getTransaction();
                    //ask the user to return to the home screen
                    System.out.println("The deposit was added! Press Y to return to the home screen");
                    myScanner.nextLine();
                    break;
                case "p":
                    //calling file writer method to write the makePayment method
                    fileWriter(makePayment());
                    //updates the Array
                    transactions = getTransaction();
                    //ask the user to return to the home screen
                    System.out.println("The payment was added! Press Y to return to the home screen");
                    myScanner.nextLine();
                    break;
                case "l":
                    boolean ledgerRunning = true;
                    while(ledgerRunning){
                        //taking ledger input and converting to lowercase
                        String ledgerChoice = ledgerScreen().toLowerCase();
                        switch(ledgerChoice) {
                            case "a":
                                //calls allLedger method
                                allLedger(transactions);
                                //ask the user to return to the ledger screen
                                System.out.println("\nWould you like to return to the ledger?");
                                myScanner.nextLine();
                                break;
                            case "d":
                                //calls allDeposit method
                                allDeposit(transactions);
                                //ask the user to return to the ledger screen
                                System.out.println("Would you like to return to the ledger?");
                                myScanner.nextLine();
                                break;
                            case "p":
                                //calls allPayment method
                                allPayment(transactions);
                                //ask the user to return to the ledger screen
                                System.out.println("Would you like to return to the ledger?");
                                myScanner.nextLine();
                                break;
                            case "r":
                                boolean reportRunning = true;
                                while(reportRunning) {
                                    //take report input
                                    int reportChoice = reportScreen();
                                    switch (reportChoice) {
                                        case 1:
                                            //calls month to date method
                                            monthToDate(transactions);
                                            System.out.println("Would you like to return to the Report?");
                                            myScanner.nextLine();
                                            break;
                                        case 2:
                                            //calls previous month method
                                            previousMonth(transactions);
                                            System.out.println("Would you like to return to the Report?");
                                            myScanner.nextLine();
                                            break;
                                        case 3:
                                            //calls year to date method
                                            yearToDate(transactions);
                                            System.out.println("Would you like to return to the Report?");
                                            myScanner.nextLine();
                                            break;
                                        case 4:
                                            //calls previous year method
                                            previousYear(transactions);
                                            System.out.println("Would you like to return to the Report?");
                                            myScanner.nextLine();
                                            break;
                                        case 5:
                                            //calls search vendor method
                                            searchVendor(transactions);
                                            System.out.println("Would you like to return to the Report?");
                                            myScanner.nextLine();
                                            break;
                                        case 0:
                                            //returns to ledger screen
                                            System.out.println("Returning to Ledger Screen");
                                            reportRunning = false;
                                            break;
                                    }
                                }
                                break;
                            case "h":
                                //returns to home screen
                                System.out.println("Returning to Home Screen");
                                ledgerRunning = false;
                                break;
                        }
                    }
                    break;
                case "x":
                    //exits the app
                    System.out.println("Goodbye, See you again!");
                    homeRunning = false;
                    break;
            }
        }

    }
    //method that creates array
    public static ArrayList<Transaction> getTransaction(){
        //creates an array
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();
        try{
            //opens BufferedReader to read file transaction.csv
            BufferedReader transactionCsv = getFileReader("transaction.csv");
            transactionCsv.readLine();
            String line;
            //while loop to keep reading until it reaches null
            while((line = transactionCsv.readLine()) != null){
                //splits into strings by |
                String[] transactionParts = line.split("\\|");
                //skips the header
                if(transactionParts[0].trim().equalsIgnoreCase("date")){
                    continue;
                }
                //breaking string into parts and parse into correct format
                LocalDate date = LocalDate.parse(transactionParts[0].trim());
                LocalTime time = LocalTime.parse(transactionParts[1].trim());
                String description = transactionParts[2].trim();
                String vendor = transactionParts[3].trim();
                double amount = Double.parseDouble(transactionParts[4].trim());
                //creates a new instance of transaction class and adding to it
                Transaction newTransaction = new Transaction(date,time,description,vendor,amount);
                transactions.add(newTransaction);
            }
            //sorts the .csv in reverse order because as you add new deposits or payments it adds to the bottom of the list
            Collections.reverse(transactions);
            //closes the BufferedReader and return the output
            transactionCsv.close();
            return transactions;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    //method to access file reader
    public static BufferedReader getFileReader(String fileName){
        try{
            FileReader fileReader = new FileReader("src/main/resources/" + fileName);
            return new BufferedReader(fileReader);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
    //method to call home screen
    public static String homeScreen(){
        //creating string choice to store user input
        String choice = "";
        //while loop to get user input for home screen
        while(true) {
            System.out.println("-------------------------------------");
            System.out.println("-------------Home Screen-------------");
            System.out.println("-------------------------------------");
            System.out.println("[D] Add Deposit\n[P] Make Payment(Debit)\n[L] Ledger\n[X] Exit");
            System.out.println("What would you like to do?");
            choice = myScanner.nextLine();
            break;
        }
        return choice;
    }
    //method to access file writer
    public static void fileWriter(String transactions){
        try{
            FileWriter fileWriter = new FileWriter("src/main/resources/transaction.csv",true);
            BufferedWriter bufWriter = new BufferedWriter(fileWriter);
            bufWriter.write(transactions);
            bufWriter.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //method to add a deposit
    public static String addDeposit(){
        //get the current date time
        LocalDateTime dateTime = LocalDateTime.now();
        //asking and recieving input
        System.out.println("Enter the description of the deposit:");
        String description = myScanner.nextLine().trim();
        //asking and recieving input
        System.out.println("Enter the vendor of the deposit:");
        String vendor = myScanner.nextLine().trim();
        //asking and recieving input
        System.out.println("Enter the amount of the deposit:");
        double amount = myScanner.nextDouble();
        //eats the empty line
        myScanner.nextLine();
        //combine all user inputs into string and returning it
        String transactions = dateTime.format(dateStamp) + " | " + dateTime.format(timeStamp) + " | " + description +" | "+ vendor + " | " + amount + "\n";
        return transactions;
    }
    //method to add payment
    public static String makePayment(){
        //get current date and time
        LocalDateTime dateTime = LocalDateTime.now();
        //asking and recieving input
        System.out.println("Enter the description of the payment:");
        String description = myScanner.nextLine().trim();
        //asking and recieving input
        System.out.println("Enter the vendor of the payment:");
        String vendor = myScanner.nextLine().trim();
        //asking and recieving input
        System.out.println("Enter the amount of the payment:");
        double amount = -1 * Math.abs(myScanner.nextDouble());
        //eats the empty line
        myScanner.nextLine();
        //combining user input into string and returning it
        String listOfPayments = dateTime.format(dateStamp) + " | " + dateTime.format(timeStamp) + " | " + description +" | "+ vendor + " | " + amount + "\n";
        return listOfPayments;

    }
    //method for the ledger screen
    public static String ledgerScreen(){
        //creating string choice to store user input
        String choice = "";
        //while loop to print the screen and store input
        while(true) {
            System.out.println("-------------------------------------");
            System.out.println("-------------Ledger Screen-----------");
            System.out.println("-------------------------------------");
            System.out.println("[A] All Ledgers\n[D] Show Deposits\n[P] Show Payments\n[R] Reports \n[H] Home");
            System.out.println("What would you like to do?");
            choice = myScanner.nextLine();
            break;
        }
        return choice;
    }
    //method to print all ledger
    public static void allLedger(ArrayList<Transaction> transactions){
        System.out.println("Here are all the Ledgers: ");
        //for loop to display all ledger in certain format
        for(int i =0; i<transactions.size();i++){
            Transaction t = transactions.get(i);
            System.out.printf("%tF %tT %s %s $%.2f%n",t.getDate(),t.getTime(),t.getDescription(),t.getVendor(),t.getAmount());
        }
    }
    //method to print all deposits
    public static void allDeposit(ArrayList<Transaction> transactions){
        System.out.println("Here are all the Deposits");
        //create string output to store the string
        String output = "";
        //for loop to go through the transaction list
        for(int i =0; i<transactions.size();i++){
            Transaction t = transactions.get(i);
            //if statement to print deposits only, in certain format
            if(t.getAmount() > 0) {
                output += String.format("%tF %tT %s %s $%.2f%n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
            }
        }
        //if else statement to see if there is any deposits
        if(output.equals("")){
            System.out.println("No deposits available");
        }else{
            System.out.println(output);
        }
    }
    //method to print all payments
    public static void allPayment(ArrayList<Transaction> transactions){
        System.out.println("Here are all the Payments");
        //create string output
        String output = "";
        //loop to read through transaction list
        for(int i =0; i<transactions.size();i++){
            Transaction t = transactions.get(i);
            //if statement to print payments only, in certain format
            if(t.getAmount() < 0) {
                output += String.format("%tF %tT %s %s $%.2f%n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
            }
        }
        //if else statement to see if there is any payments
        if(output.equals("")){
            System.out.println("No payments available");
        }else{
            System.out.println(output);
        }
    }
    //method to call report screen
    public static int reportScreen(){
        //setting choice to an invalid int
        int choice = -1;
        //while loop to print screen and receive input
        while(true) {
            System.out.println("-------------------------------------");
            System.out.println("-------------Report Screen-----------");
            System.out.println("-------------------------------------");
            System.out.println("[1] Month to date\n[2] Previous Month\n[3] Year to Date\n[4] Previous year\n[5] Search by Vendor\n[0] Back to Ledger");
            System.out.println("What would you like to do?");
            choice = myScanner.nextInt();
            //eats empty line
            myScanner.nextLine();
            break;
        }
        return choice;
    }
    //method to call month to date
    public static void monthToDate(ArrayList<Transaction> transactions){
        //gets local time of today
        LocalDate today = LocalDate.now();
        //gets the first day of the month
        LocalDate firstOfTheMonth = today.withDayOfMonth(1);
        System.out.println("Here are the Month to Date report");
        String output = "";
        for(int i = 0; i < transactions.size();i++){
            //getting the transaction array and date
            Transaction t = transactions.get(i);
            LocalDate targetDate = t.getDate();
            //checks to show everything from the first day of the month to current day to include today
            if(!targetDate.isAfter(today) && !targetDate.isBefore(firstOfTheMonth)){
                output += String.format("%tF %tT %s %s $%.2f%n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
            }
        }
        //if there is nothing then it will show no payments
        if(output.equals("")){
            System.out.println("No payments available");
        }else{
            System.out.println(output);
        }
    }
    //method to call previous month
    public static void previousMonth(ArrayList<Transaction> transactions){
        //gets current date
        LocalDate today = LocalDate.now();
        //gets the first day of last month
        LocalDate startPrevMonth = today.minusMonths(1).withDayOfMonth(1);
        //gets the last day of the month
        LocalDate endPrevMonth = today.withDayOfMonth(1).minusDays(1);
        System.out.println("Here are the Previous Months report");
        String output = "";
        for(int i = 0; i < transactions.size();i++){
            //getting the transaction array and date
            Transaction t = transactions.get(i);
            LocalDate targetDate = t.getDate();
            //checks to show everything from the first day of the month to today
            if(!targetDate.isBefore(startPrevMonth) && !targetDate.isAfter(endPrevMonth)){
                output += String.format("%tF %tT %s %s $%.2f%n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
            }
        }
        //if there is nothing then it will show no payments
        if(output.equals("")){
            System.out.println("No payments available");
        }else{
            System.out.println(output);
        }
    }
    //method to call year to date
    public static void yearToDate(ArrayList<Transaction> transactions){
        //gets local time of today
        LocalDate today = LocalDate.now();
        //gets the first day of the year
        LocalDate firstOfTheYear = today.withDayOfYear(1);
        System.out.println("Here are the Year to Date report");
        String output = "";
        for(int i = 0; i < transactions.size();i++){
            //getting the transaction array and date
            Transaction t = transactions.get(i);
            LocalDate targetDate = t.getDate();
            //checks to show everything from the first day of the year to last day of the year
            if(!targetDate.isBefore(firstOfTheYear) && !targetDate.isAfter(today)){
                output += String.format("%tF %tT %s %s $%.2f%n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
            }
        }
        //if there is nothing then it will show no payments
        if(output.equals("")){
            System.out.println("No payments available");
        }else{
            System.out.println(output);
        }
    }
    //method to call previous year
    public static void previousYear(ArrayList<Transaction> transactions){
        LocalDate today = LocalDate.now();
        //gets the first day of last year
        LocalDate startPrevYear = today.minusYears(1).withDayOfYear(1);
        //gets the last day of last year
        LocalDate endPrevYear = today.withDayOfYear(1).minusDays(1);
        System.out.println("Here are the Previous Year report");
        String output = "";
        for(int i = 0; i < transactions.size();i++){
            //getting the transaction array and date
            Transaction t = transactions.get(i);
            LocalDate targetDate = t.getDate();
            //checks to show everything from the first day of the previous year to end of last year
            if(!targetDate.isBefore(startPrevYear) && !targetDate.isAfter(endPrevYear)){
                output += String.format("%tF %tT %s %s $%.2f%n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
            }
        }
        //if there is nothing then it will show no payments
        if(output.equals("")){
            System.out.println("No payments available");
        }else{
            System.out.println(output);
        }
    }
    //method to call search by vendor
    public static void searchVendor(ArrayList<Transaction> transactions){
        //asking for user input for vendor and storing it
        System.out.println("What vendor would you like to look up");
        String vendor = myScanner.nextLine();
        System.out.println("Here are the report by vendor:" + vendor);
        String output = "";
        for(int i = 0; i < transactions.size();i++){
            //getting the transaction array
            Transaction t = transactions.get(i);
            //checks to see if user input is equal to anything in array
            if(t.getVendor().equalsIgnoreCase(vendor)){
                output += String.format("%tF %tT %s %s $%.2f%n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
            }
        }
        //if there is nothing then it will show no payments
        if(output.equals("")){
            System.out.println("No payments available");
        }else{
            System.out.println(output);
        }
    }

}
