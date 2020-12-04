package com.biomixers.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventMemberAttendingRepository extends JpaRepository<EventMemberAttending, Integer> {

}
