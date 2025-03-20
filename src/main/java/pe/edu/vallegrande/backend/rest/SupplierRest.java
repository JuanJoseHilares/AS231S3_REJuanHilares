package pe.edu.vallegrande.backend.rest;

import pe.edu.vallegrande.backend.model.Supplier;
import pe.edu.vallegrande.backend.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/api/Supplier")
public class SupplierRest {

    private final SupplierService supplierService;

    @Autowired
    public SupplierRest(SupplierService supplierService) {
        this.supplierService = supplierService;
    }
    
    @GetMapping
    public List<Supplier> getAll(){
        return supplierService.getAll();
    }

    @PostMapping("/save")
    public Supplier save(@RequestBody Supplier supplier) {
        return supplierService.save(supplier);
    }

    @PutMapping("/update")
    public Supplier update(@RequestBody Supplier supplier) {
        return supplierService.update(supplier);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        supplierService.deleteById(id);
    }
}