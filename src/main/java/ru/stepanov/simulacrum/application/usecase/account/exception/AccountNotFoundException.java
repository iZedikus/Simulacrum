package ru.stepanov.simulacrum.application.usecase.account.exception;
public class AccountNotFoundException extends RuntimeException { public AccountNotFoundException(String id){super("Account not found: "+id);} }
