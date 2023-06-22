package sg.edu.nus.iss.day23lecture.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.day23lecture.model.Loan;

@Repository
public class LoanRepo {

    @Autowired
    JdbcTemplate template;

    private final String selectSQL = "select * from loan";

    private final String insertSQL = "insert into loan values (null, ?, ?, ?)";

    public List<Loan> findAllLoans() {
        return template.query(selectSQL, BeanPropertyRowMapper.newInstance(Loan.class));
    }

    public Integer createLoan(Loan loan) {
        KeyHolder generatedKey = new GeneratedKeyHolder();

        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(insertSQL, new String[] { "id" });
                ps.setInt(1, loan.getCustomerID());
                ps.setDate(2, loan.getLoanDate());
                ps.setDate(3, loan.getReturnDate());

                return ps;
            }

        };

        template.update(psc, generatedKey);
        return generatedKey.getKey().intValue();
    }

}
