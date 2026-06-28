package bf.ensp.scolarite.service;

import bf.ensp.scolarite.dto.request.LoginRequest;
import bf.ensp.scolarite.dto.request.RegisterEtudiantRequest;
import bf.ensp.scolarite.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse login(LoginRequest request);
    AuthResponse register(RegisterEtudiantRequest request);
}
