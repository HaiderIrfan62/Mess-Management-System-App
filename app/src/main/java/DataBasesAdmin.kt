import android.widget.Toast
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

class DataBasesAdmin {



    constructor(){

    }

    fun startConnection(){
        val url:String = "jdbc:postgresql://mel.db.elephantsql.com:5432/cjrkjmnn"
        val user:String = "cjrkjmnn"
        val password:String = "0c250Dpa3vuyQ_d7LrK6YybyiJ8Pcsl8"

        var conn: Connection?=null

        try{
            conn = DriverManager.getConnection(url, user, password)
        }
        catch(e:Exception){
            e.printStackTrace()
        }
    }

    fun readTable(){
        var stmt: Statement? = null
        var resultset: ResultSet? = null

        val url:String = "jdbc:postgresql://mel.db.elephantsql.com:5432/cjrkjmnn"
        val user:String = "cjrkjmnn"
        val password:String = "0c250Dpa3vuyQ_d7LrK6YybyiJ8Pcsl8"

        var conn: Connection?=null

        try{
            //conn = DriverManager.getConnection(url, user, password)
        }
        catch(e:Exception){
            e.printStackTrace()
        }

        try{
            stmt = conn!!.createStatement()
            resultset = stmt.executeQuery("SELECT * FROM STUDENT")

            while(resultset.next() == true){
                //var managerId = resultset.getString("student_giki_email")
                var managerName = resultset.getString("student_name")
                println("Student Email " + " name: " + managerName)
            }
        }
        catch(e:Exception){
            e.printStackTrace()
        }


    }
}