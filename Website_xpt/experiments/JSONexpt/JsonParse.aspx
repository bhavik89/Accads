<%@ Page Language="C#" AutoEventWireup="true" CodeFile="JsonParse.aspx.cs" Inherits="experiments_JSONexpt_JsonParse" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>JsonParse</title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.min.js"></script>

   <%-- <script type="text/javascript">

        var myArr = 

        $.getJSON('dunkin.json' , function(data){

        $.each(data, function(key, value){
          myArr.push("<p>"+value.results+"</p>")
          })

          $("contents").html(myArr.join(" "))
                
        })

    
    </script>--%>




</head>
<body>
    <div id="placeholder"></div>
    <script src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js" type="text/javascript"></script>
    <script type="text/javascript">

   
      //  $.getJSON('dunkin.json', function (data) {
        $.ajax({
            url: "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=42.34037,-71.088735&radius=500&types=food&sensor=false&key=AIzaSyDvivWD7UeTo2DAc7jBE3aGMJO-lOKM-nQ",
            //url: "http://api.feedzilla.com/v1/categories/26/articles.json",
            success: function (data) {
            
        //        var jsonData = eval("(" + data + ")");
                var output = [];
                for (var i in data.results) {
                    //  output += "<li>" + data.users[i].firstName + " " + data.users[i].lastName + "--" + data.users[i].joined.month + "</li>";

                    document.getElementById("placeholder").innerHTML += "<p>Name: " + Data.results[i].name + "<br /></p>"
                  //  document.getElementById("placeholder").innerHTML += "Vicinity: " + Data.results[i].vicinity + "<br />"
                  //  document.getElementById("placeholder").innerHTML += "Latitude:" + Data.results[i].geometry.location.lat + "<br />";
                  //  document.getElementById("placeholder").innerHTML += "Longitude:" + Data.results[i].geometry.location.lng + "</p>";
                }
            },

            error: function (data) {
                alert("Error!!");
            }
            //output += "</ul>";
            //  document.getElementById("placeholder").innerHTML = output;
        });
    </script>
  
</body>
</html>
