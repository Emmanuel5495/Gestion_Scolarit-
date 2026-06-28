package bf.ensp.scolarite.service.impl;

import bf.ensp.scolarite.entity.Preinscription;
import bf.ensp.scolarite.enums.TypeModeEntree;
import bf.ensp.scolarite.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Override
    public void envoyerNotificationValidation(Preinscription preinscription) {
        String email = preinscription.getEmail();
        String nom = preinscription.getNom();
        String prenom = preinscription.getPrenom();
        String numeroDossier = preinscription.getNumeroDossier();

        String sujet = "ENSP — Validation de votre dossier";
        String contenu = "<h2>Bonjour " + prenom + " " + nom + ",</h2>"
                + "<p>Votre dossier de préinscription a été <strong>validé</strong>.</p>"
                + "<p>Votre numéro de dossier est : <strong>"
                + numeroDossier + "</strong></p>"
                + "<p>Conservez ce numéro, il vous sera demandé lors de l'inscription.</p>"
                + "<br><p>Service Pédagogique — ENSP</p>";

        envoyerMail(email, sujet, contenu);
    }

    @Override
    public void envoyerNotificationRejet(Preinscription preinscription) {
        String email = preinscription.getEmail();
        String nom = preinscription.getNom();
        String prenom = preinscription.getPrenom();
        String motif = preinscription.getMotifRejet();

        String sujet = "ENSP — Rejet de votre dossier";
        String contenu = "<h2>Bonjour " + prenom + " " + nom + ",</h2>"
                + "<p>Votre dossier de préinscription a été <strong>rejeté</strong>.</p>"
                + "<p>Motif : <strong>" + motif + "</strong></p>"
                + "<p>Pour toute question contactez le service pédagogique.</p>"
                + "<br><p>Service Pédagogique — ENSP</p>";

        envoyerMail(email, sujet, contenu);
    }


    @Override
    public void envoyerNotificationConcours(Preinscription preinscription) {
        String email = preinscription.getEmail();
        String nom = preinscription.getNom();
        String prenom = preinscription.getPrenom();

        String sujet = "ENSP — Autorisation à composer";
        String contenu = "<h2>Bonjour " + prenom + " " + nom + ",</h2>"
                + "<p>Votre dossier a été présélectionné.</p>"
                + "<p>Vous êtes autorisé(e) à composer au concours d'entrée.</p>"
                + "<p>Les détails vous seront communiqués ultérieurement.</p>"
                + "<br><p>Service Pédagogique — ENSP</p>";

        envoyerMail(email, sujet, contenu);
    }

    private void envoyerMail(String destinataire, String sujet, String contenu) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(destinataire);
            helper.setSubject(sujet);
            helper.setText(contenu, true);
            mailSender.send(message);
            log.info("Mail envoyé à : {}", destinataire);
        } catch (MessagingException e) {
            log.error("Erreur envoi mail à {} : {}", destinataire, e.getMessage());
        }
    }
}
