package org.tain.repository.campPageCharUdf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import org.tain.domain.campPageCharUdf.CampPageCharUdf;

@RepositoryRestResource
@Transactional
public interface CampPageCharUdfRepository extends JpaRepository<CampPageCharUdf, Long>{

}
