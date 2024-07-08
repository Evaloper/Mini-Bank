package com.evaloper.mini_bank.service.impl;


import com.evaloper.mini_bank.domain.entities.UserEntity;
import com.evaloper.mini_bank.domain.enums.TransactionStatus;
import com.evaloper.mini_bank.domain.enums.TransactionType;
import com.evaloper.mini_bank.payload.request.*;
import com.evaloper.mini_bank.payload.response.AccountInfo;
import com.evaloper.mini_bank.payload.response.BankResponse;
import com.evaloper.mini_bank.payload.response.NameAccountResponse;
import com.evaloper.mini_bank.payload.response.PhoneNumberResponse;
import com.evaloper.mini_bank.repository.UserRepository;
import com.evaloper.mini_bank.service.TransactionService;
import com.evaloper.mini_bank.service.UserService;
import com.evaloper.mini_bank.utils.AccountUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TransactionService transactionService;// $0.01 per byte
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public BankResponse balanceEnquiry(EnquiryRequest enquiryRequest) {
        // check if the provided account number exists in the db

        boolean isAccountExists = userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());


        if(!isAccountExists){
            return BankResponse.builder()
                    .responseCode(AccountUtil.ACCOUNT_NUMBER_NON_EXISTS_CODE)
                    .responseMessage(AccountUtil.ACCOUNT_NUMBER_NON_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        // if account number exists

        UserEntity foundUser = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());
        return BankResponse.builder()
                .responseCode(AccountUtil.ACCOUNT_NUMBER_FOUND_CODE)
                .responseMessage(AccountUtil.ACCOUNT_NUMBER_FOUND_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(foundUser.getAccountBalance())
                        .accountNumber(enquiryRequest.getAccountNumber())
                        .accountName(foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getOtherName())
                        .build())
                .build();
    }

    @Override
    public NameAccountResponse nameEnquiry(EnquiryRequest enquiryRequest) {
        boolean isAccountExist = userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());

        if(!isAccountExist){
            return NameAccountResponse.builder()
                    .responseCode(AccountUtil.ACCOUNT_NUMBER_NON_EXISTS_CODE)
                    .responseMessage(AccountUtil.ACCOUNT_NUMBER_NON_EXISTS_MESSAGE)
                    .firstName(null)
                    .lastName(null)
                    .accountNumber(null)
                    .build();
        }

        UserEntity foundUser = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());
        return NameAccountResponse.builder()
                .responseCode(AccountUtil.PHONE_NUMBER_FOUND_CODE)
                .responseMessage(AccountUtil.PHONE_NUMBER_FOUND_MESSAGE)
                .firstName(foundUser.getFirstName())
                .lastName(foundUser.getLastName())
                .otherName(foundUser.getOtherName())
                .accountNumber(foundUser.getAccountNumber())
                .build();
    }

    @Override
    public NameAccountResponse nameAndAccountEnquiry(EnquiryRequest enquiryRequest) {
        boolean isPhoneNumberExists = userRepository.existsByPhoneNumber(enquiryRequest.getPhoneNumber());

        if (!isPhoneNumberExists) {
            return NameAccountResponse.builder()
                    .responseCode(AccountUtil.PHONE_NUMBER_NON_EXISTS_CODE)
                    .responseMessage(AccountUtil.PHONE_NUMBER_NON_EXISTS_MESSAGE)
                    .firstName(null)
                    .lastName(null)
                    .accountNumber(null)
                    .build();
        }

        UserEntity foundUser = userRepository.findByPhoneNumber(enquiryRequest.getPhoneNumber());
        return NameAccountResponse.builder()
                .responseCode(AccountUtil.PHONE_NUMBER_FOUND_CODE)
                .responseMessage(AccountUtil.PHONE_NUMBER_FOUND_MESSAGE)
                .firstName(foundUser.getFirstName())
                .lastName(foundUser.getLastName())
                .otherName(foundUser.getOtherName())
                .accountNumber(foundUser.getAccountNumber())
                .build();
    }


    @Override
    public PhoneNumberResponse PhoneNumberEnquiry(PhoneNumberEnquiryRequest request) {
        boolean isPhoneNumberExists = userRepository.existsByPhoneNumber(request.getPhoneNumber());
        if (!isPhoneNumberExists) {
            return PhoneNumberResponse.builder()
                    .responseCode(AccountUtil.PHONE_NUMBER_NON_EXISTS_CODE)
                    .responseMessage(AccountUtil.PHONE_NUMBER_NON_EXISTS_MESSAGE)
                    .phoneNumber(null)
                    .build();
        }
        UserEntity foundUser = userRepository.findByPhoneNumber(request.getPhoneNumber());

        return PhoneNumberResponse.builder()
                .phoneNumber(foundUser.getPhoneNumber())
                .build();
    }


    @Override
    public BankResponse creditAccount(CreditAndDebitRequest request) {
        // to credit an account, first check if the account exists
        boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if(!isAccountExist){
            return BankResponse.builder()
                    .responseCode(AccountUtil.ACCOUNT_NUMBER_NON_EXISTS_CODE)
                    .responseMessage(AccountUtil.ACCOUNT_NUMBER_NON_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        // if the account exists, then get the user account number from the database
        UserEntity userToCredit = userRepository.findByAccountNumber(request.getAccountNumber());
        // then update the user account balance with what is being credit with
        // to add two big decimal you use the add method
        userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(request.getAmount()));

        userRepository.save(userToCredit);



        // Step 4 save transaction should come in when working on the generating bank statement
        TransactionRequest transactionRequest = TransactionRequest.builder()
                .accountNumber(userToCredit.getAccountNumber())
                .transactionType(TransactionType.DEPOSIT)
                .amount(request.getAmount())
                .build();

        transactionService.saveTransaction(transactionRequest);


        return BankResponse.builder()
                .responseCode(AccountUtil.ACCOUNT_CREDITED_SUCCESS_CODE)
                .responseMessage(AccountUtil.ACCOUNT_CREDITED_SUCCESS_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountName(userToCredit.getFirstName() + " " + userToCredit.getLastName() + " " + userToCredit.getOtherName())
                        .accountBalance(userToCredit.getAccountBalance())
                        .accountNumber(request.getAccountNumber())
                        .build())
                .build();
    }

    @Override
    public BankResponse debitAccount(CreditAndDebitRequest request) {

        // to debit an account, check for insufficient balance
        boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if(!isAccountExist){
            return BankResponse.builder()
                    .responseCode(AccountUtil.ACCOUNT_NUMBER_NON_EXISTS_CODE)
                    .responseMessage(AccountUtil.ACCOUNT_NUMBER_NON_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        // if account number exists
        UserEntity userToDebit = userRepository.findByAccountNumber(request.getAccountNumber());

        //check for insufficient balance
        BigInteger availableBalance = userToDebit.getAccountBalance().toBigInteger();
        BigInteger debitAmount = request.getAmount().toBigInteger();

        if(availableBalance.intValue() < debitAmount.intValue()){
            return BankResponse.builder()
                    .responseCode(AccountUtil.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtil.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo(null)
                    .build();


        }
        else {
            userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(request.getAmount()));
            userRepository.save(userToDebit);


            // Step 4 save transaction should come in when working on the generating bank statement
            TransactionRequest transactionRequest = TransactionRequest.builder()
                    .accountNumber(userToDebit.getAccountNumber())
                    .transactionType(TransactionType.WITHDRAW)
                    .amount(request.getAmount())
                    .build();

            transactionService.saveTransaction(transactionRequest);


            return BankResponse.builder()
                    .responseCode(AccountUtil.ACCOUNT_DEBITED_SUCCESS_CODE)
                    .responseMessage(AccountUtil.ACCOUNT_DEBITED_SUCCESS_MESSAGE)
                    .accountInfo(AccountInfo.builder()
                            .accountName(userToDebit.getFirstName() + " " + userToDebit.getLastName() + " " + userToDebit.getOtherName())
                            .accountBalance(userToDebit.getAccountBalance())
                            .accountNumber(request.getAccountNumber())
                            .build())
                    .build();


        }


    }

    @Override
    public BankResponse transfer(TransferRequest request) {
        /**
         * to create the transfer request
         * 1. get the destination account and check if it exists
         * 2. check if the amount being debited is not more than the account balance, if yes
         * 3. debit the account
         * 4. get the account to credit
         * 5. credit the account
         */

        boolean isDestinationAccountExists = userRepository.existsByAccountNumber(request.getSourceAccountNumber());

        if(!isDestinationAccountExists){
            return BankResponse.builder()
                    .responseCode(AccountUtil.ACCOUNT_NUMBER_NON_EXISTS_CODE)
                    .responseMessage(AccountUtil.ACCOUNT_NUMBER_NON_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        // implement the debit account user logic
        UserEntity sourceAccountUser = userRepository.findByAccountNumber(request.getSourceAccountNumber());
        if(request.getAmount().compareTo(sourceAccountUser.getAccountBalance()) > 0){
            return BankResponse.builder()
                    .responseCode(AccountUtil.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtil.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        sourceAccountUser.setAccountBalance(sourceAccountUser.getAccountBalance().subtract(request.getAmount()));
        userRepository.save(sourceAccountUser);
        String sourceUsername = sourceAccountUser.getFirstName() + " " + sourceAccountUser.getLastName() + " " + sourceAccountUser.getOtherName();

        // implement the credit account logic
        UserEntity destinationAccountUser = userRepository.findByAccountNumber(request.getDestinationAccountNumber());
        destinationAccountUser.setAccountBalance(destinationAccountUser.getAccountBalance().add(request.getAmount()));
        userRepository.save(destinationAccountUser);


        //Step 4 save transaction
        TransactionRequest transactionRequest = TransactionRequest.builder()
                .accountNumber(destinationAccountUser.getAccountNumber())
                .transactionType(TransactionType.TRANSFER)
                .amount(request.getAmount())
                .build();

        transactionService.saveTransaction(transactionRequest);




        return BankResponse.builder()
                .responseCode(AccountUtil.TRANSFER_SUCCESSFUL_CODE)
                .responseMessage(AccountUtil.TRANSFER_SUCCESSFUL_MESSAGE)
                .accountInfo(null)
                .build();
    }

    @Override
    @Transactional
    public BankResponse buyAirtime(AirtimeRequest request) {
        // Validate request
        if (request.getAccountNumber() == null || request.getPhoneNumber() == null || request.getAmount() == null) {
            logger.error("Invalid request: {}", request);
            return BankResponse.builder()
                    .responseCode(AccountUtil.ACCOUNT_NUMBER_NON_EXISTS_CODE)
                    .responseMessage(AccountUtil.ACCOUNT_NUMBER_NON_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        logger.info("Checking if account exists for account number: {}", request.getAccountNumber());
        boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());

        logger.info("Account existence check result for {}: {}", request.getAccountNumber(), isAccountExist);

        if (!isAccountExist) {
            return BankResponse.builder()
                    .responseCode(AccountUtil.ACCOUNT_NUMBER_NON_EXISTS_CODE)
                    .responseMessage(AccountUtil.ACCOUNT_NUMBER_NON_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        UserEntity user = userRepository.findByAccountNumber(request.getAccountNumber());

        if (user == null) {
            logger.error("Account number {} not found despite existence check passing", request.getAccountNumber());
            return BankResponse.builder()
                    .responseCode(AccountUtil.ACCOUNT_NUMBER_NON_EXISTS_CODE)
                    .responseMessage(AccountUtil.ACCOUNT_NUMBER_NON_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        BigDecimal availableBalance = user.getAccountBalance();
        BigDecimal airtimeAmount = request.getAmount();

        logger.info("Available balance: {}, Airtime amount: {}", availableBalance, airtimeAmount);

        if (airtimeAmount.compareTo(availableBalance) > 0) {
            return BankResponse.builder()
                    .responseCode(AccountUtil.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtil.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        try {
            // Deduct airtime amount from user's balance
            user.setAccountBalance(availableBalance.subtract(airtimeAmount));
            userRepository.save(user);
            logger.info("New balance after deduction: {}", user.getAccountBalance());

            // Save transaction
            TransactionRequest transactionRequest = TransactionRequest.builder()
                    .accountNumber(user.getAccountNumber())
                    .transactionType(TransactionType.BUY_AIRTIME)
                    .amount(airtimeAmount)
                    .build();
            transactionService.saveTransaction(transactionRequest);

            // Build response
            return BankResponse.builder()
                    .responseCode(AccountUtil.AIRTIME_PURCHASE_SUCCESS_CODE)
                    .responseMessage(AccountUtil.AIRTIME_PURCHASE_SUCCESS_MESSAGE)
                    .accountInfo(AccountInfo.builder()
                            .accountName(user.getFirstName() + " " + user.getLastName() + " " + user.getOtherName())
                            .accountBalance(user.getAccountBalance())
                            .accountNumber(request.getAccountNumber())
                            .build())
                    .build();

        } catch (Exception e) {
            logger.error("Error purchasing airtime for account {}: ", request.getAccountNumber(), e);
            return BankResponse.builder()
                    .responseCode(AccountUtil.TRANSACTION_ERROR_CODE)
                    .responseMessage(AccountUtil.TRANSACTION_ERROR_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
    }

@Override
    public BankResponse buyData(DataRequest request) {
        boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());

        if(!isAccountExist){
            return BankResponse.builder()
                    .responseCode(AccountUtil.ACCOUNT_NUMBER_NON_EXISTS_CODE)
                    .responseMessage(AccountUtil.ACCOUNT_NUMBER_NON_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        UserEntity user = userRepository.findByAccountNumber(request.getAccountNumber());

        int dataAmount = request.getDataAmountInBytes(request.getDataAmount());
        String dataAmountInBytes = request.getDataAmount();

        BigDecimal amountToDeduct = BigDecimal.valueOf(dataAmount);

        BigDecimal availableBalance = user.getAccountBalance();
        BigDecimal availableBalanceBigDecimal = new BigDecimal(availableBalance.intValue());

        if (availableBalanceBigDecimal.compareTo(amountToDeduct) < 0){
            return BankResponse.builder()
                    .responseCode(AccountUtil.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtil.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        System.out.println("amountToDeduct: " + amountToDeduct);
        System.out.println("accountBalance: " + user.getAccountBalance());
        System.out.println("dataAmountInBytes: " + dataAmountInBytes);
//        System.out.println("dataCostPerByte: " + dataCostPerByte);

        user.setAccountBalance(user.getAccountBalance().subtract(amountToDeduct));
        userRepository.save(user);

        TransactionRequest transactionRequest = TransactionRequest.builder()
                .accountNumber(user.getAccountNumber())
                .transactionType(TransactionType.BUY_DATA)
                .amount(amountToDeduct)
                .status(TransactionStatus.COMPLETED)
                .build();

        transactionService.saveTransaction(transactionRequest);

        return BankResponse.builder()
                .responseCode(AccountUtil.DATA_PURCHASE_SUCCESS_CODE)
                .responseMessage(AccountUtil.DATA_PURCHASE_SUCCESS_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountName(user.getFirstName() + " " + user.getLastName() + " " + user.getOtherName())
                        .accountBalance(user.getAccountBalance())
                        .accountNumber(request.getAccountNumber())
                        .build())
                .build();
    }
}

