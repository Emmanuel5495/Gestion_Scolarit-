package bf.ensp.scolarite.payment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Primary
@Slf4j
public class MockPaymentServiceImpl implements PaymentService {

    @Override
    public PaymentResult initierPaiement(PaymentRequest request) {
        log.info("MOCK PAIEMENT — Simulation paiement de {} pour {}",
                request.getMontant(), request.getEmailEtudiant());
        return PaymentResult.builder()
                .success(true)
                .transactionId("MOCK-" + UUID.randomUUID())
                .message("Paiement simulé avec succès")
                .build();
    }

    @Override
    public PaymentResult verifierStatut(String transactionId) {
        log.info("MOCK PAIEMENT — Vérification statut : {}", transactionId);
        return PaymentResult.builder()
                .success(true)
                .transactionId(transactionId)
                .message("Paiement confirmé (simulation)")
                .build();
    }
}
