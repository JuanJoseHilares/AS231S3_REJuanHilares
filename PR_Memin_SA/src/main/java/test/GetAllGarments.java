package test;

import model.Garments;
import service.ServiceGarments;

import java.util.List;

public class GetAllGarments {

    public static void main(String[] args) {
        ServiceGarments serviceGarments = new ServiceGarments();
        List<Garments> garments = serviceGarments.getAllGarments();

        if (!garments.isEmpty()) {
            System.out.println("Lista de prendas:");
            for (Garments garment : garments) {
                System.out.println("ID: " + garment.getId());
                System.out.println("Código: " + garment.getCode());
                System.out.println("Nombre: " + garment.getName());
                System.out.println("Marca: " + garment.getBrand());
                System.out.println("Categoría: " + garment.getCategory());
                System.out.println("Precio: " + garment.getPrice());
                System.out.println("Estado: " + garment.getState());
                System.out.println("------------------------");
            }
        } else {
            System.out.println("No se encontraron prendas.");
        }
    }
}