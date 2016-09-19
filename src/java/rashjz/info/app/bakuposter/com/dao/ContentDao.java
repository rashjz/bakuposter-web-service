package rashjz.info.app.bakuposter.com.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import rashjz.info.app.bakuposter.com.model.Comment;
import rashjz.info.app.bakuposter.com.model.ListItem;
import rashjz.info.app.bakuposter.com.model.SearchModel;
import rashjz.info.app.bakuposter.com.util.ConvertUtil;
import rashjz.info.app.bakuposter.com.util.DataBaseHelper;
import rashjz.info.app.bakuposter.com.util.DatabaseUtil;

/**
 *
 * @author rasha_000
 */
public class ContentDao {

    public List<ListItem> gettingContentList(SearchModel model) {
        Connection c = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<ListItem> listItems = new ArrayList<ListItem>();
        try {
            c = DataBaseHelper.connect();
//            c = DatabaseUtil.connect();
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT C.REC_ID,\n"
                    + "       C.TITLE,\n"
                    + "       C.DESCRIPTION,\n"
                    + "       C.RELEASE_YEAR,\n"
                    + "       C.RATING,\n"
                    + "       C.END_DATE,\n"
                    + "       C.VIDEO_URL,\n"
                    + "       C.IMG_URL,\n"
                    + "       C.GENRE,\n"
                    + "       L.TITLE AS loc_name,\n"
                    + "       L.LATITUDE,\n"
                    + "       L.LONGITUDE,\n"
                    + "       C.PRICE,\n"
                    + "       C.AGE_ALLOW\n"
                    + "  FROM bakuposter.CONTENT C \n"
                    + "       LEFT JOIN bakuposter.LOCATION L  ON C.LOCATION_ID = L.REC_ID\n"
                    + " WHERE   C.STATUS = 1 AND C.END_DATE > SYSDATE() ");
            if (model.getType_id() != null && model.getType_id().compareTo(BigDecimal.ZERO) != 0) {
                sql.append("AND C.type_id = " + model.getType_id());
            }
            if (model.getTitle() != null && !model.getTitle().equals("")) {
                sql.append("AND C.TITLE like '" + model.getTitle() + "%'");
            }
            if (model.getPrice() != 0) {
                sql.append("AND C.PRICE =" + model.getPrice());
            }
            if (model.getFrom_date() != null) {
                sql.append(" AND C.END_DATE >= STR_TO_DATE('" + ConvertUtil.dateToString(model.getFrom_date(), "MM/dd/YYYY") + "', '%m/%d/%Y')");
            }
            if (model.getTo_date() != null) {
                sql.append(" AND C.END_DATE <= STR_TO_DATE('" + ConvertUtil.dateToString(model.getTo_date(), "MM/dd/YYYY") + "', '%m/%d/%Y')");
            }
//            if (model.getTo_date() != null) {
//                sql.append(" AND C.END_DATE <= to_date('" + ConvertUtil.dateToString(model.getTo_date(), "MM/dd/YYYY") + "', 'MM/DD/YYYY')");
//            }
            sql.append(" order by C.INS_DATE desc ");
            System.out.println(sql.toString());
            pstm = c.prepareStatement(sql.toString());
            rs = pstm.executeQuery();
            while (rs.next()) {
                ListItem item = new ListItem();
                item.setRec_id(rs.getBigDecimal(1));
                item.setTitle(rs.getString(2));
                item.setDescription(rs.getString(3));
                item.setRelease_year(rs.getInt(4));
                item.setRating(rs.getFloat(5));
                item.setEnd_date(rs.getTimestamp(6));
                item.setVideo_url(rs.getString(7));
                item.setImg_url(rs.getString(8));
                item.setGenre(rs.getString(9));
                item.getLocation().setTitle(rs.getString(10));
                item.getLocation().setLatitude(rs.getBigDecimal(11));
                item.getLocation().setLongitude(rs.getBigDecimal(12));
                item.setPrice(rs.getInt(13));
                item.setAge_allow(rs.getInt(14));
                listItems.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.close(rs, pstm, c);
        }
        return listItems;
    }

    public int insertVote(float rate, BigDecimal content_id, String mac_id, int vote_type) {
        Connection c = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int status = 0;
        try {
            c = DataBaseHelper.connect();
//            c = DatabaseUtil.connect();
            StringBuffer sql = new StringBuffer();
            sql.append("INSERT INTO bakuposter.VOTE_CONTENT (`VALUE`, `CONTENT_ID`, `MAC_ID`, `VOTE_TYPE`) "
                    + " VALUES (" + rate + "," + content_id + ",'" + mac_id + "'," + vote_type + ")");
            System.out.println(sql.toString());
            pstm = c.prepareStatement(sql.toString());
            status = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.close(rs, pstm, c);
        }
        return status;
    }

