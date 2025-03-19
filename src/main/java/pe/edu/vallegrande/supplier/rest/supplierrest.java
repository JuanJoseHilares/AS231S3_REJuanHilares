package pe.edu.vallegrande.supplier.rest;

import pe.edu.vallegrande.supplier.model.suppliermodel;
import pe.edu.vallegrande.supplier.service.supplierservice;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/api/supplier")
public class supplierrest {

    private supplierservice supplierservice;

    public void SupplierRest(supplierservice supplierService) {
        this.supplierservice = supplierService;
    }


    @GetMapping
    public List<suppliermodel> getAll() {
        return supplierservice.getAll();
    }

    @PostMapping("/save")
    public suppliermodel save(@RequestBody suppliermodel supplier) {
        return supplierservice.save(supplier);
    }

    @PutMapping("/update")
    public suppliermodel update(@RequestBody suppliermodel supplier) {
        return supplierservice.update(supplier);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        supplierservice.deleteById(id);
    }
}