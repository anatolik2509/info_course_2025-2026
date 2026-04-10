package org.example.repository.jpa;

import org.example.entity.Cabinet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CabinetJpaRepository extends JpaRepository<Cabinet, Long> {
}
