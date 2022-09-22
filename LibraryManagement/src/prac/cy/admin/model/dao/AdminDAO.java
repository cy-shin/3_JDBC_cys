package prac.cy.admin.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import prac.cy.library.vo.Library;

import static prac.cy.common.JDBCTemplate.*;

public class AdminDAO {

	private PreparedStatement pstmt;
	private Statement stmt;
	private ResultSet rs;
	
	private Properties prop;
	
	public AdminDAO() {
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("admin-query.xml"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
