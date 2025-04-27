package com.pluralsight;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AccountingLedgerApp {
    static Scanner myScanner = new Scanner(System.in);
    public static void main(String[] args) {
        boolean appRunning = true;
        while(appRunning){
            String mainMenuChoice = homeScreen().toLowerCase();
            switch(mainMenuChoice){
                case "d":
                    
                    break;
                case "p":

                    break;
                case "l":

                    break;
                case "x":
                    System.out.println("Goodbye, See you again!");
                    appRunning = false;
                    break;

            }
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
    public static BufferedWriter fileWriter(){
        try{
            FileWriter fileWriter = new FileWriter("src/main/resources/transaction.csv",true);
            BufferedWriter bufWriter = new BufferedWriter(fileWriter);
            bufWriter.write("Date | Time | Description | Vendor | Amount\n");
            bufWriter.close();
            return new BufferedWriter(fileWriter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static String addDeposit(){

        System.out.println("Enter the description of the deposit:");
        String description = myScanner.nextLine();
        System.out.println("Enter the vendor of the deposit:");
        String vendor = myScanner.nextLine();
        System.out.println("Enter the amount of the deposit:");
        double amount = myScanner.nextDouble();
        Transaction transactions = new Transaction(description, vendor, amount);
        String listOfDeposit = transactions.getDescription() +" | " + transactions.getVendor() +" | " +transactions.getAmount();

        return listOfDeposit;
    }
    public static DateTimeFormatter dateTimeFormatter(){
        DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd | HH:mm");
        return dateTime;
    }


}
