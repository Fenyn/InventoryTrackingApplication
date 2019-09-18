/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthonyknight.wguinventorytrackingapplication.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Midge
 */
public class Inventory {

    ObservableList<Part> allParts;
    ObservableList<Product> allProducts;

    public void addPart(Part newPart) {
        if (allParts == null) {
            allParts = FXCollections.observableArrayList();
            newPart.setID(1);
        } else {
            Part part = allParts.get(allParts.size() - 1);
            newPart.setID(part.id + 1);
        }

        allParts.add(newPart);
    }

    public void addProduct(Product newProduct) {
        if (allProducts == null) {
            allProducts = FXCollections.observableArrayList();
            newProduct.setID(1);
        } else {
            Product product = allProducts.get(allProducts.size() - 1);
            newProduct.setID(product.id + 1);
        }

        allProducts.add(newProduct);
    }

    public Part lookupPart(int partID) {
        for (Part part : allParts) {
            if (part.getID() == partID) {
                return part;
            }
        }
        return null;
    }

    public Product lookupProduct(int productID) {
        for (Product product : allProducts) {
            if (product.getID() == productID) {
                return product;
            }
        }
        return null;
    }

    public ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> foundParts = FXCollections.emptyObservableList();

        for (Part part : allParts) {
            if (part.name.equalsIgnoreCase(partName)) {
                foundParts.add(part);
            }
        }

        return foundParts;
    }

    public ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> foundProducts = FXCollections.emptyObservableList();

        for (Product product : allProducts) {
            if (product.name.equalsIgnoreCase(productName)) {
                foundProducts.add(product);
            }
        }
        return foundProducts;
    }

    public void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    public void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    public void deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
    }

    public void deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
    }

    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }
    
    public int GetIndexOfPartByID(int partID){
        Part part = lookupPart(partID);
        int index = allParts.indexOf(part);
        return index;
    }

    public int GetIndexOfProductByID(int id) {
        Product prod = lookupProduct(id);
        int index = allParts.indexOf(prod);
        return index;
    }

}
