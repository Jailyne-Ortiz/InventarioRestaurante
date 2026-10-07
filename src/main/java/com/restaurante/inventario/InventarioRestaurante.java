package com.restaurante.inventario;

import com.restaurante.inventario.dao.ProductoDAO;
import com.restaurante.inventario.modelo.Producto;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class InventarioRestaurante {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("InventarioPU");
        ProductoDAO productoDAO = new ProductoDAO(emf);
        Scanner scanner = new Scanner(System.in);

        int opcion;

        do {
            System.out.println("\n===== CRUD INVENTARIO RESTAURANTE =====");
            System.out.println("1. Crear producto");
            System.out.println("2. Listar productos");
            System.out.println("3. Actualizar producto");
            System.out.println("4. Eliminar producto");
            System.out.println("5. Buscar producto por ID");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Nombre del producto: ");
                    String nombre = scanner.nextLine();

                    System.out.print("Categoria: ");
                    String categoria = scanner.nextLine();

                    System.out.print("Cantidad: ");
                    int cantidad = scanner.nextInt();

                    System.out.print("Precio: ");
                    BigDecimal precio = scanner.nextBigDecimal();

                    Producto nuevoProducto = new Producto(nombre, categoria, cantidad, precio);
                    productoDAO.crear(nuevoProducto);
                    break;

                case 2:
                    List<Producto> productos = productoDAO.listar();

                    if (productos.isEmpty()) {
                        System.out.println("No hay productos registrados.");
                    } else {
                        System.out.println("\nLista de productos:");
                        for (Producto producto : productos) {
                            System.out.println(producto);
                        }
                    }
                    break;

                case 3:
                    System.out.print("Ingrese el ID del producto a actualizar: ");
                    Long idActualizar = scanner.nextLong();
                    scanner.nextLine();

                    Producto productoActualizar = productoDAO.buscarPorId(idActualizar);

                    if (productoActualizar != null) {
                        System.out.print("Nuevo nombre: ");
                        productoActualizar.setNombre(scanner.nextLine());

                        System.out.print("Nueva categoria: ");
                        productoActualizar.setCategoria(scanner.nextLine());

                        System.out.print("Nueva cantidad: ");
                        productoActualizar.setCantidad(scanner.nextInt());

                        System.out.print("Nuevo precio: ");
                        productoActualizar.setPrecio(scanner.nextBigDecimal());

                        productoDAO.actualizar(productoActualizar);
                    } else {
                        System.out.println("Producto no encontrado.");
                    }
                    break;

                case 4:
                    System.out.print("Ingrese el ID del producto a eliminar: ");
                    Long idEliminar = scanner.nextLong();

                    productoDAO.eliminar(idEliminar);
                    break;

                case 5:
                    System.out.print("Ingrese el ID del producto: ");
                    Long idBuscar = scanner.nextLong();

                    Producto productoEncontrado = productoDAO.buscarPorId(idBuscar);

                    if (productoEncontrado != null) {
                        System.out.println(productoEncontrado);
                    } else {
                        System.out.println("Producto no encontrado.");
                    }
                    break;

                case 0:
                    System.out.println("Saliendo del programa...");
                    break;

                default:
                    System.out.println("Opcion no valida.");
                    break;
            }

        } while (opcion != 0);

        scanner.close();
        emf.close();
    }
}