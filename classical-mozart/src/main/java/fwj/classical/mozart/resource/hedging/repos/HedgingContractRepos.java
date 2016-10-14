package fwj.classical.mozart.resource.hedging.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import fwj.classical.mozart.resource.hedging.entity.HedgingContract;

@RepositoryRestResource(exported = false)
public interface HedgingContractRepos extends JpaRepository<HedgingContract, Integer> {

	List<HedgingContract> findByCode(String code);
	
}
