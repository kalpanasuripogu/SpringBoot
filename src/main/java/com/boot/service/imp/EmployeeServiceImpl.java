package com.boot.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.exception.ResourceNotFoundException;
import com.boot.model.Employee;
import com.boot.repository.EmployeeRepository;
import com.boot.service.EmployeeService;




@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        // Log the received employee details
        System.out.println("Received employee details: " + employee);

        // Validate and set the required fields before saving
        if (employee.getFirstName() == null || employee.getFirstName().trim().isEmpty()) {
            // Handle the validation logic as needed, e.g., throw an exception or set a default value.
            // For simplicity, let's assume a default value "Unknown" is set when firstName is null or empty.
            employee.setFirstName("Unknown");
        }

        // Log the values after the first name is processed
        System.out.println("Processed employee details after first name: " + employee);

        // Handle optional fields
        if (employee.getLastName() == null || employee.getLastName().trim().isEmpty()) {
            // Set a default value for lastName only if it is null or empty
            employee.setLastName("Unknown");
        }

        // Log the final processed employee details
        System.out.println("Final processed employee details: " + employee);

        // Add similar validation for other optional fields

        // Save the employee
        return employeeRepository.save(employee);
    }
    
    @Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

    @Override
	public Employee getEmployeeById(long id) {
//		Optional<Employee> employee = employeeRepository.findById(id);
//		if(employee.isPresent()) {
//			return employee.get();
//		}else {
//			throw new ResourceNotFoundException("Employee", "Id", id);
//		}
		return employeeRepository.findById(id).orElseThrow(() -> 
						new ResourceNotFoundException("Employee", "Id", id));
		
	}

    @Override
    public Employee updateEmployee(Employee employee, long id) {
        System.out.println("Updating employee with id: " + id);

        // Check whether an employee with the given id exists in the database
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "Id", id));

        // Log the existing and updated employee details
        System.out.println("Existing employee: " + existingEmployee);
        System.out.println("Updated employee: " + employee);

        // Update the fields with the values from the request payload
        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setEmail(employee.getEmail());

        // Save the updated employee to the database and return the updated instance
        Employee updatedEmployee = employeeRepository.save(existingEmployee);

        // Log the updated employee details
        System.out.println("Updated employee details: " + updatedEmployee);

        return updatedEmployee;
    }
    @Override
	public void deleteEmployee(long id) {
		
		// check whether a employee exist in a DB or not
		employeeRepository.findById(id).orElseThrow(() -> 
								new ResourceNotFoundException("Employee", "Id", id));
		employeeRepository.deleteById(id);
	}
	

}