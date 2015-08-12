package com.hand.exam_3_2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ex2 {	
	
	public static Connection getConnection(){
		Connection conn =null;
		try {
			Class.forName("com.mysql.jdbc.Driver");//用来注册mysql的jdbc程序
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila","root","lin051477515");//连接数据库

		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;		
	}
	

	public static void select(int id){     

		Connection conn = getConnection();
		
		String sql1 = "select first_name,last_name from customer where customer_id = "+id;
		String sql2 = "select f.film_id,f.title,r.rental_date from rental r,inventory i,film_text f "
				+ "where r.inventory_id = i.inventory_id and i.film_id = f.film_id and r.customer_id="+id+" order by rental_date desc;";
	
		
		try {

			PreparedStatement ps1 = conn.prepareCall(sql1);	
			PreparedStatement ps2 = conn.prepareCall(sql2);
	
			
			ResultSet rs1 =ps1.executeQuery(sql1);//执行sql语句
			ResultSet rs2 =ps2.executeQuery(sql2);
			while (rs1.next()) {
				System.out.println(rs1.getString("first_name")+rs1.getString("last_name")+"租用的Film->");					

			}
			
			System.out.println("FILMID"+"  \t  "+"FILM名字"+"\t\t"+"租用时间");
			while (rs2.next()) {
				System.out.println(rs2.getInt("film_id")+"\t    "+rs2.getString("title")+"\t\t"+rs2.getString("rental_date"));						

			}
			conn.close();			

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		
		System.out.println("请输入Customer_id号：");
		
		Scanner sc =new Scanner(System.in);
		int id =sc.nextInt();
		select(id);
		
		
	}

}

