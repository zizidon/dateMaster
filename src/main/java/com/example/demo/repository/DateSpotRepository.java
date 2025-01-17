package com.example.demo.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.DateSpot;

@Repository
public class DateSpotRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 名前に部分一致するデートスポットを検索するメソッド
    public List<DateSpot> findBySpotNameContaining(String spotName) {
        String sql = "SELECT * FROM date_spots WHERE spot_name LIKE ?";
        return jdbcTemplate.query(sql, new Object[]{"%" + spotName + "%"}, new DateSpotRowMapper());
    }

    // 名前で完全一致するデートスポットを検索するメソッド
    public List<DateSpot> findBySpotName(String spotName) {
        String sql = "SELECT * FROM date_spots WHERE spot_name = ?";
        return jdbcTemplate.query(sql, new Object[]{spotName}, new DateSpotRowMapper());
    }

    // すべてのスポットを取得するメソッド
    public List<DateSpot> findAll() {
        String sql = "SELECT * FROM date_spots";
        return jdbcTemplate.query(sql, new DateSpotRowMapper());
    }

    // 複数のスポット名を使って一度にスポット情報を取得するメソッド
    public List<DateSpot> findBySpotNames(List<String> spotNames) {
        // IN句のプレースホルダを動的に作成
        String sql = "SELECT * FROM date_spots WHERE spot_name IN (" +
                     String.join(",", spotNames.stream().map(s -> "?").toArray(String[]::new)) + ")";
        
        // クエリの引数としてスポット名リストを渡す
        return jdbcTemplate.query(sql, spotNames.toArray(), new DateSpotRowMapper());
    }

    private static class DateSpotRowMapper implements RowMapper<DateSpot> {
        @Override
        public DateSpot mapRow(ResultSet rs, int rowNum) throws SQLException {
            DateSpot spot = new DateSpot();
            spot.setSpotId(rs.getLong("spot_id"));
            spot.setSpotName(rs.getString("spot_name"));
            spot.setDescription(rs.getString("category"));
            spot.setSpotType(rs.getLong("spot_type"));
            spot.setSpotAddress(rs.getString("spot_address"));
            spot.setMonday(rs.getString("Monday"));
            spot.setTuesday(rs.getString("Tuesday"));
            spot.setWednesday(rs.getString("Wednesday"));
            spot.setThursday(rs.getString("Thursday"));
            spot.setFriday(rs.getString("Friday"));
            spot.setSaturday(rs.getString("Saturday"));
            spot.setSunday(rs.getString("Sunday"));
            spot.setLatitude(rs.getDouble("latitude"));
            spot.setLongitude(rs.getDouble("longitude"));
            return spot;
        }
    }
}
