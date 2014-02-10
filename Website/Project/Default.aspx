<%@ Page Language="C#" EnableEventValidation="false" ValidateRequest="false" %>
<%@ Import Namespace="edu.neu.ccis.bhavik.projectComment" %>
<%@ Import Namespace="System" %>
<%@ Import Namespace="System.Collections.Generic" %>

<!DOCTYPE html>
<script runat="server">
  
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            Session["update"] = Server.UrlEncode(System.DateTime.Now.ToString());

            projectCommentDataContext context = new projectCommentDataContext();
            projectComment newcomment = new projectComment();
            var commentTable = (from tabl in context.projectComment select tabl).OrderByDescending(tabl => tabl.commentID);

            name.Text = "";
            comment.Text = "";


            commentSpace.InnerHtml = "";

            StringBuilder builder = new StringBuilder();

            foreach (var c in commentTable)
            {
                var str = c.comments;
                var length = str.Length - 2;
                str = str.Substring(1, length);

                builder.Append("<div style='margin:1%; border: 5px solid #e3e3e3; border-radius: 8px;'><div style='font-size: 14px; background-color: #f1f1f1; border-bottom: 1px solid #e3e3e3; padding: 5px;  font-weight:bold;'><i>");
                builder.Append("<b>" + c.username + "</b>");
                builder.Append(" posted on ");
                builder.Append(c.date);
                builder.Append(" at ");
                builder.Append(c.time);
                builder.Append("</div><div style='text-align: justify; padding:1%; background-color:White;'>");
                builder.Append(str);
                builder.Append("</div></div>");
            }

        }

        if (Page.User.Identity.IsAuthenticated)
        {

            name.Enabled = true;
            comment.Enabled = true;
            post.Enabled = true;
            commentMessage.Text = "";
            commentRequired.Style["Display"] = "Static";

        }
    }


    protected void post_Click(object sender, EventArgs e)
    {


        if (Session["update"].ToString() == ViewState["update"].ToString())
        {

            if (name.Text != null && comment.Text != null && comment.Text != "")
            {

                if (name.Text == null || name.Text == "")
                {
                    name.Text = "unknown";
                }

                projectCommentDataContext context = new projectCommentDataContext();
                projectComment newcomment = new projectComment();

                var date = System.DateTime.Now.ToLongDateString();

                newcomment.username = name.Text;

                newcomment.comments = "\"" + Server.HtmlEncode(comment.Text) + "\"";

                newcomment.date = System.DateTime.Now.ToLongDateString();
                newcomment.time = System.DateTime.Now.ToShortTimeString();

                context.projectComment.InsertOnSubmit(newcomment);

                foreach (var control in this.Controls)
                {
                    var textbox = name as TextBox;
                    var commentbox = comment as TextBox;

                    if (textbox != null)
                    {
                        textbox.Text = "name";
                        comment.Text = "Comment";
                    }
                }

                context.SubmitChanges();
                //Response.Redirect(Request.Url.PathAndQuery, true);
            }


            Session["update"] = Server.UrlEncode(System.DateTime.Now.ToString());

        }

        name.Text = "";
        comment.Text = "";


    }

    protected override void OnPreRender(EventArgs e)
    {
        base.OnPreRender(e);
        ViewState["update"] = Session["update"];

        projectCommentDataContext context1 = new projectCommentDataContext();
        projectComment newcomment1 = new projectComment();
        var commentTable = (from tabl in context1.projectComment select tabl).OrderByDescending(tabl => tabl.commentID);


        commentSpace.InnerHtml = "";


        StringBuilder builder = new StringBuilder();

        foreach (var c in commentTable)
        {
            var str = c.comments;
            var length = str.Length - 2;
            str = str.Substring(1, length);

            builder.Append("<div style='margin:1%; border: 5px solid #e3e3e3; border-radius: 8px;'><div style='font-size: 14px; background-color: #f1f1f1; border-bottom: 1px solid #e3e3e3; padding: 5px;  font-weight:bold;'><i>");
            builder.Append("<b>" + c.username + "</b>");
            builder.Append(" posted on ");
            builder.Append(c.date);
            builder.Append(" at ");
            builder.Append(c.time);
            builder.Append("</div><div style='text-align: justify; padding:1%; background-color:White;'>");
            builder.Append(str);
            builder.Append("</div></div>");
        }


        commentSpace.InnerHtml = builder.ToString();


        name.Text = "";
        comment.Text = "";
    }
    
