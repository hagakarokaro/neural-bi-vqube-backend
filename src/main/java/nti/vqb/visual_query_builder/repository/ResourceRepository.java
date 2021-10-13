package nti.vqb.visual_query_builder.repository;

import nti.vqb.visual_query_builder.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository ("resourceRepository")
public interface ResourceRepository extends JpaRepository<Resource, Integer> {

}
