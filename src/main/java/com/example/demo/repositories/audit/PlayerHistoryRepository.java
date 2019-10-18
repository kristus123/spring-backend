package com.example.demo.repositories.audit;

import com.example.demo.envers.AuditQueryResult;
import com.example.demo.envers.AuditQueryUtils;
import com.example.demo.models.PlayerHistoryModel;
import com.example.demo.models.PlayerModel;
//import org.hibernate.envers.AuditReader;
//import org.hibernate.envers.AuditReaderFactory;
//import org.hibernate.envers.query.AuditEntity;
//import org.hibernate.envers.query.AuditQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlayerHistoryRepository {//implements IPlayerHistoryRepository {
/*
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<PlayerHistoryModel> listPlayerHistoryRevisions(Integer playerId) {
        // Create the Audit Reader. It uses the EntityManager, which will be opened when
        // starting the new Transation and closed when the Transaction finishes.
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        // Create the Query:
        AuditQuery auditQuery = auditReader.createQuery()
                .forRevisionsOfEntity(PlayerModel.class, false, true)
                .add(AuditEntity.id().eq(playerId));
        // We don't operate on the untyped Results, but cast them into a List of AuditQueryResult:
        return AuditQueryUtils.getAuditQueryResults(auditQuery, PlayerModel.class).stream()
                // Turn into the CustomerHistory Domain Object:
                .map(x -> getCustomerHistory(x))
                // And collect the Results:
                .collect(Collectors.toList());
    }

    private static PlayerHistoryModel getCustomerHistory(AuditQueryResult<PlayerModel> auditQueryResult) {
        //System.out.println("REVISION ID: " + AuditEntity.property("rev").desc().);
        return new PlayerHistoryModel(
                auditQueryResult.getEntity(),
                auditQueryResult.getType()
                //auditQueryResult.getRevision().getRevisionNumber() NOT WORKING FOR SOME REASON!!!
        );
    }

    public void findAll() {}


 */

}
