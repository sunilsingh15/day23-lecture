package sg.edu.nus.iss.day23lecture.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private Integer id;

    @NotEmpty(message = "First name is a mandatory field!")
    private String firstName;

    @NotEmpty(message = "Last name is a mandatory field!")
    private String lastName;
}
