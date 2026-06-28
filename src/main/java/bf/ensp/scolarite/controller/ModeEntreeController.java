package bf.ensp.scolarite.controller;

import bf.ensp.scolarite.dto.response.ApiResponse;
import bf.ensp.scolarite.dto.response.ModeEntreeResponse;
import bf.ensp.scolarite.entity.ModeEntree;
import bf.ensp.scolarite.repository.ModeEntreeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/modes-entree")
@RequiredArgsConstructor
public class ModeEntreeController {

    private final ModeEntreeRepository modeEntreeRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ModeEntreeResponse>>> getAll() {
        List<ModeEntreeResponse> modes = modeEntreeRepository.findAll()
                .stream()
                .map(m -> ModeEntreeResponse.builder()
                        .id(m.getId())
                        .libelle(m.getLibelle())
                        .type(m.getType())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(
                ApiResponse.success("Modes d'entrée récupérés", modes)
        );
    }
}
