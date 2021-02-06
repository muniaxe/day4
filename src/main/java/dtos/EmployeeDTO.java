/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Employee;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tha
 */
public class EmployeeDTO {

    private long id;
    private String name;
    private String address;

    public EmployeeDTO(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.address = employee.getAddress();
    }

    public static List<EmployeeDTO> getDtos(List<Employee> rms) {
        List<EmployeeDTO> rmdtos = new ArrayList();
        rms.forEach(rm -> rmdtos.add(new EmployeeDTO(rm)));
        return rmdtos;
    }

}
