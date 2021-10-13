package nti.vqb.visual_query_builder.repository;

import nti.vqb.visual_query_builder.entity.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository ("connectionRepository")
public interface ConnectionRepository extends JpaRepository<Connection, Integer> {
}
