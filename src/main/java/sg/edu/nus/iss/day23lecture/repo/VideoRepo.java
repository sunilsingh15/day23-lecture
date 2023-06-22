package sg.edu.nus.iss.day23lecture.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.day23lecture.model.Video;

@Repository
public class VideoRepo {

    @Autowired
    JdbcTemplate template;
    
    private final String findAllVideosSql = "select * from video";
    private final String insertVideoSQL = "insert into video (title, synopsis, available_count) values (?, ?, ?)";
    private final String updateVideoSQL = "update video set title = ?, synopsis = ?, available_count = ? where id = ?";

    public List<Video> findAll() {
        return template.query(findAllVideosSql, BeanPropertyRowMapper.newInstance(Video.class));
    }

    public int updateVideo(Video video) {
        return template.update(updateVideoSQL, video.getTitle(), video.getSynopsis(), video.getAvailable_count(), video.getId());
    }

    public int insertVideo(Video video) {
        return template.update(insertVideoSQL, video.getTitle(), video.getSynopsis(), video.getAvailable_count());
    }
}