    public int insertComment(String mac_id, BigDecimal content_id, String comment, String email) {
        Connection c = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int status = 0;
        try {
            c = DataBaseHelper.connect();
//            c = DatabaseUtil.connect();
            StringBuffer sql = new StringBuffer();
            sql.append("INSERT INTO bakuposter.CONTENT_COMMENTS (`DEVICE_MAC_ID`, `CONTENT_ID`, `USER_COMMENT`, `STATUS`, `USER_EMAIL`) \n"
                    + "     VALUES (\n"
                    + "             '" + mac_id + "',\n"
                    + "             " + content_id + ",\n"
                    + "             '" + comment + "',\n"
                    + "             'a',"
                    + "             '" + email + "'\n"
                    + ")");
            System.out.println(sql.toString());
            pstm = c.prepareStatement(sql.toString());
            status = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.close(rs, pstm, c);
        }
        return status;
    }

    public ListItem getItemData(ListItem item) {
        Connection c = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Comment> comments = new ArrayList<Comment>();
        try {
            c = DataBaseHelper.connect();
//            c = DatabaseUtil.connect();
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT cm.USER_COMMENT  , cm.USER_EMAIL \n"
                    + "  FROM bakuposter.CONTENT_COMMENTS cm\n"
                    + " WHERE cm.CONTENT_ID = " + item.getRec_id());
            System.out.println(sql.toString());
            pstm = c.prepareStatement(sql.toString());
            rs = pstm.executeQuery();
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setMessage(rs.getString(1));
                comment.setFromName(rs.getString(2));
                comments.add(comment);
            }

            pstm = c.prepareStatement("SELECT VALUE\n"
                    + "  FROM bakuposter.VOTE_CONTENT vt\n"
                    + " WHERE vt.VOTE_TYPE = 2 AND vt.MAC_ID = '" + item.getBnote() + "' AND vt.CONTENT_ID = " + item.getRec_id());
            rs = pstm.executeQuery();
            while (rs.next()) {
                item.setLike_status((int) rs.getFloat(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.close(rs, pstm, c);
        }
        item.setComments(comments);
        return item;
    }

    public long registerDevice(String regId, String name, String email) {
        Connection c = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        long regID = 0;
        try {
            c = DataBaseHelper.connect();
//            c = DatabaseUtil.connect();
            StringBuffer sql = new StringBuffer();
            sql.append("INSERT INTO bakuposter.GCM_USERS (GCM_REGID, `NAME`, EMAIL, CREATED_AT, STATUS) \n"
                    + "     VALUES (?,\n"
                    + "             ?,\n"
                    + "             ?,\n"
                    + "             NOW(),"
                    + "             'a')");
            System.out.println(sql.toString());
            pstm = c.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, regId);
            pstm.setString(2, name);
            pstm.setString(3, email);

            pstm.executeUpdate();
            rs = pstm.getGeneratedKeys();
            if (rs != null && rs.next()) {
                regID = rs.getLong(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.close(rs, pstm, c);
        }
        return regID;
    }

    public void unregisterDevice(String regId) {
        Connection c = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            c = DataBaseHelper.connect();
//            c = DatabaseUtil.connect();
            StringBuffer sql = new StringBuffer();
            sql.append("UPDATE RASHAD.GCM_USERS gcm\n"
                    + "   SET status = 'd'\n"
                    + " WHERE GCM.GCM_REGID = ?");
            System.out.println(sql.toString());
            pstm = c.prepareStatement(sql.toString());
            pstm.setString(1, regId);
            pstm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.close(rs, pstm, c);
        }
    }

    public static void main(String[] args) {
//        SearchModel model = new SearchModel();
//        model.setFrom_date(new Date(29, 12, 12));
//        model.setTo_date(new Date(2015, 12, 12));
//        model.setType_id(BigDecimal.ONE);
//        model.setTitle("");
//        model.setPrice(0);
//        List<ListItem> items = new ContentDao().gettingContentList(model);
//        System.out.println(items.size());

//        float f = 1f;
//        new ContentDao().insertVote(f, BigDecimal.ZERO, "wer34s-fdg-fgh-fgfh", 2);
//        new ContentDao().insertComment("as-34-as-fg-3-sd", BigDecimal.ONE, " maraqlidi isdir", "email@email.com");
//        
        ListItem item = new ListItem();
        item.setBnote("wer34s-fdg-fgh-fgfh");
        item.setRec_id(new BigDecimal(1));
        System.out.println(new ContentDao().getItemData(item).getComments().size() + " " + item.getLike_status());

//        new ContentDao().registerDevice("rashjz", "Rashad", "jz");
//        new ContentDao().unregisterDevice("rashjz");
    }
}
