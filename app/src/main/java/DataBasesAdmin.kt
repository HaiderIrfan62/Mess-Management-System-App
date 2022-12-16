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
            conn = DriverManager.getConnection(url, user, password)
        }
        catch(e:Exception){
            e.printStackTrace()
        }

        try{
            stmt = conn!!.createStatement()
            resultset = stmt.executeQuery("SELECT * FROM MANAGER;")

            while(resultset.next() == true){
                var managerId = resultset.getInt("manager_id")
                var managerName = resultset.getString("manager_name")
                println("Manager ID " + managerId + " manager name: " + managerName)
            }
        }
        catch(e:Exception){
            e.printStackTrace()
        }
    }
}