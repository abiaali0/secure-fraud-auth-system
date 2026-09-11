package com.abia.fraudauth.audit;

import org.springframework.stereotype.Service;

@Service
public class AuditService {
    private final AuditEventRepository events;

    public AuditService(AuditEventRepository events) {
        this.events = events;
    }

    public void record(String actor, String action, String details) {
        AuditEvent event = new AuditEvent();
        event.setActor(actor);
        event.setAction(action);
        event.setDetails(details);
        events.save(event);
    }
}
