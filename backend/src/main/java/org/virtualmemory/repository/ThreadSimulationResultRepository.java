package org.virtualmemory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.virtualmemory.entity.ThreadSimulationResult;

import java.util.List;

@Repository
public interface ThreadSimulationResultRepository extends JpaRepository<ThreadSimulationResult, Long> {
    List<ThreadSimulationResult> findBySimulationTypeOrderByCreatedAtDesc(String simulationType);
    List<ThreadSimulationResult> findByDeadlockDetected(Boolean deadlockDetected);
}
