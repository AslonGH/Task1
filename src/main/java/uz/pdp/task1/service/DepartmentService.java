package uz.pdp.task1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task1.entity.Company;
import uz.pdp.task1.entity.Department;
import uz.pdp.task1.payload.ApiResponse;
import uz.pdp.task1.payload.DepartmentDto;
import uz.pdp.task1.repository.CompanyRepository;
import uz.pdp.task1.repository.DepartmentRepository;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    CompanyRepository companyRepository;

    public ApiResponse addDepartment(DepartmentDto departmentDto) {

        Department department=new Department();
        department.setName(departmentDto.getName());

        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent())
        return new ApiResponse("Such Company not found",false);
        department.setCompany(optionalCompany.get());
        departmentRepository.save(department);
        return new ApiResponse(" Department saved",true);
    }

    public List<Department> getDepartments(){
        List<Department> departments = departmentRepository.findAll();
        return departments;
    }

    public Department getDepartmentById(Integer id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        return optionalDepartment.orElse(new Department());
    }

    public ApiResponse editDepartment(DepartmentDto departmentDto, Integer id) {

        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (!optionalDepartment.isPresent())
        return new ApiResponse("Such Department not found",false);
        Department department = optionalDepartment.get();
        department.setName(departmentDto.getName());

        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent())
            return new ApiResponse("Such Company not found",false);
        department.setCompany(optionalCompany.get());
        departmentRepository.save(department);
        return new ApiResponse(" Department edited",true);
    }

    public ApiResponse deleteDepartment(Integer id) {
        try {
            departmentRepository.deleteById(id);
            return new ApiResponse("Department ??chirildi",true);
        }catch (Exception e){
            return new ApiResponse("Department ??chirilmadi",false);
        }
    }


}
