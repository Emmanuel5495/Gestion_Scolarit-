package bf.ensp.scolarite.payment;

public interface PaymentService {
    PaymentResult initierPaiement(PaymentRequest request);
    PaymentResult verifierStatut(String transactionId);
}
