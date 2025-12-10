package org.virtualmemory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.virtualmemory.entity.ThreadSimulationResult;
import org.virtualmemory.model.ConcurrencySimulator;
import org.virtualmemory.repository.ThreadSimulationResultRepository;
import org.virtualmemory.service.AIRecommendationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/concurrency")
public class ConcurrencyController {

    @Autowired
    private AIRecommendationService aiService;

    @Autowired
    private ThreadSimulationResultRepository threadSimulationRepository;

    @PostMapping("/simulate/deadlock")
    public Map<String, Object> simulateDeadlock() {
        ConcurrencySimulator simulator = new ConcurrencySimulator();
        long startTime = System.currentTimeMillis();

        simulator.simulateDeadlock();

        long executionTime = System.currentTimeMillis() - startTime;
        Map<String, Object> state = simulator.getSimulationState();

        String aiRecommendation = aiService.generateRecommendation("DEADLOCK", state);

        ThreadSimulationResult result = new ThreadSimulationResult();
        result.setSimulationType("DEADLOCK");
        result.setThreadCount(simulator.getThreads().size());
        result.setDeadlockDetected(simulator.isDeadlockDetected());
        result.setExecutionTimeMs(executionTime);
        result.setAiRecommendation(aiRecommendation);
        threadSimulationRepository.save(result);

        Map<String, Object> response = new HashMap<>(state);
        response.put("aiRecommendation", aiRecommendation);
        response.put("executionTime", executionTime);

        return response;
    }

    @PostMapping("/simulate/race-condition")
    public Map<String, Object> simulateRaceCondition(@RequestBody Map<String, Object> request) {
        Integer numThreads = (Integer) request.getOrDefault("numThreads", 5);
        Integer sharedCounter = (Integer) request.getOrDefault("sharedCounter", 0);

        ConcurrencySimulator simulator = new ConcurrencySimulator();
        long startTime = System.currentTimeMillis();

        simulator.simulateRaceCondition(numThreads, sharedCounter);

        long executionTime = System.currentTimeMillis() - startTime;
        Map<String, Object> state = simulator.getSimulationState();

        String aiRecommendation = aiService.generateRecommendation("RACE_CONDITION", state);

        ThreadSimulationResult result = new ThreadSimulationResult();
        result.setSimulationType("RACE_CONDITION");
        result.setThreadCount(numThreads);
        result.setDeadlockDetected(false);
        result.setExecutionTimeMs(executionTime);
        result.setAiRecommendation(aiRecommendation);
        threadSimulationRepository.save(result);

        Map<String, Object> response = new HashMap<>(state);
        response.put("aiRecommendation", aiRecommendation);
        response.put("executionTime", executionTime);

        return response;
    }

    @PostMapping("/simulate/producer-consumer")
    public Map<String, Object> simulateProducerConsumer(@RequestBody Map<String, Object> request) {
        Integer producers = (Integer) request.getOrDefault("producers", 2);
        Integer consumers = (Integer) request.getOrDefault("consumers", 2);
        Integer bufferSize = (Integer) request.getOrDefault("bufferSize", 5);

        ConcurrencySimulator simulator = new ConcurrencySimulator();
        long startTime = System.currentTimeMillis();

        simulator.simulateProducerConsumer(producers, consumers, bufferSize);

        long executionTime = System.currentTimeMillis() - startTime;
        Map<String, Object> state = simulator.getSimulationState();

        String aiRecommendation = aiService.generateRecommendation("PRODUCER_CONSUMER", state);

        ThreadSimulationResult result = new ThreadSimulationResult();
        result.setSimulationType("PRODUCER_CONSUMER");
        result.setThreadCount(producers + consumers);
        result.setDeadlockDetected(false);
        result.setExecutionTimeMs(executionTime);
        result.setAiRecommendation(aiRecommendation);
        threadSimulationRepository.save(result);

        Map<String, Object> response = new HashMap<>(state);
        response.put("aiRecommendation", aiRecommendation);
        response.put("executionTime", executionTime);

        return response;
    }

    @PostMapping("/simulate/thread-pool")
    public Map<String, Object> simulateThreadPool(@RequestBody Map<String, Object> request) {
        Integer poolSize = (Integer) request.getOrDefault("poolSize", 4);
        Integer taskCount = (Integer) request.getOrDefault("taskCount", 10);

        ConcurrencySimulator simulator = new ConcurrencySimulator();
        long startTime = System.currentTimeMillis();

        simulator.simulateThreadPool(poolSize, taskCount);

        long executionTime = System.currentTimeMillis() - startTime;
        Map<String, Object> state = simulator.getSimulationState();

        String aiRecommendation = aiService.generateRecommendation("THREAD_POOL", state);

        ThreadSimulationResult result = new ThreadSimulationResult();
        result.setSimulationType("THREAD_POOL");
        result.setThreadCount(poolSize);
        result.setDeadlockDetected(false);
        result.setExecutionTimeMs(executionTime);
        result.setAiRecommendation(aiRecommendation);
        threadSimulationRepository.save(result);

        Map<String, Object> response = new HashMap<>(state);
        response.put("aiRecommendation", aiRecommendation);
        response.put("executionTime", executionTime);

        return response;
    }

    @GetMapping("/history")
    public List<ThreadSimulationResult> getSimulationHistory() {
        return threadSimulationRepository.findAll();
    }

    @GetMapping("/history/{type}")
    public List<ThreadSimulationResult> getSimulationHistoryByType(@PathVariable String type) {
        return threadSimulationRepository.findBySimulationTypeOrderByCreatedAtDesc(type.toUpperCase());
    }
}
