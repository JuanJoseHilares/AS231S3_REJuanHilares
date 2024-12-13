package test;

import model.Kardex;
import service.ServiceKardex;

import java.util.List;

public class GetAllKardex {

    public static void main(String[] args) {
        ServiceKardex serviceKardex = new ServiceKardex();
        List<Kardex> kardexList = serviceKardex.getAllKardex();

        if (!kardexList.isEmpty()) {
            System.out.println("Lista de Kardex:");
            for (Kardex kardex : kardexList) {
                System.out.println("ID: " + kardex.getId());
                System.out.println("Nombre del Producto: " + kardex.getProductName());
                System.out.println("Fecha de Registro: " + kardex.getRegistration_date());
                System.out.println("Mes: " + kardex.getMonth());
                System.out.println("Movimiento: " + kardex.getMotion());
                System.out.println("Cantidad: " + kardex.getAmount());
                System.out.println("Costo Unitario: " + kardex.getUnit_cost());
                System.out.println("Costo Total: " + kardex.getTotal_cost());
                System.out.println("Estado: " + kardex.getState());
                System.out.println("------------------------");
            }
        } else {
            System.out.println("No se encontraron registros en el Kardex.");
        }
    }
}