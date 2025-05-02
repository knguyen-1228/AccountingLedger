# Accounting Ledger App

## Description
An accounting ledger is a book or digital record that contains all financial transactions of a business or personal use.
In my project, I made a accounting ledger to show a home screen, ledger screen, and report screen.
In each screen you will be shown options to prompt the user to see what they want to do and execute the code.

## Home Screen
### In this home screen it displays the home screen and ask the user if they would like to 
[D] add deposit
[P] make payment
[L] ledger
[X] exit
```
System.out.println("-------------------------------------");
System.out.println("-------------Home Screen-------------");
System.out.println("-------------------------------------");
System.out.println("[D] Add Deposit\n[P] Make Payment(Debit)\n[L] Ledger\n[X] Exit");
System.out.println("What would you like to do?");

```

## Ledger Screen
### In this ledger screen it displays the home screen and ask the user if they would like to
[A] All ledger
[D] Show deposit
[P] Show payment
[R] reports
[H] home
```
System.out.println("-------------------------------------");
System.out.println("-------------Ledger Screen-----------");
System.out.println("-------------------------------------");
System.out.println("[A] All Ledgers\n[D] Show Deposits\n[P] Show Payments\n[R] Reports \n[H] Home");
System.out.println("What would you like to do?");
```
## Report Screen
### In this ledger screen it displays the home screen and ask the user if they would like to
[1] Month to Date
[2] Previous Month
[3] Year to Date
[4] Previous Year
[5] Search by Vendor
[0]Back to ledger
```
System.out.println("-------------------------------------");
System.out.println("-------------Report Screen-----------");
System.out.println("-------------------------------------");
System.out.println("[1] Month to date\n[2] Previous Month\n[3] Year to Date\n[4] Previous year\n[5] Search by Vendor\n[0] Back to Ledger");
System.out.println("What would you like to do?");
```

## Interesting Piece of Code
This code was the most interesting for me because I found it the most fun to work with.
Let's go through this code step by step.
First you get the current date by using LocalDate.now and setting it as today.
Then you get the first of the month by today.withDayOfMonth(1) and setting it as firstOfTheMonth.
The next step you print "Here are the Month to Date report".
After, you create a string output to add everything into the string.
The for loop is the best part of this code, you loop through the list of array.
The if statement is the tricky part of this code, !targetDate.isAfter(today) this checks if target date is today or earlier
and !targetDate.isBefore(firstOfTheMonth) this checks if this is on or after the first date
If both conditions are true it will add the array in the format into output as a string.
Finally, it will print the output if there is one.

```
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
```
