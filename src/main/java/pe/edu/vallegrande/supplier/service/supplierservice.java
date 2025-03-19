package pe.edu.vallegrande.supplier.service;

import pe.edu.vallegrande.supplier.model.suppliermodel;
import java.util.List;

public interface supplierservice {

    List<suppliermodel> getAll();

    suppliermodel save(suppliermodel supplier);

    suppliermodel update(suppliermodel supplier);

    void deleteById(Long id);
}