package pe.edu.vallegrande.supplier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.vallegrande.supplier.model.suppliermodel;

public interface supplierrepository extends JpaRepository<suppliermodel, Long> {

}
