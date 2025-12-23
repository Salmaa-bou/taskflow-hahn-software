package ma.ensah.taskflowprojecthahnsoftwarebackend.repository;


import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.entity.TaskAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskAssignmentRepository extends JpaRepository<TaskAssignment, Long> {

    List<TaskAssignment> findByTaskId(Long taskId);

    List<TaskAssignment> findByAssigneeId(Long userId);

    Optional<TaskAssignment> findByTaskIdAndAssigneeId(Long taskId, Long userId);

    boolean existsByTaskIdAndAssigneeId(Long taskId, Long userId);

    void deleteByTaskIdAndAssigneeId(Long taskId, Long userId);
}