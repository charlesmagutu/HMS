package org.condabu.notificationservice.repository;

import org.condabu.notificationservice.entity.SMS;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SMSRepository extends JpaRepository<SMS, Long> {
    Optional<SMS> findByType(String type);

}
