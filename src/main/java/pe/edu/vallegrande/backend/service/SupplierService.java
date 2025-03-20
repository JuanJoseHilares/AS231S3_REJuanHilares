package pe.edu.vallegrande.backend.service;

import pe.edu.vallegrande.backend.model.Supplier;

import java.util.List;

public interface SupplierService {

    List<Supplier> getAll();

    Supplier save(Supplier supplier);

    Supplier update(Supplier supplier);

    void deleteById(Long id);
}
