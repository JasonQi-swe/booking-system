package com.example.bookingsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class RejectedTransactionsResponse {
    @JsonProperty("Rejected Transactions")
    private List<RejectedTransaction> rejectedTransactions;

    public RejectedTransactionsResponse() {}

    public RejectedTransactionsResponse(List<RejectedTransaction> rejectedTransactions) {
        this.rejectedTransactions = rejectedTransactions;
    }

    public List<RejectedTransaction> getRejectedTransactions() {
        return rejectedTransactions;
    }

    public void setRejectedTransactions(List<RejectedTransaction> rejectedTransactions) {
        this.rejectedTransactions = rejectedTransactions;
    }

    public static class RejectedTransaction {
        @JsonProperty("First Name")
        private String firstName;

        @JsonProperty("Last Name")
        private String lastName;

        @JsonProperty("Email Id")
        private String emailId;

        @JsonProperty("Transaction Number")
        private String transactionNumber;

        public RejectedTransaction() {}

        public RejectedTransaction(String firstName, String lastName, String emailId, String transactionNumber) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.emailId = emailId;
            this.transactionNumber = transactionNumber;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmailId() {
            return emailId;
        }

        public void setEmailId(String emailId) {
            this.emailId = emailId;
        }

        public String getTransactionNumber() {
            return transactionNumber;
        }

        public void setTransactionNumber(String transactionNumber) {
            this.transactionNumber = transactionNumber;
        }
    }
}
