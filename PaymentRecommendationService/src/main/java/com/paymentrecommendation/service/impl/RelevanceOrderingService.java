package com.paymentrecommendation.service.impl;

import com.paymentrecommendation.enums.LineOfBusiness;
import com.paymentrecommendation.enums.PaymentInstrumentType;
import com.paymentrecommendation.exception.LOBNotFoundException;
import com.paymentrecommendation.models.PaymentInstrument;

import java.util.*;

public class RelevanceOrderingService {

    private HashMap<LineOfBusiness, LinkedList<PaymentInstrumentType>> lobRelevanceOrder;

    public RelevanceOrderingService() {
        lobRelevanceOrder = new HashMap<>();
        populateLOBMap();
    }

    private void populateLOBMap() {
        LinkedList<PaymentInstrumentType> CCPayment = new LinkedList<>();
        CCPayment.add(PaymentInstrumentType.UPI);
        CCPayment.add(PaymentInstrumentType.NETBANKING);
        CCPayment.add(PaymentInstrumentType.DEBIT_CARD);

        lobRelevanceOrder.put(LineOfBusiness.CREDIT_CARD_BILL_PAYMENT, CCPayment);

        LinkedList<PaymentInstrumentType> CommercePayment = new LinkedList<>();
        CommercePayment.add(PaymentInstrumentType.CREDIT_CARD);
        CommercePayment.add(PaymentInstrumentType.UPI);
        CommercePayment.add(PaymentInstrumentType.DEBIT_CARD);

        lobRelevanceOrder.put(LineOfBusiness.COMMERCE, CommercePayment);

        LinkedList<PaymentInstrumentType> InvestmentPayment = new LinkedList<>();
        InvestmentPayment.add(PaymentInstrumentType.UPI);
        InvestmentPayment.add(PaymentInstrumentType.NETBANKING);
        InvestmentPayment.add(PaymentInstrumentType.DEBIT_CARD);

        lobRelevanceOrder.put(LineOfBusiness.INVESTMENT, InvestmentPayment);
    }

    public LinkedList<PaymentInstrumentType> getRelevanceOrderByLOB(LineOfBusiness lineOfBusiness) throws LOBNotFoundException {
        if(lobRelevanceOrder.containsKey(lineOfBusiness)){
            return lobRelevanceOrder.get(lineOfBusiness);
        }
        else
            throw new LOBNotFoundException();
    }

    //Not providing any setter for security purpose
}