</script>
<html>
<head>
    <title>PlaceSearch</title>
    <link rel="shortcut icon" href="_images/logo_noback.ico" type="image/x-icon" />
    <link href="CSS/index.css" rel="stylesheet" media="screen, projection" />
    <link href="CSS/style_media.css" rel="stylesheet" type="text/css" />
    <link href="CSS/selectbox.css" rel="stylesheet" type="text/css" />
    <script src="JS/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="JS/jquery.selectbox-0.2.js" type="text/javascript"></script>
    <script src="JS/topmenu.js" type="text/javascript"></script>
    <script src="JS/infoholder.js" type="text/javascript"></script>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.4.4.js"></script>
    <script src="fancybox/jquery.mousewheel-3.0.4.pack.js" type="text/javascript"></script>
    <script src="fancybox/jquery.fancybox-1.3.4.pack.js" type="text/javascript"></script>
    <link href="fancybox/jquery.fancybox-1.3.4.css" rel="stylesheet" type="text/css" />
    <script src="JS/photos.js" type="text/javascript"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js" type="text/javascript"></script>
    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&libraries=places,geometry"
        type="text/javascript"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js" type="text/javascript"></script>
    <script src="JS/mapsListner.js" type="text/javascript"></script>
    <script src="JS/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="JS/jQuery.fitmaps.js" type="text/javascript"></script>
    <script src="JS/fitmaps.js" type="text/javascript"></script>
    <style type="text/css">
        #maps_display
        {
            max-width: 60%;
            width: auto;
            float: left;
            padding: 0.5%;
            height: inherit;
        }
        
        #map_heading
        {
            font-family: Lucida Calligraphy;
            font-size: 30px;
            color: rgb(164, 0, 4);
            padding-left: 10px;
        }
        
        .comment-box-content
        {
            text-align: justify;
            padding: 1%;
            max-height: 345px;
            overflow: auto;
            background-color: White;
        }
        
        .style5
        {
            width: 200px;
        }
        .style6
        {
            font-size: medium;
        }
    </style>
</head>
<body class="pad">
    <form id="form1" runat="server">
    <div>
        <ul class="master_navigation">
            <li><a href="#">
                <img id="logo" src="_images/logo_noback.png" />
            </a></li>
            <li><a href="#">Home</a></li>
            <li><a href="../story/index.htm?../Project/Documentation/story.txt">Documentation</a></li>
            <li>
                <select class="select_typ" name="foodStuff" id="foodStuff" tabindex="1" onchange="foodstuff_fn()">
                    <option class="no-display" value="" disabled selected>Hungry?</option>
                    <optgroup label="Food">
                        <option value="food">Get Food</option>
                        <option value="bakery">Find Bakery</option>
                        <option value="cafe">Cafe</option>
                        <option value="restaurants">Restaurants</option>
                    </optgroup>
                    <optgroup label="Drinks">
                        <option value="bar">Bar</option>
                        <option value="liquor">Liquor Store</option>
                    </optgroup>
                    <option value="store">Other Stores</option>
                </select>
            </li>
            <li>
                <select class="select_typ" name="liesure" id="liesure" tabindex="2" onchange="liesure_fn()">
                    <option class="no-display" value="" disabled selected>Bored?</option>
                    <optgroup label="Wanna Party?">
                        <option value="casino">Find Casinos</option>
                        <option value="night_club">Go Clubbing</option>
                    </optgroup>
                    <optgroup label="Visit Places">
                        <option value="aquarium">Aquarium</option>
                        <option value="art_gallery">Artistic</option>
                        <option value="movie_theater">Cinemas</option>
                        <option value="museum">Museums</option>
                        <option value="amusement_park">Picnic</option>
                        <option value="park">Park</option>
                        <option value="shopping_mall">Go Shopping</option>
                    </optgroup>
                    <optgroup label="Religious">
                        <option value="church">Church</option>
                        <option value="hindu_temple">Temple</option>
                    </optgroup>
                </select>
            </li>
            <li>
                <select class="select_typ" name="other" id="other" tabindex="1" onchange="other_fn()">
                    <option class="no-display" value="" disabled selected>More places</option>
                    <option value="airport">Airport</option>
                    <option value="atm">Need Money?</option>
                    <option value="bank">Bank</option>
                    <option value="gas_station">Gas Station</option>
                    <option value="health">Health care</option>
                    <option value="library">Library</option>
                    <option value="subway_station">Subway Station</option>
                    <option value="establishment">Other Stores</option>
                </select>
            </li>
            <li id="login">
                <asp:LoginStatus ID="LoginStatus2" runat="server" LoginText="Sign In"></asp:LoginStatus>
            </li>
        </ul>
    </div>
    <%--~~~~END OF HEADER~~~~--%>

