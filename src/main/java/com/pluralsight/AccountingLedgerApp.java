package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class AccountingLedgerApp {
    static DateTimeFormatter dateTimeStamp = DateTimeFormatter.ofPattern("yyyy-MM-dd | HH:mm |");
    static Scanner myScanner = new Scanner(System.in);
    public static void main(String[] args) {
        ArrayList<Transaction> transactions = getTransaction();
        boolean homeRunning = true;
        boolean ledgerRunning = true;
        boolean reportRunning = true;
        while(homeRunning){
            String mainMenuChoice = homeScreen().toLowerCase();
            switch(mainMenuChoice){
                case "d":
                    /*fileWriter(addDeposit());*/
                    break;
                case "p":
                    /*fileWriter(makePayment());*/
                    break;
                case "l":
                    while(ledgerRunning){
                        String ledgerChoice = ledgerScreen();
                        switch(ledgerChoice) {
                            case "a":
                                System.out.println("Here are all the ledgers:");

                                break;
                            case "d":
                                break;
                            case "p":
                                break;
                            case "r":
                                while(reportRunning) {
                                    int reportChoice = reportScreen();
                                    switch (reportChoice) {
                                        case 1:
                                            break;
                                        case 2:
                                            break;
                                        case 3:
                                            break;
                                        case 4:
                                            break;
                                        case 5:
                                            break;
                                        case 0:
                                            System.out.println("Returning to Ledger Screen");
                                            reportRunning = false;
                                            break;
                                    }
                                }
                                break;
                            case "h":
                                System.out.println("Returning to Home Screen");
                                ledgerRunning = false;
                                break;
                        }
                    }
                    break;
                case "x":
                    System.out.println("Goodbye, See you again!");
                    homeRunning = false;
                    break;
            }
        }

    }
    public static ArrayList<Transaction> getTransaction(){
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();
        BufferedReader transactionCsv = getFileReader("transaction.csv");
        try{
            String line;
            while((line = transactionCsv.readLine()) != null){
                String[] transactionParts = line.split("\\|");
                Transaction newTransaction = new Transaction(LocalDate.parse(transactionParts[0]), LocalDateTime.parse(transactionParts[1]),transactionParts[2],transactionParts[3],Double.parseDouble(transactionParts[4]));
                transactions.add(newTransaction);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return transactions;

    }
    public static BufferedReader getFileReader(String fileName){
        try{
            FileReader fileReader = new FileReader("src/main/resources/" + fileName);
            return new BufferedReader(fileReader);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
    public static String homeScreen(){

        String choice = "";

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
    public static void fileWriter(String printLine){
        try{
            FileWriter fileWriter = new FileWriter("src/main/resources/transaction.csv",true);
            BufferedWriter bufWriter = new BufferedWriter(fileWriter);
            bufWriter.write(printLine);
            bufWriter.write("\n");
            bufWriter.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    // fix these lines of code
    /*public static ArrayList<Transaction> addDeposit(){
        LocalDateTime now = LocalDateTime.now();
        String date = dateTimeStamp.format("yyyy-MM-dd");
        String time = dateTimeStamp.format("HH:mm");
        System.out.println("Enter the description of the deposit:");
        String description = myScanner.nextLine().trim();
        System.out.println("Enter the vendor of the deposit:");
        String vendor = myScanner.nextLine().trim();
        System.out.println("Enter the amount of the deposit:");
        double amount = myScanner.nextDouble();
        myScanner.nextLine();
        Transaction transactions = new Transaction (date, time, description, vendor, amount);
        String listOfDeposit = transactions.getDate() + transactions.getTime() + transactions.getDescription() +" | " + transactions.getVendor() +" | " +transactions.getAmount();

        return listOfDeposit;
    }*/
    /*public static String makePayment(){

        System.out.println("Enter the description of the payment:");
        String description = myScanner.nextLine().trim();
        System.out.println("Enter the vendor of the payment:");
        String vendor = myScanner.nextLine().trim();
        System.out.println("Enter the amount of the payment:");
        double amount = -1 * Math.abs(myScanner.nextDouble());
        myScanner.nextLine();
        Transaction transactions = new Transaction (date, time, description, vendor, amount);
        String listOfPayments =  transactions.getDate() + transactions.getTime() + transactions.getDescription() +" | " + transactions.getVendor() +" | " +transactions.getAmount();

        return listOfPayments;
    }*/
    public static String ledgerScreen(){
        String choice = "";

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

    public static int reportScreen(){
        int choice = -1;

        while(true) {
            System.out.println("-------------------------------------");
            System.out.println("-------------Report Screen-----------");
            System.out.println("-------------------------------------");
            System.out.println("[1] Month to date\n[2] Previous Month\n[3] Year to Date\n[4] Previous year\n[5] Search by Vendor\n[0] Back to Ledger");
            System.out.println("What would you like to do?");
            choice = myScanner.nextInt();
            myScanner.nextLine();
            break;
        }
        return choice;
    }

}
