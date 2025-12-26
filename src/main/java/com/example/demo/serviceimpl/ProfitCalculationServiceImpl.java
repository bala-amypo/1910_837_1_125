import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

@PersistenceContext
private EntityManager entityManager;

public List<ProfitCalculationRecord> findRecordsWithMarginBetween(Double min, Double max) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<ProfitCalculationRecord> query = cb.createQuery(ProfitCalculationRecord.class);
    Root<ProfitCalculationRecord> root = query.from(ProfitCalculationRecord.class);
    
    query.select(root).where(cb.between(root.get("profitMargin"), min, max));
    
    return entityManager.createQuery(query).getResultList();
}