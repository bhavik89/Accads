<%@Page Language="C#"%>
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

           <%-- <div class='comment-block'><div class='comment-header'><i>" + place.reviews[i].author_name + "</div><div class='comment-content'>" + place.reviews[i].text + "</div></div>--%>


            commentSpace.InnerHtml = "";

            foreach (var c in commentTable)
            {
                commentSpace.InnerHtml += "<div style='margin:1%; border: 5px solid #e3e3e3; border-radius: 8px;'><div style='font-size: 14px; background-color: #f1f1f1; border-bottom: 1px solid #e3e3e3; padding: 5px;  font-weight:bold;'><i>";
                commentSpace.InnerHtml += "<b>" + c.username + "</b>";
                commentSpace.InnerHtml += " posted on ";
                commentSpace.InnerHtml += c.date;
                commentSpace.InnerHtml += " at ";
                commentSpace.InnerHtml += c.time;
                commentSpace.InnerHtml += "</div>";
                commentSpace.InnerHtml += "<div style='text-align: justify; padding:1%; background-color:White;'>";
                commentSpace.InnerHtml += c.comments;
                commentSpace.InnerHtml += "</div></div>";
             }

        }

        if (Page.User.Identity.IsAuthenticated)
        {

            name.Enabled = true;
            comment.Enabled = true;
            post.Enabled = true;

        }
    }


    protected void post_Click(object sender, EventArgs e)
    {
        //  if (Session["update"].ToString() == ViewState["update"].ToString())
        //{
        
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
                newcomment.comments = comment.Text;
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
            //ClientScript.RegisterStartupScript(GetType(), "hwa", "alert('Hello World');", true);
        }
            

        else
        {
            //ClientScript.RegisterStartupScript(GetType(), "hwa", "alert('Hello World');", true);
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

        foreach (var c in commentTable)
        {
            commentSpace.InnerHtml += "<div style='margin:1%; border: 5px solid #e3e3e3; border-radius: 8px;'><div style='font-size: 14px; background-color: #f1f1f1; border-bottom: 1px solid #e3e3e3; padding: 5px;  font-weight:bold;'><i>";
                commentSpace.InnerHtml += "<b>" + c.username + "</b>";
                commentSpace.InnerHtml += " posted on ";
                commentSpace.InnerHtml += c.date;
                commentSpace.InnerHtml += " at ";
                commentSpace.InnerHtml += c.time;
                commentSpace.InnerHtml += "</div>";
                commentSpace.InnerHtml += "<div style='text-align: justify; padding:1%; background-color:White;'>";
                commentSpace.InnerHtml += c.comments;
                commentSpace.InnerHtml += "</div></div>";
        }

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
    <script type="text/javascript">
        var selbox = jQuery.noConflict();
        selbox(document).ready(function () {
            selbox(function () {
                selbox(".select_typ").selectbox();
            });

            selbox(document).keypress(function (e) {
                var event = e || window.event;
                var key = event.charCode || event.keyCode
                if (key == 13 || key == 10) {
                    if (document.getElementById("searchTextField").value)
                        initialize();
                }
            });
        });
    </script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js" type="text/javascript"></script>
    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&libraries=places,geometry" type="text/javascript"></script>
    <style type="text/css">
        
        .style4
        {
            width: 256px;
        }
        .style5
        {
            width: 257px;
        }
        .style6
        {
            width: 319px;
        }
        .style7
        {
            width: 240px;
        }
        
    </style>
    <script type="text/javascript">
        function tab(tab) {
            document.getElementById('tab1').style.display = 'none';
            document.getElementById('tab2').style.display = 'none';
            document.getElementById('li_tab1').setAttribute("class", "");
            document.getElementById('li_tab2').setAttribute("class", "");
            document.getElementById(tab).style.display = 'block';
            document.getElementById('li_' + tab).setAttribute("class", "active");
        }
    </script>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.4.4.js"></script>
    <script src="fancybox/jquery.mousewheel-3.0.4.pack.js" type="text/javascript"></script>
    <script src="fancybox/jquery.fancybox-1.3.4.pack.js" type="text/javascript"></script>
    <link href="fancybox/jquery.fancybox-1.3.4.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">

        var fancy = jQuery.noConflict();

        fancy(document).ready(function () {
            fancy(".fancybox").fancybox({
                openEffect: 'fade',
                closeEffect: 'elastic',
                scrolling: 'yes',
                helpers: {
                    media: true
                }
            });
        });
        
    </script>
  <script src="JS/mapsListner.js" type="text/javascript"></script>
 <script src="JS/jquery-1.7.2.min.js" type="text/javascript"></script>
      <%-- <script src="JS/jQuery.fitmaps.js" type="text/javascript"></script>
    <script type="text/javascript">
        var mapfit = jQuery.noConflict();

        mapfit(document).ready(function () {
        mapfit("#map-canvas").fitMaps({ w: '75%', h: '50%' });
        });   
    </script>--%>

    <style type="text/css">
    #maps_display
 {
     background-image: url('_images/back.jpg');
      max-width:65%; 
      width:auto; 
      float:left; 
      padding:0.5%; 
      height:inherit;     
     }
     
     #map_heading
     {
         font-family:Lucida Calligraphy; 
         font-size: 30px; 
         color: rgb(164, 0, 4); 
         padding-left: 20px;
         }
    </style>

</head>
<body class="pad">
    <form id="form1" runat="server">
    <%--<div style="margin-top:2%;">
    <header class="pageHeader pos_rel">

    <nav id="pageNav">
    <ul>
    <li>
    <a href="maps.aspx">
    <img src="_images/logo_noback.png" style="position:absolute; z-index:100; margin-top:-45px; margin-left:-2%" />
    </a>
    </li>
    <li style="padding-left:8%;"><a href="#">Home</a></li>
    <li><a href="#">Documentation</a></li>
      <li>
         <select  class = "select_typ" name="foodStuff" id="foodStuff" tabindex="1" onchange="foodstuff_fn()">
			<option value="" >Hungry?</option>
			<optgroup label="Food">
				<option value="food">Food</option>
                <option value="bakery">Bakery</option>
				<option value="cafe">Cafe</option>
                <option value="restaurants">Restaurants</option>
            </optgroup>
			<optgroup label="Drinks">
				<option value="bar">Bar</option>
				<option value="liquor">Liquor Store</option>
			</optgroup>
			<option value="establishment">Other Store</option>
			<option value="store">Other</option>
		</select>
           </li>

           <li>
         <select class = "select_typ" name="liesure" id="liesure" tabindex="2" onchange="liesure_fn()">
			<option value="">Bored?</option>
			<optgroup label="Wanna Party?">
				<option value="casino">Casino</option>
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
           <li >
         <select class = "select_typ" name="other" id="other" tabindex="1" onchange="other_fn()">
			<option value="">More places</option>
			    <option value="airport">Airport</option>
                <option value="atm">Need Money?</option>
		        <option value="bank">bank</option>
                <option value="gas_station">Gas Station</option>
                <option value="health">Health care</option>
                <option value="library">Library</option>
                <option value="subway_station">Subway Station</option>          
		</select>
           </li>
           <li>
           
        <asp:LoginView ID="LoginView0" Runat="server">
        <LoggedInTemplate>
           
        <asp:LoginName
            ID="Login1"
            Runat="server"
            FormatString="Welcome <span class='red'>{0}</span>&nbsp&nbsp&nbsp&nbsp" />
        </LoggedInTemplate>
        </asp:LoginView>
           
        <asp:LoginStatus ID="LoginStatus2" runat="server" LoginText="Sign In">     
        </asp:LoginStatus>
        </li>      
        </ul>
  </nav>
</header>
</div>--%>

<div>
    <ul class = "master_navigation">
    <li>
    <a href="maps.aspx">
    <%--<img src="_images/logo_noback.png" style="position:relative; margin-right:20px; z-index:100; margin-top:-47px; margin-left: -2%;" />--%>
   <img id="logo" src="_images/logo_noback.png" />
    </a>
    
    </li>
    <li><a href="#">Home</a></li>
    <li><a href="#">Documentation</a></li>      
    
    <li>
         <select  class = "select_typ" name="foodStuff" id="foodStuff" tabindex="1" onchange="foodstuff_fn()">
			<option value="" >Hungry?</option>
			<optgroup label="Food">
				<option value="food">Food</option>
                <option value="bakery">Bakery</option>
				<option value="cafe">Cafe</option>
                <option value="restaurants">Restaurants</option>
            </optgroup>
			<optgroup label="Drinks">
				<option value="bar">Bar</option>
				<option value="liquor">Liquor Store</option>
			</optgroup>
			<option value="establishment">Other Store</option>
			<option value="store">Other</option>
		</select>
           </li>

           <li>
         <select class = "select_typ" name="liesure" id="liesure" tabindex="2" onchange="liesure_fn()">
			<option value="">Bored?</option>
			<optgroup label="Wanna Party?">
				<option value="casino">Casino</option>
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
           <li >
         <select class = "select_typ" name="other" id="other" tabindex="1" onchange="other_fn()">
			<option value="">More places</option>
			    <option value="airport">Airport</option>
                <option value="atm">Need Money?</option>
		        <option value="bank">bank</option>
                <option value="gas_station">Gas Station</option>
                <option value="health">Health care</option>
                <option value="library">Library</option>
                <option value="subway_station">Subway Station</option>          
		</select>
           </li>       
           
           
           <li id="login">
           
        <asp:LoginView ID="LoginView0" Runat="server">
        <LoggedInTemplate>
           
        <asp:LoginName
            ID="Login1"
            Runat="server"
            FormatString="Welcome <span class='red'>{0}</span>" />
        </LoggedInTemplate>
        </asp:LoginView>
           
        <asp:LoginStatus ID="LoginStatus2" runat="server" LoginText="Sign In">     
        </asp:LoginStatus>
        </li>      
        </ul>
</div>

 <%--~~~~END OF HEADER~~~~--%>
<div class="clear-both"></div>
<div>
<div id="inputfield">
        <div>
            <input id="searchTextField" type="text" size="50">
            <asp:Button ID="find" runat="server" CssClass="buttonStlye" Text="Search" OnClientClick="initialize()"/>
            
            <asp:CheckBox ID="AutoFillCheck" runat="server" OnClick="initialize()" Text="AutoComplete" />

            <span style="float: right; padding-right: 7px">
                <asp:CheckBox ID="nearbySearch" runat="server" Text="Search Nearby"
                    OnClick="initialize()" /></span><br />
             
            <span style="float: right">Travel Distance(in meters)&nbsp;
                <select id="radii" onchange="initialize()" disabled>
                    <option>10</option>
                    <option>100</option>
                    <option>500</option>
                    <option>1000</option>
                    <option>5000</option>
                    <option>10000</option>
                </select>
            </span>
        </div>

        <div id="tabs">
                   <%--<div class="style1"><strong><em>List of Places found for you!!</em></strong></div>--%>
                    <ul>
                        <li id="li_tab1" onclick="tab('tab1')"><a>Place List</a></li>
                        <li id="li_tab2" onclick="tab('tab2')"><a>Directions</a></li>
                    </ul>
                    <div id="Content_Area">
                        <div id="tab1"> <p>Find Places using the Options given above</p></div>
                        <div id="tab2" class="no-display"> 
                        <p>No Directions to Display</p>
                    </div>
                        
          </div>             
                    
                    <button class="float_left" id="more"> More Similar Places </button>
                   
                    <a id="moreInfo" onclick="sendData()" target="_blank" class="float_right no-display">More info. on this place</a></div>          
                  


</div>

<%--<div style="background-color:#FFF9CA; max-width:65%; width:auto; float:left; padding:0.5%; height:inherit;">--%>

<div id="maps_display">
<asp:ScriptManager ID="SM1" runat="server">
        </asp:ScriptManager>
        <asp:UpdatePanel ID="map_update" runat="server">
            <ContentTemplate>                
                    <span id="map_heading">
                    <em><strong>Find your favourite spot near you!</strong></em></span><br />
                    <span class="style3"><a name="PAGETOP">Simply, find the places which suits your mood right now</a></span>         
             
                <div id="map-canvas"></div>               
              
            </ContentTemplate>
            <Triggers>
                <asp:AsyncPostBackTrigger ControlID="nearbySearch" EventName="CheckedChanged" />
                <asp:AsyncPostBackTrigger ControlID="AutoFillCheck" EventName="CheckedChanged" />
                <asp:AsyncPostBackTrigger ControlID="find" EventName="Click" />
            </Triggers>
        </asp:UpdatePanel>
</div>


<div class="clear-both"></div>
</div>

<%--photos div--%>

<div class="rounded">
<div id="photosName"> </div>
<div class="container wrapper">
<div id="photos"></div>
</div>
</div>

<%--comment and reviews--%>
<div id="commentDivision">
   
    <asp:UpdatePanel ID="commentUpdate" runat="server">
        <ContentTemplate>
        
            <div class="comment-box-block"> 
            <div class="comment-box-header"><i><strong> Site User reviews about places</strong></i></div>
            
            <div id="commentSpace" runat="server"></div>
            <div id="UserComment">
            <table class="style1">
                <tr>
            <td class="style5">
                Name:
            </td>
            <td>
                <asp:TextBox ID="name" runat="server" Width="150px" CssClass="style5" Enabled="false"></asp:TextBox>
                 <asp:RegularExpressionValidator ID="reg1" runat="server" 
                    ControlToValidate="name" ValidationExpression="^[a-zA-Z0-9 ]+$"
                 ErrorMessage="Enter valid name" Font-Italic="True" 
                    Font-Names="Aparajita" Font-Size="Small" ForeColor="#CC6600"></asp:RegularExpressionValidator>
            </td>
        </tr>
        <tr>
            <td class="style5">
                Comment:
            </td>
            <td>
                <asp:TextBox ID="comment" runat="server" EnableTheming="False" TextMode="MultiLine"  CssClass="comment-box" Enabled="true" Text="Comment Here (You must sign in before commenting)" Font-Size="Medium" ForeColor="#333333"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td class="style4">
                &nbsp;
            </td>
            <td>
                <asp:Button ID="post" runat="server" OnClick="post_Click" Text="Post Comment" Width="131px" CssClass="style5" Enabled="false" />
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



<%--
<div class="comment-box-block">
<div class="comment-box-header">
<div id="reviewsPlaceName" class="comment-header"></div>
<div id="reviews" style="margin:1%"></div>
</div>
</div>--%>

<div id="reviews_box" class="comment-box-block">
<div id="reviewsPlaceName" class="comment-box-header">Reviews of places you searched for</div>
<div id="reviews">No reviews found for the search.</div>
</div>
<br />
</div>
<div class="clear-both"></div>
<footer id="pageFooter">
      <nav class="footerNav">
    </h2>
          <p>Find Places | &copy;Copyright All rights reserved.</p> 
    
</footer>
</form>
</body>
</html>
