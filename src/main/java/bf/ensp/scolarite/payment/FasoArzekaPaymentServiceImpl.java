package bf.ensp.scolarite.payment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FasoArzekaPaymentServiceImpl implements PaymentService {

    // TODO : implémenter quand l'API FASO ARZEKA sera disponible
    // Pour activer : ajoute @Primary ici et retire-le de MockPaymentServiceImpl

    @Override
    public PaymentResult initierPaiement(PaymentRequest request) {
        throw new UnsupportedOperationException(
                "Intégration FASO ARZEKA non encore implémentée"
        );
    }

    @Override
    public PaymentResult verifierStatut(String transactionId) {
        throw new UnsupportedOperationException(
                "Intégration FASO ARZEKA non encore implémentée"
        );
    }
}
