package com.restaurante.inventario.dao;

import com.restaurante.inventario.modelo.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class ProductoDAO {

    private EntityManagerFactory emf;

    public ProductoDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void crear(Producto producto) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(producto);
            em.getTransaction().commit();
            System.out.println("Producto guardado correctamente.");
        } finally {
            em.close();
        }
    }

    public List<Producto> listar() {
        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery("SELECT p FROM Producto p", Producto.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Producto buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();

        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public void actualizar(Producto producto) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(producto);
            em.getTransaction().commit();
            System.out.println("Producto actualizado correctamente.");
        } finally {
            em.close();
        }
    }

    public void eliminar(Long id) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Producto producto = em.find(Producto.class, id);

            if (producto != null) {
                em.remove(producto);
                System.out.println("Producto eliminado correctamente.");
            } else {
                System.out.println("No se encontro un producto con ese ID.");
            }

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}