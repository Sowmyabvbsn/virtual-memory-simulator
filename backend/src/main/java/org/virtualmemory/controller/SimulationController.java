package org.virtualmemory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.virtualmemory.entity.SimulationResult;
import org.virtualmemory.model.Simulator;
import org.virtualmemory.repository.SimulationResultRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
public class SimulationController {

    @Autowired
    private SimulationResultRepository simulationRepository;

    @PostMapping("/simulate")
    public Map<String, Object> simulate(@RequestBody Map<String, Object> request) {
        List<Integer> sequence = (List<Integer>) request.get("sequence");

        Integer ramSize = (Integer) request.getOrDefault("ramSize", 3);
        Integer maxPages = (Integer) request.getOrDefault("maxPages", 10);

        Simulator sim = new Simulator(ramSize, maxPages);

        for (int pageId : sequence) {
            sim.accessPage(pageId);
        }

        SimulationResult result = new SimulationResult();
        result.setSimulationType("VIRTUAL_MEMORY");
        result.setInputSequence(sequence.toString());
        result.setHits(sim.getHits());
        result.setFaults(sim.getFaults());
        result.setRamSize(ramSize);
        simulationRepository.save(result);

        Map<String, Object> response = new HashMap<>();
        response.put("frames", getRAMFrames(sim));
        response.put("hits", sim.getHits());
        response.put("faults", sim.getFaults());

        return response;
    }

    // Convert RAM in list of IDs for frontend
    private List<Integer> getRAMFrames(Simulator sim) {
        Integer[] ramFrames = new Integer[sim.getMemory().getFrames().length];
        for (int i = 0; i < sim.getMemory().getFrames().length; i++) {
            if (sim.getMemory().getFrames()[i] != null) {
                ramFrames[i] = sim.getMemory().getFrames()[i].getId();
            } else {
                ramFrames[i] = -1; // frame gol
            }
        }
        return List.of(ramFrames);
    }
}
