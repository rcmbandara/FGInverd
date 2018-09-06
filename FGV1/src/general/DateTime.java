package general;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTime {

	public static void main(String[] args) {

		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		// cal.add(Calendar.HOUR, -1);
		// cal.set(Calendar.HOUR_OF_DAY, 5);

		int y = cal.get(cal.HOUR_OF_DAY);
		int w = 0;

		if (y == 0) {
			w = 16;
		} else if (8 > y && y > 0) {
			w = (24 - y);

		} else if (24 > y && y > 7) {
			w = y - 7;
		}

		System.out.println(y + "");

		date = cal.getTime();

		System.out.println(date.toString());
		for (int i = 1; i <= w; i++) {
			cal.add(Calendar.HOUR, -1);
			date = cal.getTime();

			// DateFormat readFormat = new SimpleDateFormat( "MMM dd,
			// yyyyhh:mm:ss aa");
			DateFormat readFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
			String formattedDate = readFormat.format(date);

			System.out.println(formattedDate);
		}

	}

}
