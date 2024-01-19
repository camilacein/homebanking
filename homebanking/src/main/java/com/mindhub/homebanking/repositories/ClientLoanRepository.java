package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.ClientLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource
public interface ClientLoanRepository extends JpaRepository <ClientLoan, Long> {

}
