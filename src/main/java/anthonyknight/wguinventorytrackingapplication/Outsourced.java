/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthonyknight.wguinventorytrackingapplication;

/**
 *
 * @author Midge
 */
public class Outsourced extends Part{
    
    String companyName;
    
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyname) {
        super(id, name, price, stock, min, max);
        this.companyName = companyname;
    }
    
    public void setCompanyName(String companyname){
        this.companyName = companyname;
    }    
    
    public String getCompanyName(){
        return companyName;
    }
}
