package ma.ensah.taskflowprojecthahnsoftwarebackend.repository;


import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.entity.Task;
import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByProjectId(Long projectId);

    List<Task> findByProjectIdAndStatus(Long projectId, TaskStatus status);

    List<Task> findByCreatedById(Long userId);

    @Query("SELECT t FROM Task t JOIN t.assignments a WHERE a.assignee.id = :userId")
    List<Task> findTasksAssignedToUser(Long userId);

    long countByProjectIdAndCompleted(Long projectId, Boolean completed);
}