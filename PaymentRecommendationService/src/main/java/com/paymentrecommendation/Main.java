package com.paymentrecommendation;

import com.google.common.collect.Lists;
import com.paymentrecommendation.enums.Issuer;
import com.paymentrecommendation.enums.LineOfBusiness;
import com.paymentrecommendation.enums.PaymentInstrumentType;
import com.paymentrecommendation.exception.LOBNotFoundException;
import com.paymentrecommendation.models.*;
import com.paymentrecommendation.service.PaymentRecommender;
import com.paymentrecommendation.service.impl.PaymentRecommenderService;

import java.util.*;

import static com.paymentrecommendation.enums.LineOfBusiness.INVESTMENT;

public class Main {
 public static void main(String args[]) {
     /*PaymentRecommender paymentRecommender = new PaymentRecommenderService();
     DeviceContext deviceContext1 = new DeviceContext(true);
     UserContext userContext1 = new UserContext(deviceContext1);
     List<PaymentInstrument> paymentInstruments1 = new ArrayList<>();

     UserPaymentInstrument userPaymentInstrument1 = new UserPaymentInstrument(paymentInstruments1);
     //populate the list TODO
     String userId = "uid101";
     User user1 = new User(userId, userContext1, userPaymentInstrument1);

     LineOfBusiness lineOfBusiness1 = INVESTMENT;
     CartDetail cartDetail1 = new CartDetail(10000.00, new ArrayList<CartItem>());
     Cart cart = new Cart(lineOfBusiness1, cartDetail1);

     List<PaymentInstrument> paymentInstrumentList = paymentRecommender.recommendPaymentInstruments(user1, cart);
     */
     PaymentRecommender paymentRecommender = new PaymentRecommenderService();

     final PaymentInstrument hdfcCreditCard = createCreditCardPaymentInstrument();
     final PaymentInstrument sbiUPI = createUPIPaymentInstrument();
     final PaymentInstrument hdfcUPI = createUPIPaymentInstrument();
     hdfcUPI.setIssuer(Issuer.HDFC);
     hdfcUPI.setRelevanceScore(0.88);
     final PaymentInstrument sbiNetBanking = createNetBankingPaymentInstrument();
     final PaymentInstrument sbiDebitCard = createDebitCardInstrument();
     final User user = new User(UUID.randomUUID().toString(), new UserContext(new DeviceContext(true)),
             new UserPaymentInstrument(Arrays.asList(hdfcCreditCard, sbiUPI, hdfcUPI, sbiNetBanking, sbiDebitCard)));
     final List<PaymentInstrument> expectedInstrumentList = Lists.newArrayList(hdfcUPI, sbiUPI, sbiNetBanking, sbiDebitCard);
     final Cart cart = new Cart(LineOfBusiness.INVESTMENT, new CartDetail(10000d, Collections.singletonList(getCartItem(10000d))));
     final List<PaymentInstrument> actualList = paymentRecommender.recommendPaymentInstruments(user, cart);


 }

    private static PaymentInstrument createCreditCardPaymentInstrument() {
        return new PaymentInstrument(PaymentInstrumentType.CREDIT_CARD, "credit_card_number", Issuer.HDFC, "HDFC Diner", 0.7);
    }

    private static PaymentInstrument createUPIPaymentInstrument() {
        return new PaymentInstrument(PaymentInstrumentType.UPI, "upi_handle", Issuer.SBI, "SBI UPI", 0.85);
    }

    private static PaymentInstrument createNetBankingPaymentInstrument() {
        return new PaymentInstrument(PaymentInstrumentType.NETBANKING, "bank_account_number", Issuer.SBI, "Savings Account", 0.86);
    }

    private static PaymentInstrument createDebitCardInstrument() {
        return new PaymentInstrument(PaymentInstrumentType.DEBIT_CARD, "debit_card_number", Issuer.SBI, "SBI Debit Card", 0.91);
    }

    private static CartItem getCartItem(final Double amount) {
        return new CartItem(UUID.randomUUID().toString(), "Reliance Stocks", amount);
    }
}
