package pe.edu.vallegrande.supplier.service.impl;

import pe.edu.vallegrande.supplier.model.suppliermodel;
import pe.edu.vallegrande.supplier.repository.supplierrepository;
import pe.edu.vallegrande.supplier.service.supplierservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.util.Optional;
import java.util.List;

@Slf4j
@Service
public class supplierserviceimpl implements supplierservice {

    private final supplierrepository supplierrepository;

    @Autowired
    public supplierserviceimpl(supplierrepository supplierRepository) {
        this.supplierrepository = supplierRepository;
    }

    @Override
    public List<suppliermodel> getAll() {
        log.info("Lista de Proveedores");
        return supplierrepository.findAll();
    }

    @Override
    public suppliermodel save(suppliermodel supplier) {
        log.info("Registro de Proveedor: " + supplier.toString());
        supplier.setStates("A");
        return supplierrepository.save(supplier);
    }

    @Override
    public suppliermodel update(suppliermodel supplier) {
        log.info("Actualizando Proveedor: " + supplier.toString());
        Optional<suppliermodel> supplierExistente = supplierrepository.findById(supplier.getSupplierId());
        if (supplierExistente.isPresent()) {
            return supplierrepository.save(supplier);
        } else {
            throw new RuntimeException("Proveedor no encontrado con ID: " + supplier.getSupplierId());
        }
    }

    @Override
    public void deleteById(Long id) {
        log.info("Eliminando Proveedor con ID: " + id);
        supplierrepository.deleteById(id);
    }
}