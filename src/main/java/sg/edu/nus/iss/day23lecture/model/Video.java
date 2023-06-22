package sg.edu.nus.iss.day23lecture.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video {

    private Integer id;
    private String title;
    private String synopsis;
    private Integer available_count;
    
}
