package pupccb.solutionsresource.com.util;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by User on 9/3/2015.
 */
public class PrettyTime {

    public static String timeAgo(Date d, boolean... args) {

        boolean longerVersion = args.length > 0 && args[0];

        long diff = new Date().getTime() - d.getTime();

        double seconds = Math.abs(diff) / 1000;
        double minutes = seconds / 60;
        double hours = minutes / 60;
        double days = hours / 24;
        double weeks = days / 7;
        double months = weeks / 4;
        double years = months / 12;

        if (years >= 1) {
            return yearsRemaining(years, longerVersion);
        } else if (months >= 1) {
            return monthsRemaining(months, longerVersion);
        } else if (weeks >= 1) {
            return weeksRemaining(weeks, longerVersion);
        } else if (days >= 1) {
            return daysRemaining(days, longerVersion);
        } else if (hours >= 1) {
            return hoursRemaining(hours, longerVersion);
        } else if (minutes >= 1) {
            return minutesRemaining(minutes, longerVersion);
        } else {
            return secondsRemaining(seconds, longerVersion);
        }
    }

    /**
     * @param years
     * @param longerVersion
     * @return
     */
    public static String yearsRemaining(double years, boolean longerVersion) {
        int y = (int) years;
        if (longerVersion) {
            if (y == 1) {
                return "last year";
            } else {
                return Integer.toString(y) + " years ago.";
            }
        }
        return Integer.toString(y) + "y";
    }

    public static String monthsRemaining(double months, boolean longerVersion) {
        int m = (int) months;
        if (longerVersion) {
            if (m == 1) {
                return "last month.";
            } else {
                return Integer.toString(m) + " months ago.";
            }
        }
        return Integer.toString(m) + "m";
    }

    public static String weeksRemaining(double weeks, boolean longerVersion) {
        int w = (int) weeks;
        if (longerVersion) {
            if (w < 2) {
                return "last week.";
            } else {
                return Integer.toString(w) + " weeks ago.";
            }
        }
        return Integer.toString(w) + "w";
    }

    public static String daysRemaining(double days, boolean longerVersion) {
        int d = (int) days;
        if (longerVersion) {
            if (d < 2) {
                return "yesterday.";
            } else {
                return Integer.toString(d) + " days ago.";
            }
        }
        return Integer.toString(d) + "d";
    }

    public static String hoursRemaining(double hours, boolean longerVersion) {
        int h = (int) hours;
        if (longerVersion) {
            if (h < 2) {
                return "an hour ago.";
            } else {
                return Integer.toString(h) + " hours ago.";
            }
        }
        return Integer.toString(h) + "h";
    }

    public static String minutesRemaining(double minutes, boolean longerVersion) {
        int m = (int) minutes;
        if (longerVersion) {
            if (m < 2) {
                return "a minute ago";
            } else {
                return Integer.toString(m) + " minutes ago.";
            }
        }
        return Integer.toString(m) + "m";
    }

    public static String secondsRemaining(double seconds, boolean longerVersion) {
        int s = (int) seconds;
        if (longerVersion) {
            if (s <= 5) {
                return "just now";
            } else {
                return Integer.toString(s) + " seconds ago.";
            }
        }
        // The difference is the now and just now
        if (s <= 5) {
            return "now";
        }
        return Integer.toString(s) + "s";
    }

    public static final List<Long> times = Arrays.asList(
            TimeUnit.DAYS.toMillis(365),
            TimeUnit.DAYS.toMillis(30),
            TimeUnit.DAYS.toMillis(1),
            TimeUnit.HOURS.toMillis(1),
            TimeUnit.MINUTES.toMillis(1),
            TimeUnit.SECONDS.toMillis(1) );
    public static final List<String> timesString = Arrays.asList("year", "month", "day", "hour", "minute", "second");

    public static String toDuration(long duration) {

        StringBuffer res = new StringBuffer();
        for(int i=0;i< times.size(); i++) {
            Long current = times.get(i);
            long temp = duration/current;
            if(temp>0) {
                res.append(temp).append(" ").append( timesString.get(i) ).append(temp > 1 ? "s" : "").append(" ago");
                break;
            }
        }
        if("".equals(res.toString()))
            return "0 second ago";
        else
            return res.toString();
    }

}
