package rashjz.info.app.bakuposter.com.ws;

import java.util.List;
import javax.ejb.Stateless;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import rashjz.info.app.bakuposter.com.dao.ContentDao;
import rashjz.info.app.bakuposter.com.model.Comment;
import rashjz.info.app.bakuposter.com.model.ListItem;
import rashjz.info.app.bakuposter.com.model.SearchModel;
import rashjz.info.app.bakuposter.com.model.Vote;

/**
 * REST Web Service
 *
 * @author Rashad Javadov
 */
@Stateless
@Path("/bakufun")
public class BakufunWSResource {

    @Context
    private UriInfo context;

    @GET
    @Produces("text/html")
    public String getInfo() {
        return "<html><body><h1>Bakuposter Application WS by Rashad Javadov </h1></body></html>";
    }

    @POST
    @Path("contentList")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public List<ListItem> contentList(SearchModel model) {
        if (model == null) {
            model = new SearchModel();
        }
        List<ListItem> listItems = new ContentDao().gettingContentList(model);
        listItems.get(0).setBnote(model.toString());
        return listItems;
    }

    @POST
    @Path("getItemData")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public ListItem getItemData(ListItem item) {
//        hansiki comment list getirir ve like statusun mac  id ile mueyyen edir
        item = new ContentDao().getItemData(item);
        return item;
    }

    @POST
    @Path("insertComment")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Comment insertComment(Comment comment) {
        int item = new ContentDao().insertComment(comment.getMac_id(), comment.getContent_id(), comment.getMessage(), comment.getFromName());
        return comment;
    }

    @POST
    @Path("voteContent")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Vote voteContent(Vote vote) {
        int item = new ContentDao().insertVote(vote.getRate(), vote.getContent_id(), vote.getDeviceID(), vote.getVote_type());
        return vote;
    }

    @POST
    @Path("registerDevice")
    @Consumes({"application/json", "application/x-www-form-urlencoded"})
    @Produces({"application/json", "application/x-www-form-urlencoded"})
    public String registerDevice( @FormParam("name") String name, @FormParam("email") String email,@FormParam("regId") String regId) {
//        http://localhost:8075/PaycodeWS/wserv/checkLogin?login=rashadjavad@gmail.com&passw=12
        long dbregID = new ContentDao().registerDevice(regId, name, email);
        System.out.println(dbregID);
        return dbregID + "";
    }

    @POST
    @Path("unregisterDevice")
    @Produces(MediaType.APPLICATION_JSON)
    public void unregisterDevice(@QueryParam("regId") String regId) {
        new ContentDao().unregisterDevice(regId);
    }
}
