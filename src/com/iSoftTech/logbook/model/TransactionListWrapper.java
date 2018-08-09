/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iSoftTech.logbook.model;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * Helper class to wrap a list of persons. This is used for saving the
 * list of persons to XML.
 * @author Dada abiola
 */
@XmlRootElement(name = "transactions")
public class TransactionListWrapper {

    private List<Transaction> transactions;

    @XmlElement(name = "transaction")
    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
