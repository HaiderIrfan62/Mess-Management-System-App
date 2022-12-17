package com.example.messmanagementsystem
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.DriverManager

class DataBaseTest {
    val url:String = "jdbc:postgresql://mel.db.elephantsql.com:5432/cjrkjmnn"
    val user:String = "cjrkjmnn"
    val password:String = "0c250Dpa3vuyQ_d7LrK6YybyiJ8Pcsl8"

    private val INSERT_USERS_SQL = "INSERT INTO STUDENT" +
            "  (student_giki_email, student_name, student_password, hostel_no) VALUES " +
            " (?, ?, ?, ?);"

    //@Throws(SQLException::class)
    //@JvmStatic
    fun main(email:String, name:String, pass:String, hostel:Int) {
        val createTableExample = DataBaseTest()
        createTableExample.insertRecord(email, name, pass, hostel)
    }

    @Throws(SQLException::class)
    fun insertRecord(email:String, name:String, pass:String, hostel:Int) {
        println(INSERT_USERS_SQL)
        // Step 1: Establishing a Connection
        try {
            DriverManager.getConnection(url, user, password).use { connection ->
                connection.prepareStatement(INSERT_USERS_SQL).use { preparedStatement ->
                    preparedStatement.setString(1, email)
                    preparedStatement.setString(2, name)
                    preparedStatement.setString(3, pass)
                    preparedStatement.setInt(4, hostel)
                    System.out.println(preparedStatement)
                    // Step 3: Execute the query or update query
                    preparedStatement.executeUpdate()
                }
            }
        } catch (e: SQLException) {

            // print SQL exception information
            printSQLException(e)
        }

        // Step 4: try-with-resource statement will auto close the connection.
    }

    fun printSQLException(ex: SQLException) {
        for (e in ex) {
            if (e is SQLException) {
                e.printStackTrace(System.err)
                System.err.println("SQLState: " + (e as SQLException).getSQLState())
                System.err.println("Error Code: " + (e as SQLException).getErrorCode())
                System.err.println("Message: " + e.message)
                var t: Throwable? = ex.cause
                while (t != null) {
                    println("Cause: $t")
                    t = t.cause
                }
            }
        }
    }
}