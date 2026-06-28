package bf.ensp.scolarite.service;

import bf.ensp.scolarite.entity.Preinscription;

public interface MailService {
    void envoyerNotificationValidation(Preinscription preinscription);
    void envoyerNotificationRejet(Preinscription preinscription);
    void envoyerNotificationConcours(Preinscription preinscription);
}
