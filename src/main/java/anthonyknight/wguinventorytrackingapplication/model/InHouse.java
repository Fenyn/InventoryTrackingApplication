/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthonyknight.wguinventorytrackingapplication.model;

/**
 *
 * @author Anthony Knight
 */
public class InHouse extends Part{
    int machineId;

    public InHouse(String name, double price, int stock, int min, int max, int machineid) {
        super(name, price, stock, min, max);
        this.machineId = machineid;
    }
    
    public void setMachineId(int machineid){
        this.machineId = machineid;
    }
    
    public int getMachineId(){
        return machineId;
    }
    
    @Override
    public String getVariable(){
        return String.valueOf(machineId);
    }
    
    @Override
    public void setVariable(String input){
        int machID = Integer.valueOf(input);
        setMachineId(machID);
    }
}
