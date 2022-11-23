package com.paymentrecommendation.service.impl;

import com.paymentrecommendation.Constants;
import com.paymentrecommendation.enums.LineOfBusiness;
import com.paymentrecommendation.enums.PaymentInstrumentType;
import com.paymentrecommendation.exception.LOBNotFoundException;
import com.paymentrecommendation.models.Cart;
import com.paymentrecommendation.models.PaymentInstrument;
import com.paymentrecommendation.models.User;
import com.paymentrecommendation.models.UserPaymentInstrument;
import com.paymentrecommendation.service.PaymentRecommender;

import java.util.*;

public class PaymentRecommenderService implements PaymentRecommender {

    RelevanceOrderingService relevanceOrderingService;

    public PaymentRecommenderService() {
        relevanceOrderingService = new RelevanceOrderingService();
    }

    @Override
    public List<PaymentInstrument> recommendPaymentInstruments(User user, Cart cart) {
        LineOfBusiness userLOB = cart.getLineOfBusiness();
        boolean isUPIEnabled = user.getUserContext().getDeviceContext().isUpiEnabled();
        Double amountInCart = cart.getCartDetail().getCartAmount();
        List<PaymentInstrument> userPaymentInstrumentList = user.getUserPaymentInstrument().getPaymentInstruments();

        LinkedList<PaymentInstrumentType> relOrder = null;
        try {
            relOrder = relevanceOrderingService.getRelevanceOrderByLOB(userLOB);
        } catch (LOBNotFoundException e) {
            throw new RuntimeException(e);
        }


        List<PaymentInstrument> result = new LinkedList<>();
        Iterator<PaymentInstrumentType> itr = relOrder.iterator();
        while (itr.hasNext()) {
            PaymentInstrumentType piType = itr.next();
            Iterator<PaymentInstrument> userPIItr = userPaymentInstrumentList.iterator();
            List<PaymentInstrument> unsorted = new ArrayList<>();

            if(piType==PaymentInstrumentType.UPI && !isUPIEnabled)
                continue;
            if(userLOB == LineOfBusiness.INVESTMENT && amountInCart> Constants.LOB_INVESTMENT_MAX_LIMIT)
                continue;
            if(userLOB == LineOfBusiness.CREDIT_CARD_BILL_PAYMENT && amountInCart> Constants.LOB_CREDIT_CARD_PAYMENT_MAX_LIMIT)
                continue;
            if(userLOB == LineOfBusiness.COMMERCE && amountInCart> Constants.LOB_COMMERCE_MAX_LIMIT)
                continue;

            while (userPIItr.hasNext()) {
                PaymentInstrument pi = (PaymentInstrument) userPIItr.next();
                if (pi.getPaymentInstrumentType() == piType) {
                    unsorted.add(pi);
                }
            }

            List<PaymentInstrument> sorted=null;
            if(!unsorted.isEmpty())
                sorted = sortList(unsorted);

            Iterator<PaymentInstrument> itrRes = sorted.iterator();
            while (itrRes.hasNext()) {
                PaymentInstrument pi = (PaymentInstrument) itrRes.next();
                result.add(pi);
            }
        }
        return result;
    }

    private List<PaymentInstrument> sortList(List<PaymentInstrument> list) {
        Collections.sort(list, new Comparator<PaymentInstrument>() {
            public int compare(PaymentInstrument o1, PaymentInstrument o2) {
                if (o2.getRelevanceScore() >= o1.getRelevanceScore())
                    return 1;
                else
                    return -1;
            }
        });
        return list;
    }

}