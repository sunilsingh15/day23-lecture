package sg.edu.nus.iss.day23lecture.model;

import java.sql.Date;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    private Integer id;
    private Integer customerID;

    @FutureOrPresent(message = "Borrow date must be a current or future date!")
    private Date loanDate;

    @Future(message = "Return date must be a future date!")
    private Date returnDate;

}
