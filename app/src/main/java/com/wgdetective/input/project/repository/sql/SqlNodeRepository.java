package com.wgdetective.input.project.repository.sql;

import com.wgdetective.input.project.repository.sql.entity.NodeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Reactive database node repository.
 */
@Repository
public interface SqlNodeRepository extends CrudRepository<NodeEntity, Long> {

}
