package com.evaloper.mini_bank.utils;

import java.time.Year;

public class AccountUtil {
    public static final String ACCOUNT_EXISTS_CODE = "001";
    public static final String ACCOUNT_EXISTS_MESSAGE = "This user already has an account created!";

    public static final String ACCOUNT_CREATION_SUCCESS_CODE = "002";
    public static final String ACCOUNT_CREATION_SUCCESS_MESSAGE = "Account has been created Successfully!";

    public static final String ACCOUNT_NUMBER_NON_EXISTS_CODE = "003";

    public static final String ACCOUNT_NUMBER_NON_EXISTS_MESSAGE = "Provided account number does not exists";

    public static final String ACCOUNT_NUMBER_FOUND_CODE = "004";
    public static final String ACCOUNT_NUMBER_FOUND_MESSAGE = "Account number found";

    public static final String ACCOUNT_CREDITED_SUCCESS_CODE = "005";
    public static final String ACCOUNT_CREDITED_SUCCESS_MESSAGE = "Account credited successfully!";

    public static final String INSUFFICIENT_BALANCE_CODE = "006";
    public static final String INSUFFICIENT_BALANCE_MESSAGE = "Insufficient Balance";

    public static final String ACCOUNT_DEBITED_SUCCESS_CODE = "007";
    public static final String ACCOUNT_DEBITED_SUCCESS_MESSAGE = "Account has been debited successfully";

    public static final String TRANSFER_SUCCESSFUL_CODE = "008";
    public static final String TRANSFER_SUCCESSFUL_MESSAGE="Transfer Successful";
    public static final String LOGIN_FAILURE_CODE="009";
    public static final String LOGIN_FAILURE_MESSAGE="Invalid email or password";
    public static final String LOGIN_SUCCESS_CODE="009";
    public static final String LOGIN_SUCCESS_MESSAGE="Logged in successfully";
    public static final String AIRTIME_PURCHASE_SUCCESS_CODE="010";
    public static final String AIRTIME_PURCHASE_SUCCESS_MESSAGE="Airtime purchased successfully";
    public static final String DATA_PURCHASE_SUCCESS_CODE="011";
    public static final String DATA_PURCHASE_SUCCESS_MESSAGE="Data purchased successfully";



    public static String generateAccountNumber(){

        /**
         * this algorithm will assume that your account number will be a total of 10 digits, since we
         * basically have 10 digits account number in Nigeria
         * for generating account number
         * concantenate current year + any six random digits
         */

        // to get current year -- will give us the first 4 digits
        Year currentYear = Year.now();

        // this will give us the next 6 digits
        int min = 100000;
        int max = 999999;

        // generate a random number between min and max

        int randomNumber = (int)Math.floor(Math.random() * (max - min + 1) + min);

        // convert current year and random number to string and then concatenate them

        String year = String.valueOf(currentYear);
        String randomNum = String.valueOf(randomNumber);

        // append both the current year and the random number to generate the 10 digits account number
        StringBuilder accountNumber = new StringBuilder();

        // since it is a String builder we convert it to a string using the toString method
        return accountNumber.append(year).append(randomNum).toString();

    }

}

