package com.iSoftTech.logbook.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Dada abiola
 */
public class Transaction {
    
    private final StringProperty transactionType;
    private final StringProperty customersName;
    private final DoubleProperty commissionDeu;
    private final DoubleProperty eFloat;
    private final DoubleProperty transactionAmount;
    private final IntegerProperty transactionFee;
    private final StringProperty phoneNumber;
    private final StringProperty recipientName;
    private final StringProperty recipientPhone;
    private final StringProperty acctNumber;
    private final StringProperty txnID;
    private final SimpleObjectProperty dateCreated;
    private final SimpleObjectProperty timeCreated;
    private final StringProperty bank;
    
    
    /**
     * Default constructor.
     */
    public Transaction(){
        this(null,null,null,null,null,null,null,null,null,null,null,null,null,null);
    }

    public Transaction(String transaction, String customers, Double commission, Double eFloaT, Double transactionA,
            Integer transactionF, String phone, String recipientN, String recipient, String acct, 
            String txnId, Object date, Object time, String bankName
            ) {
        
        this.commissionDeu = new SimpleDoubleProperty(commission);
        this.eFloat = new SimpleDoubleProperty(eFloaT);
        this.transactionType = new SimpleStringProperty(transaction);
        this.transactionAmount = new SimpleDoubleProperty(transactionA);
        this.transactionFee = new SimpleIntegerProperty(transactionF);
        this.customersName = new SimpleStringProperty(customers);
        this.phoneNumber = new SimpleStringProperty(phone);
        this.recipientName = new SimpleStringProperty(recipientN);
        this.recipientPhone = new SimpleStringProperty(recipient);
        this.acctNumber = new SimpleStringProperty(acct);
        this.txnID = new SimpleStringProperty(txnId);
        this.dateCreated = new SimpleObjectProperty(date);
        this.timeCreated = new SimpleObjectProperty(time);
        this.bank = new SimpleStringProperty(bankName);
    }

    public String getTransactionType() {
        return transactionType.get();
    }
    public void setTransactionType(String transaction){
        transactionTypeProperty().set(transaction);
    }
    public StringProperty transactionTypeProperty(){
        return transactionType;
    }
    public String getCustomersName(){
        return customersName.get();
    }
    public void setCustomersName(String customers){
        this.customersName.set(customers);
    }
    public StringProperty customersNameProperty() {
        return customersName;
    }
    public Double getCommissionDeu(){
        return commissionDeu.get();
    }
    public void setCommissionDeu(Double commission){
        this.commissionDeu.set(commission);
    }
    public DoubleProperty commissionDeuProperty() {
        return commissionDeu;
    }
    public Double geteFloat(){
        return eFloat.get();
    }
    public void seteFloat(Double eFloaT){
        this.eFloat.set(eFloaT);
    }
    public DoubleProperty eFloatProperty() {
        return eFloat;
    }
    public Double getTransactionAmount() {
        return transactionAmount.get();
    }
    public void setTransactionAmount(Double transactionA){
        this.transactionAmount.set(transactionA);
    }
    public DoubleProperty transactionAmountProperty() {
        return transactionAmount;
    }
    public Integer getTransactionFee() {
        return transactionFeeProperty().get();
    }
    public void setTransactionFee(final Integer transactionF){
        this.transactionFeeProperty().set(transactionF);
    }
    public IntegerProperty transactionFeeProperty() {
        return transactionFee;
    }
    public String getPhoneNumber() {
        return phoneNumber.get();
    }
    public void setPhoneNumber(String phone){
        this.phoneNumber.set(phone);
    }
    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }
    public String getRecipientName() {
        return recipientName.get();
    }
    public void setRecipientName(String recipientN){
        this.recipientName.set(recipientN);
    }
    public StringProperty recipientNameProperty() {
        return recipientName;
    }
    public String getRecipientPhone() {
        return recipientPhone.get();
    }
    public void setRecipientPhone(String recipient){
        this.recipientPhone.set(recipient);
    }
    public StringProperty recipientPhoneProperty() {
        return recipientPhone;
    }
    public String getAcctNumber(){
        return acctNumber.get();
    }
    public void setAcctNumber(String acct){
        this.acctNumber.set(acct);
    }
    public StringProperty acctNumberProperty(){
        return acctNumber;
    }
    public String getTxnID(){
        return txnIDProperty().get();
    }
    public void setTxnID(String txnId){
        this.txnIDProperty().set(txnId);
    }
    public StringProperty txnIDProperty(){
        return txnID;
    }
    
    public Object getDate() {
        return dateProperty().get();
    }
    public void setDate(Object date){
        dateProperty().set(date);
    }
    public ObjectProperty dateProperty() {
        return dateCreated;
    }
    public Object getTime() {
        return timeProperty().get();
    }
    public void setTime(Object time){
        timeProperty().set(time);
    }
    public ObjectProperty timeProperty() {
        return timeCreated;
    }
    public String getBank() {
        return bank.get();
    }
    public void setBank(String bankName){
        this.bank.set(bankName);
    }
    public StringProperty bankProperty() {
        return bank;
    } 
}
