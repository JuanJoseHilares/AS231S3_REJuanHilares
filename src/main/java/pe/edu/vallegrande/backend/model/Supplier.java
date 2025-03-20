package pe.edu.vallegrande.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private Long supplierId;

    @Column(name = "names", nullable = false, length = 90)
    private String names;

    @Column(name = "addres", nullable = false, length = 50)
    private String address;

    @Column(name = "city", nullable = false, length = 25)
    private String city;

    @Column(name = "phone_number", length = 9)
    private String phoneNumber;

    @Column(name = "whatsapp_number", nullable = false, length = 9)
    private String whatsappNumber;

    @Column(name = "reference_number", length = 9)
    private String referenceNumber;

    @Column(name = "email", nullable = false, length = 90)
    private String email;

    @Column(name = "admin_id", nullable = false)
    private int adminId;

    @Column(name = "states", columnDefinition = "char(1) default 'A'")
    private String states;
}