package pe.edu.vallegrande.backend.service.impl;

import pe.edu.vallegrande.backend.model.Supplier;
import pe.edu.vallegrande.backend.repository.SupplierRepository;
import pe.edu.vallegrande.backend.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.util.Optional;
import java.util.List;

@Slf4j
@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public List<Supplier> getAll() {
        log.info("Listar Proveedor");
        return supplierRepository.findAll();
    }

    @Override
    public Supplier save(Supplier supplier) {
        log.info("Registrar Proveedor: " + supplier.toString());
        supplier.setStates("A");
        return supplierRepository.save(supplier);
    }

    @Override
    public Supplier update(Supplier supplier) {
        log.info("Actualizando Proveedor: " + supplier.toString());
        Optional<Supplier> clienteExistente = supplierRepository.findById(supplier.getSupplierId());
        if (clienteExistente.isPresent()) {
            return supplierRepository.save(supplier);
        } else {
            throw new RuntimeException("Proveedor no encontrado con ID: " + supplier.getSupplierId());
        }
    }

    @Override
    public void deleteById(Long id) {
        log.info("Eliminando Proveedor con ID: " + id);
        supplierRepository.deleteById(id);
    }
}