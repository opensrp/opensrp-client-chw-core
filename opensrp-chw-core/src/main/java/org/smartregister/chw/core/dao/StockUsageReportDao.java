package org.smartregister.chw.core.dao;

import org.smartregister.chw.core.domain.StockUsage;
import org.smartregister.dao.AbstractDao;

import java.util.ArrayList;
import java.util.List;

public class StockUsageReportDao extends AbstractDao {

    public static List<StockUsage> getStockUsage() {
        String sql = "SELECT strftime('%Y',(datetime(v.visit_date/1000, 'unixepoch', 'localtime'))) as \"Year\",\n" +
                "\tstrftime('%m',(datetime(v.visit_date/1000, 'unixepoch', 'localtime'))) as \"Month\",\n" +
                "\tvd.human_readable_details as StockName,\n" +
                "\tcount(1) as Usage\n" +
                " FROM visit_details vd\n" +
                "  INNER JOIN visits v on vd.visit_id = v.visit_id\n" +
                "  WHERE  vd.human_readable_details LIKE 'ORS%' or vd.human_readable_details LIKE 'Zinc%' or vd.human_readable_details LIKE 'Panadol%'\n" +
                "  group by vd.human_readable_details, strftime('%Y',(datetime(v.visit_date/1000, 'unixepoch', 'localtime'))), strftime('%m',(datetime(v.visit_date/1000, 'unixepoch', 'localtime')))\n" +
                " UNION ALL\n" +
                " SELECT substr(fp.fp_start_date, 7, 4) as \"Year\",\n" +
                "\tsubstr(fp.fp_start_date, 4, 2) as \"Month\",\n" +
                "\tfp.fp_method_accepted as StockName,\n" +
                "\tcount(1) as Usage\n" +
                " FROM ec_family_planning fp\n" +
                "  WHERE (fp.fp_method_accepted LIKE 'COC%' or fp.fp_method_accepted LIKE 'POP%' or fp.fp_method_accepted LIKE '%condom%' \n" +
                "  or fp.fp_method_accepted like '%Standard day method%' or fp.fp_method_accepted like '%Emergency contraceptive%')\n" +
                "  AND fp.fp_start_date is not NULL\n" +
                "  group by fp.fp_method_accepted, substr(fp.fp_start_date, 7, 4), substr(fp.fp_start_date, 4, 2)\n" +
                "UNION ALL\n" +
                "SELECT \n" +
                " substr(mc.malaria_test_date, 7, 4) as Year, \n" +
                " substr(mc.malaria_test_date, 4, 2) as Month,\n" +
                " mc.malaria_treat as StockName,\n" +
                " count(1) as Usage\n" +
                "\tfrom ec_malaria_confirmation mc\n" +
                "where mc.malaria_treat in('ALU 6','ALU 12', 'ALU 18', 'ALU 24')\n" +
                "  and mc.malaria_test_date is not NULL\n" +
                "  group by mc.malaria_treat, substr(mc.malaria_test_date, 7, 4), substr(mc.malaria_test_date, 4, 2)\n" +
                " UNION ALL\n" +
                " SELECT substr(mc.malaria_test_date, 7, 4) as \"Year\",\n" +
                "        substr(mc.malaria_test_date, 4, 2) as \"Month\",\n" +
                "\t\t'RDTs' as StockName,\n" +
                "\t\tcount(1) as Usage\n" +
                "\tfrom ec_malaria_confirmation mc\n" +
                "where mc.malaria_results in('Negative','Positive')\n" +
                "and mc.malaria_test_date is not NULL\n" +
                "  group by substr(mc.malaria_test_date, 7, 4), substr(mc.malaria_test_date, 4, 2)\n" +
                "order by Year DESC, Month DESC";

        DataMap<StockUsage> dataMap = cursor -> {
            StockUsage stockUsage = new StockUsage();
            stockUsage.setYear(getCursorValue(cursor, "Year"));
            stockUsage.setMonth(getCursorValue(cursor, "Month"));
            stockUsage.setStockName(getCursorValue(cursor, "StockName"));
            stockUsage.setStockUsage(getCursorValue(cursor, "Usage"));
            return stockUsage;
        };

        List<StockUsage> res = readData(sql, dataMap);
        if (res == null)
            return new ArrayList<>();

        return res;
    }

    /**
     * Reads the min last update date and compares to today's date
     * if last update date is not in same month as today return false
     *
     * @return
     */
    public static boolean lastInteractedWithinMonth() {
        String sql = "select count(stock_name) as count\n" +
                "from stock_usage_report\n" +
                "WHERE strftime('%Y',(updated_at)) == strftime('%Y',('NOW'))\n" +
                "AND strftime('%m',(updated_at)) == strftime('%m',('NOW'))";
        DataMap<String> dataMap = cursor -> getCursorValue(cursor, "count");

        List<String> res = readData(sql, dataMap);
        return (res != null && !(res.get(0).equals("0")));
    }

    /*public  List<StockUsage> getMonthlyStockUsage(String month, String year) {
        String sql = "select * from stock_usage_report " +
                "WHERE month= '" + month + "' " +
                "AND year= '" + year + "'" +
                "GROUP BY stock_name";

        DataMap<StockUsage> dataMap = cursor -> {
            StockUsage stockUsage = new StockUsage();
            stockUsage.setStockName(getCursorValue(cursor, "stock_name"));
            stockUsage.setStockUsage(getCursorValue(cursor, "stock_usage"));
            stockUsage.setYear(getCursorValue(cursor, "year"));
            stockUsage.setMonth(getCursorValue(cursor, "month"));
            return stockUsage;
        };

        List<StockUsage> res = readData(sql, dataMap);
        if (res == null) {
            return new ArrayList<>();
        }
        return res;
    }
*/
    public  String getStockUsageForMonth(String month, String stockName, String year) {
        String sql = "select stock_usage from stock_usage_report " +
                "WHERE month= '" + month + "' " +
                "AND stock_name LIKE '%" + stockName + "%' " +
                "AND year= '" + year + "'" +
                "GROUP by stock_name";

        DataMap<StockUsage> dataMap = cursor -> {
            StockUsage stockUsage = new StockUsage();
            stockUsage.setYear(year);
            stockUsage.setMonth(month);
            stockUsage.setStockName(stockName);
            stockUsage.setStockUsage(getCursorValue(cursor, "stock_usage"));
            return stockUsage;
        };

        List<StockUsage> res = readData(sql, dataMap);
        if (res == null || res.size() == 0) {
            return " ";
        }
        return res.get(0).getStockUsage();
    }

}
