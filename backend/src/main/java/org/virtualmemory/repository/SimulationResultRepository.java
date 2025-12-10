package org.virtualmemory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.virtualmemory.entity.SimulationResult;

import java.util.List;

@Repository
public interface SimulationResultRepository extends JpaRepository<SimulationResult, Long> {
    List<SimulationResult> findBySimulationTypeOrderByCreatedAtDesc(String simulationType);
}
