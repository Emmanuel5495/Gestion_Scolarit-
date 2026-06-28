package bf.ensp.scolarite.controller;

import bf.ensp.scolarite.dto.response.ApiResponse;
import bf.ensp.scolarite.entity.ModeEntree;
import bf.ensp.scolarite.repository.ModeEntreeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modes-entree")
@RequiredArgsConstructor
public class ModeEntreeController {

    private final ModeEntreeRepository modeEntreeRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ModeEntree>>> getAll() {
        return ResponseEntity.ok(
                ApiResponse.success("Modes d'entrée récupérés",
                        modeEntreeRepository.findAll())
        );
    }
}
