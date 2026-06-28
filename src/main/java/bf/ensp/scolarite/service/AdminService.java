package bf.ensp.scolarite.service;

import bf.ensp.scolarite.dto.request.CreateUtilisateurRequest;
import bf.ensp.scolarite.dto.response.EtudiantResponse;
import bf.ensp.scolarite.entity.ServicePedagogique;

import java.util.List;

public interface AdminService {
    ServicePedagogique creerCompteServicePedagogique(CreateUtilisateurRequest request);
    List<EtudiantResponse> getTousLesEtudiants();
}
