package pe.edu.vallegrande.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.vallegrande.backend.model.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    
}
