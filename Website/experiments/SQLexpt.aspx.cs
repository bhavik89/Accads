using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data.SqlClient;
using System.Configuration;

public partial class experiments_SQLexpt : System.Web.UI.Page
{
    SqlConnection con = new SqlConnection(ConfigurationManager.ConnectionStrings["ConnectionString1"].ConnectionString);
    protected void Page_Load(object sender, EventArgs e)
    {
        con.Open();
    }
    protected void Button1_Click(object sender, EventArgs e)
    {
        SqlCommand cmd = new SqlCommand("insert into personData values ('"+name.Text+"','"+age.Text+"','"+city.Text+"' )",con);
        cmd.ExecuteNonQuery();
        con.Close();
        data.Visible = true;
        data.Text = "Thank You for giving your Data";
        name.Text = "";
        age.Text = "";
        city.Text = "";
        GridView2.DataBind();
       
    }
}