<%--left hand side of page having selection checkboxes and place info--%>
    <div class="clear-both">
    </div>
    <div id="mid_contents">
        <div id="inputfield">
            <div>
                <input id="searchTextField" type="text">
                <asp:Button ID="find" runat="server" CssClass="buttonStlye" Text="Search" OnClientClick="initialize()" />
                <span class="float_left">
                    <asp:CheckBox ID="AutoFillCheck" runat="server" OnClick="initialize()" Text="AutoComplete" /></span>
                <span class="float_right">
                    <asp:CheckBox ID="nearbySearch" runat="server" Text="Search Nearby" OnClick="initialize()"
                        CssClass="nearbysearch" /></span>
            </div>

<%--place list and directions holder--%>

            <div id="tabs">
                <ul>
                    <li id="li_tab1" onclick="tab('tab1')"><a>Place List</a></li>
                    <li id="li_tab2" onclick="tab('tab2')"><a>Directions</a></li>
                </ul>
                <div id="Content_Area">
                    <div id="tab1">
                    </div>
                    <div id="tab2" class="no-display">
                        <p>
                            No Directions to Display</p>
                    </div>
                </div>
                <button class="float_left" id="more">
                    More Similar Places
                </button>
                <a id="moreInfo" onclick="sendData()" target="_blank" class="float_right no-display">
                    More info. on this place</a></div>
        </div>

     <%--   maps division--%>

        <div id="maps_display">
            <asp:ScriptManager ID="SM1" runat="server">
            </asp:ScriptManager>
            <asp:UpdatePanel ID="map_update" runat="server">
                <ContentTemplate>
                    <span id="map_heading"><em><strong>Find your favourite spot near you!</strong></em></span><br />
                    <span class="style3"><a name="PAGETOP">Simply, find the places which suits your mood
                        right now</a></span>
                    <div id="map-canvas">
                    </div>
                </ContentTemplate>
                <Triggers>
                    <asp:AsyncPostBackTrigger ControlID="nearbySearch" EventName="CheckedChanged" />
                    <asp:AsyncPostBackTrigger ControlID="AutoFillCheck" EventName="CheckedChanged" />
                    <asp:AsyncPostBackTrigger ControlID="find" EventName="Click" />
                </Triggers>
            </asp:UpdatePanel>
        </div>
        <div class="clear-both">
        </div>
    </div>

    <%--photos div--%>
    <div class="rounded">
        <div id="photosName">
        </div>
        <div class="container wrapper">
            <div id="photos">
            </div>
        </div>
    </div>

    <%--comment and reviews--%>
    <div id="commentDivision">

    <%--site comments--%>

        <asp:UpdatePanel ID="commentUpdate" runat="server">
            <ContentTemplate>
                <div class="comment-box-block">
                    <div class="comment-box-header">
                        <i><strong>Site User reviews about places</strong></i></div>
                    <div id="commentSpace" runat="server">
                    </div>
                    <div id="UserComment">
                        <table class="style1">
                            <tr>
                                <td>
                                    <span class="style6">Name:</span>
                                </td>
                                <td>
                                    <asp:TextBox ID="name" runat="server" Width="140px" CssClass="style5" Enabled="false"></asp:TextBox>
                                    <asp:RegularExpressionValidator ID="reg1" runat="server" ControlToValidate="name"
                                        ValidationExpression="^[a-zA-Z0-9 ]+$" ErrorMessage="*Enter valid name"></asp:RegularExpressionValidator>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <span class="style6">Comment:</span>
                                </td>
                                <td>
                                    <span>
                                        <asp:TextBox ID="comment" runat="server" EnableTheming="False" TextMode="MultiLine"
                                            CssClass="comment-box" Enabled="false" Text="Comment Here (You must sign in before commenting)"></asp:TextBox>
                                        <span>
                                            <asp:RequiredFieldValidator ID="commentRequired" runat="server" ControlToValidate="comment"
                                                ErrorMessage="*Required" Display="None"></asp:RequiredFieldValidator></span> </span>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <span>
                                        <asp:Button ID="post" runat="server" OnClick="post_Click" Text="Post Review" Width="120px"
                                            CssClass="style5" Enabled="false" />
                                        <asp:Label ID="commentMessage" runat="server" Text="Login to post reviews"></asp:Label>
                                    </span>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </ContentTemplate>
            <Triggers>
                <asp:AsyncPostBackTrigger ControlID="post" EventName="Click" />
            </Triggers>
        </asp:UpdatePanel>

       <%-- google user reviews--%>

        <div id="reviews_box" class="comment-box-block">
            <div id="reviewsPlaceName" class="comment-box-header">
                Reviews of places you searched for</div>
            <div id="reviews" class="comment-box-content">
                No reviews found for the search.</div>
        </div>
        <br />
    </div>
    <div class="clear-both">
    </div>

<%--footer--%>
    <footer id="pageFooter">
      <nav class="footerNav">
            <p>Find Places | &copy;Copyright 2013 | Bhavik Gandhi | All rights reserved</p> 
    </footer>
    </form>
</body>
</html>
