package tw.idv.frank.customer.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.idv.frank.customer.model.entity.Customer;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
