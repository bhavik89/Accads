<%@ Page Language="C#" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<script runat="server">
    </script>    



<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>
    <link href="jquery.sexy-combo-2.1.3/css/sexy-combo.css" rel="stylesheet" type="text/css" />
    <link href="jquery.sexy-combo-2.1.3/css/sexy/sexy.css" rel="stylesheet" type="text/css" />
    <link href="jquery.sexy-combo-2.1.3/css/custom/custom.css" rel="stylesheet" type="text/css" />
    <script src="jquery.sexy-combo-2.1.3/examples/jquery-1.3.2.min.js" type="text/javascript"></script>
    <script src="jquery.sexy-combo-2.1.3/examples/jquery.bgiframe.min.js" type="text/javascript"></script>
    <script src="jquery.sexy-combo-2.1.3/jquery.sexy-combo.js" type="text/javascript"></script>

    <script type="text/javascript" >
        $(function () {

            $("#empty-combo").sexyCombo({
                emptyText: "Choose a state..."
            });
        });
   </script>


</head>
<body>
    <form id="form1" runat="server">
    <div>
    <p>
      <label for="empty-combo">Choose the state:</label><br />
      <select id="empty-combo" name="empty-combo"  size="1">
        <option value="500">Very near</option>
        <option value="805">1/2 mile</option>
        <option value="1610">1 mile</option>
        <option value="3220">2 miles</option>
        <option value="4830">3 miles</option>
        <option value="8047">5 miles</option>
        <option value="CO">10 miles</option>
        <option value="CT">15 miles</option>
        <option value="DE">20 miles</option>
        <option value="FL">30 miles</option>
        
      </select>
    </p>  
     
    
    
    
    </div>


    </form>
</body>
</html>
