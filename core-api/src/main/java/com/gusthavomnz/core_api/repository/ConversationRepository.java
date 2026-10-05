package com.gusthavomnz.core_api.repository;

import com.gusthavomnz.core_api.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
}
