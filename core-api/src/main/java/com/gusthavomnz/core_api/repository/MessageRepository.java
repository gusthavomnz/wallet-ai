package com.gusthavomnz.core_api.repository;

import com.gusthavomnz.core_api.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
