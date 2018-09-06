package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class hrtrackTbl {

	public static void TruncatehrtrackTbl(Connection conn) throws SQLException {

		String sql = "truncate table prorep.hrtrack";
		try (PreparedStatement stmt = conn.prepareStatement(sql);) {
		stmt.execute();

		}
	}

}
