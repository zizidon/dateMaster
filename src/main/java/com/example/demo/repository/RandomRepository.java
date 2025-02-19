package com.example.demo.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demo.entity.DateShare;
import com.example.demo.entity.DateSpot;

@Repository
public class RandomRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// セッションに表示済みスポットを保存するキー
	private static final String DISPLAYED_SPOTS_KEY = "displayedSpots";

	// 名前に部分一致するデートスポットを検索するメソッド
	public List<DateSpot> findBySpotNameContaining(String spotName) {
		String sql = "SELECT * FROM date_spots WHERE spot_name LIKE ?";
		return jdbcTemplate.query(sql, new Object[] { "%" + spotName + "%" }, new DateSpotRowMapper());
	}

	// 名前で完全一致するデートスポットを検索するメソッド
	public List<DateSpot> findBySpotName(String spotName) {
		String sql = "SELECT * FROM date_spots WHERE spot_name = ?";
		return jdbcTemplate.query(sql, new Object[] { spotName }, new DateSpotRowMapper());
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

	//目的地と天気に基づいてデートスポットを検索するメソッド
	public List<DateSpot> findByDestinationAndWeather(int destination, int weather, int planCount) {
		String sql = "SELECT * FROM date_spots WHERE category = ? AND (spot_type = ? OR spot_type = 4) "
				+ "ORDER BY RAND() LIMIT ?";
		return jdbcTemplate.query(sql, new Object[] { destination, weather, planCount }, new DateSpotRowMapper());
	}

	//指定された緯度経度の近くにあるスポットを検索するメソッド
	public List<DateSpot> findNearbySpots(double latitude, double longitude, int limit, List<Long> excludeSpotIds) {
		String sql = "SELECT * FROM date_spots " +
				"WHERE spot_id NOT IN (" +
				String.join(",", excludeSpotIds.stream().map(String::valueOf).toArray(String[]::new)) +
				") " +
				"ORDER BY (6371 * ACOS(COS(RADIANS(?)) * COS(RADIANS(latitude)) * " +
				"COS(RADIANS(longitude) - RADIANS(?)) + " +
				"SIN(RADIANS(?)) * SIN(RADIANS(latitude)))) ASC " +
				"LIMIT ?";

		return jdbcTemplate.query(sql, new Object[] { latitude, longitude, latitude, limit }, new DateSpotRowMapper());
	}

	public List<DateSpot> findByCategory(int categoryId) {
		String sql = "SELECT * FROM date_spots WHERE category = ?";
		return jdbcTemplate.query(sql, new Object[] { categoryId }, new DateSpotRowMapper());
	}

	public List<DateSpot> updateSpotAndNearbySpots(
			Long currentSpotId,
			Long selectedSpotId,
			int destinationId,
			int weatherId,
			String spotType,
			int plantCount) {

		List<DateSpot> updatedSpots = new ArrayList<>();

		if ("メインスポット".equals(spotType)) {
			// メインスポットの変更: メインスポットと必要な数のサブスポットを更新
			DateSpot mainSpot = findById(selectedSpotId);
			updatedSpots.add(mainSpot);

			int planCount = 1;
			if (planCount > 1) {
				List<DateSpot> subSpots = findNearbySpots(
						mainSpot.getLatitude(),
						mainSpot.getLongitude(),
						planCount - 1, // planCountに基づいてサブスポットの数を決定
						Collections.singletonList(mainSpot.getSpotId()));
				updatedSpots.addAll(subSpots);
			}
		} else {
			// サブスポットの変更: 他のスポットは変更しない
			DateSpot selectedSpot = findById(selectedSpotId);
			updatedSpots = getCurrentSpots(); // 既存のスポットリストを取得

			// 指定されたインデックスのスポットを置き換える
			int index = "サブスポット1".equals(spotType) ? 1 : 2;
			if (index < updatedSpots.size()) { // インデックスが有効な範囲内かチェック
				updatedSpots.set(index, selectedSpot);
			}
		}

		return updatedSpots;
	}

	private List<DateSpot> getCurrentSpots() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
		HttpSession session = attributes.getRequest().getSession();

		@SuppressWarnings("unchecked")
		List<DateSpot> currentSpots = (List<DateSpot>) session.getAttribute("currentSpots");

		if (currentSpots == null) {
			return new ArrayList<>();
		}

		return currentSpots;
	}

	public DateSpot findById(Long spotId) {
		String sql = "SELECT * FROM date_spots WHERE spot_id = ?";
		List<DateSpot> spots = jdbcTemplate.query(sql, new Object[] { spotId }, new DateSpotRowMapper());
		return spots.isEmpty() ? null : spots.get(0);
	}

	// 指定された条件に合うスポットを、既に表示されたスポット以外から取得するメソッド
	public List<DateSpot> findNewSpotsByDestinationAndWeather(int destination, int weather, List<Long> excludeSpotIds) {
		// セッションから表示済みスポットのIDリストを取得
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
		HttpSession session = attributes.getRequest().getSession();

		@SuppressWarnings("unchecked")
		List<Long> displayedSpotIds = (List<Long>) session.getAttribute(DISPLAYED_SPOTS_KEY);
		if (displayedSpotIds == null) {
			displayedSpotIds = new ArrayList<>();
		}

		// 現在の除外リストと表示済みリストを結合
		List<Long> allExcludeIds = new ArrayList<>(excludeSpotIds);
		allExcludeIds.addAll(displayedSpotIds);

		// 重複を除去
		allExcludeIds = new ArrayList<>(new HashSet<>(allExcludeIds));

		String sql;
		List<DateSpot> spots;

		if (!allExcludeIds.isEmpty()) {
			sql = "SELECT * FROM date_spots WHERE category = ? AND (spot_type = ? OR spot_type = 4) "
					+ "AND spot_id NOT IN ("
					+ String.join(",", allExcludeIds.stream().map(String::valueOf).toArray(String[]::new))
					+ ") ORDER BY RAND() LIMIT 1";
			spots = jdbcTemplate.query(sql, new Object[] { destination, weather }, new DateSpotRowMapper());
		} else {
			sql = "SELECT * FROM date_spots WHERE category = ? AND (spot_type = ? OR spot_type = 4) "
					+ "ORDER BY RAND() LIMIT 1";
			spots = jdbcTemplate.query(sql, new Object[] { destination, weather }, new DateSpotRowMapper());
		}

		// 新しいスポットが見つからない場合は、表示済みリストをリセット
		if (spots.isEmpty()) {
			displayedSpotIds.clear();
			sql = "SELECT * FROM date_spots WHERE category = ? AND (spot_type = ? OR spot_type = 4) "
					+ "ORDER BY RAND() LIMIT 1";
			spots = jdbcTemplate.query(sql, new Object[] { destination, weather }, new DateSpotRowMapper());
		}

		// 見つかったスポットを表示済みリストに追加
		if (!spots.isEmpty()) {
			displayedSpotIds.add(spots.get(0).getSpotId());
			session.setAttribute(DISPLAYED_SPOTS_KEY, displayedSpotIds);
		}

		return spots;
	}

	//DBからユーザーの情報を取得
	public Long findPartnerIdByUserId(Long userId) {
		String sql = "SELECT partner FROM users WHERE id = ?";
		try {
			return jdbcTemplate.queryForObject(sql, Long.class, userId);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public void saveDateShare(DateShare dateShare, Long userId, Long partnerId) {
		String sql = "INSERT INTO random_share (spot1, spot2, spot3, count, user_id, partner_id) " +
				"VALUES (?, ?, ?, ?, ?, ?)";

		jdbcTemplate.update(sql,
				dateShare.getSpot1(),
				dateShare.getSpot2(),
				dateShare.getSpot3(),
				dateShare.getCount(),
				userId,
				partnerId);

	}

	public void updateRandomRanking(List<DateSpot> spots) {
		// スポットIDをソートして一意のパターンを作成
		List<Long> sortedSpotIds = spots.stream()
				.map(DateSpot::getSpotId)
				.sorted()
				.collect(Collectors.toList());

		String spotPattern = String.join("-", sortedSpotIds.stream()
				.map(String::valueOf)
				.collect(Collectors.toList()));

		// ランキングテーブルの更新
		String checkSql = "SELECT count FROM random_ranking WHERE spot_pattern = ?";
		String updateSql = "UPDATE random_ranking SET count = count + 1 WHERE spot_pattern = ?";
		String insertSql = "INSERT INTO random_ranking (spot_pattern, spot1, spot2, spot3, count) VALUES (?, ?, ?, ?, 1)";

		try {
			// 既存パターンの確認
			Integer currentCount = jdbcTemplate.queryForObject(checkSql, Integer.class, spotPattern);

			if (currentCount != null) {
				// 既存パターンの場合、カウントを増やす
				jdbcTemplate.update(updateSql, spotPattern);
			} else {
				// 新規パターンの場合、新しく登録
				Long spot1 = spots.size() > 0 ? spots.get(0).getSpotId() : null;
				Long spot2 = spots.size() > 1 ? spots.get(1).getSpotId() : null;
				Long spot3 = spots.size() > 2 ? spots.get(2).getSpotId() : null;

				jdbcTemplate.update(insertSql, spotPattern, spot1, spot2, spot3);
			}
		} catch (EmptyResultDataAccessException e) {
			// 新規パターンの場合、新しく登録
			Long spot1 = spots.size() > 0 ? spots.get(0).getSpotId() : null;
			Long spot2 = spots.size() > 1 ? spots.get(1).getSpotId() : null;
			Long spot3 = spots.size() > 2 ? spots.get(2).getSpotId() : null;

			jdbcTemplate.update(insertSql, spotPattern, spot1, spot2, spot3);
		}
	}

}