package com.vinayak.policyapi.ppolicy_api.dataTransferObjects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

public class CustomerRequestDTO {
    
    @NotBlank(message = "Customer = mandatory")
    private String name;

    @NotNull(message = "Age = mandatory")
    @Min(value = 18, message = "Customer >= 18 years old")
    @Max(value = 65, message = "Customer <= 65 years old")
    private Integer age;

    private String panNumber;

    @NotBlank(message = "NomineeName = mandatory")
    private String nomineeName;

    public String getName() { 
        return name; 
    }
    
    public void setName(String name) { 
        this.name = name; 
    }
    
    public Integer getAge() { 
        return age; 
    }
    
    public void setAge(Integer age) { 
        this.age = age; 
    }
    
    public String getPanNumber() { 
        return panNumber; 
    }
    
    public void setPanNumber(String panNumber) { 
        this.panNumber = panNumber; 
    }
    
    public String getNomineeName() { 
        return nomineeName; 
    }
    
    public void setNomineeName(String nomineeName) { 
        this.nomineeName = nomineeName; 
    }

}