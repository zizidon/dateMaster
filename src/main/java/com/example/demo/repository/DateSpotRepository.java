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
        // 名前に部分一致するスポットを検索するSQLクエリ
        String sql = "SELECT * FROM date_spots WHERE spot_name LIKE ?";

        // JdbcTemplateを使ってクエリを実行し、結果をDateSpotオブジェクトに変換して返す
        return jdbcTemplate.query(sql, new Object[]{"%" + spotName + "%"}, new DateSpotRowMapper());
    }

    // 名前で完全一致するデートスポットを検索するメソッド
    public List<DateSpot> findBySpotName(String spotName) {
        // 名前で完全一致するスポットを検索するSQLクエリ
        String sql = "SELECT * FROM date_spots WHERE spot_name = ?";

        // JdbcTemplateを使ってクエリを実行し、結果をDateSpotオブジェクトに変換して返す
        return jdbcTemplate.query(sql, new Object[]{spotName}, new DateSpotRowMapper());
    }

    // すべてのスポットを取得するメソッド
    public List<DateSpot> findAll() {
        // すべてのスポットを取得するSQLクエリ
        String sql = "SELECT * FROM date_spots";

        // JdbcTemplateを使ってクエリを実行し、結果をDateSpotオブジェクトに変換して返す
        return jdbcTemplate.query(sql, new DateSpotRowMapper());
    }

    // ResultSetの行をDateSpotオブジェクトに変換するためのRowMapper
    private static class DateSpotRowMapper implements RowMapper<DateSpot> {
        @Override
        public DateSpot mapRow(ResultSet rs, int rowNum) throws SQLException {
            DateSpot spot = new DateSpot();
            spot.setSpotId(rs.getLong("spot_id"));
            spot.setSpotName(rs.getString("spot_name"));
            spot.setDescription(rs.getString("category"));
            spot.setSpotType(rs.getLong("spot_type"));
            spot.setSpotAddress(rs.getString("spot_address"));

            // 営業時間を曜日ごとに設定
            spot.setMonday(rs.getString("Monday"));
            spot.setTuesday(rs.getString("Tuesday"));
            spot.setWednesday(rs.getString("Wednesday"));
            spot.setThursday(rs.getString("Thursday"));
            spot.setFriday(rs.getString("Friday"));
            spot.setSaturday(rs.getString("Saturday"));
            spot.setSunday(rs.getString("Sunday"));

            // 緯度・経度の取得
            spot.setLatitude(rs.getDouble("latitude"));
            spot.setLongitude(rs.getDouble("longitude"));

            return spot;
        }
    }
}
