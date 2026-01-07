package org.virtualmemory.controller;

import org.springframework.web.bind.annotation.*;
import org.virtualmemory.model.Simulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
public class SimulationController {

    @PostMapping("/simulate")
    public Map<String, Object> simulate(@RequestBody Map<String, Object> request) {
        List<Integer> sequence = (List<Integer>) request.get("sequence");

        Integer ramSize = (Integer) request.getOrDefault("ramSize", 3);
        Integer maxPages = (Integer) request.getOrDefault("maxPages", 10);
        String algorithm = (String) request.getOrDefault("algorithm", "FIFO");

        Simulator sim = new Simulator(ramSize, maxPages, algorithm);

        int[] sequenceArray = sequence.stream().mapToInt(Integer::intValue).toArray();
        sim.setSequence(sequenceArray);

        List<Map<String, Object>> steps = new ArrayList<>();

        for (int pageId : sequence) {
            boolean wasInRAM = sim.getPageTable().getPage(pageId).isInRAM();
            sim.accessPage(pageId);

            Map<String, Object> step = new HashMap<>();
            step.put("pageId", pageId);
            step.put("isHit", wasInRAM);
            step.put("frames", getRAMFrames(sim));
            steps.add(step);
        }

        return Map.of(
                "steps", steps,
                "hits", sim.getHits(),
                "faults", sim.getFaults(),
                "algorithm", algorithm
        );
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
