package com.hytech.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.xml.sax.InputSource;

import com.hytech.config.MvcConfiguration;
import com.hytech.dao.IssueMapper;
import com.hytech.model.Issue;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes=MvcConfiguration.class)
public class TestIssueService_DBUnit {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSessionFactory sessionFactory;
	
	@Autowired
	private IssueMapper issueMapper;
	
	private IDatabaseConnection connection;
	
	private File backupFile;
	
	@Before
	public void init() throws Exception {
		// 建立連線
		connection = new DatabaseConnection(DataSourceUtils.getConnection(dataSource));
		
		// 備份
		QueryDataSet backup = new QueryDataSet(connection);
		backup.addTable("Issue");
		backupFile = File.createTempFile("issue_backup", ".xml");
		FlatXmlDataSet.write(backup, new FileOutputStream(backupFile));
		
		// 測試前讀入初始資料集合、清除表格並將初始資料集合新增至資料庫中的表格
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		IDataSet dataSet = builder.build(new ClassPathResource("/dataSet.xml").getInputStream());
		DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
		
	}
	
	@After
	public void tearDownClass() throws Exception {
		
		IDataSet dataSet = new FlatXmlDataSet(new FlatXmlProducer(new InputSource(new FileInputStream(backupFile))));
		DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
		connection.close();
    }
	
	@Test
	public void testGetAllIssue() throws DataSetException, SQLException {
		List<Issue> result = issueMapper.selectAll();
        
        assertEquals(3, result.size());
	}
	
	@Test
	public void testGetIssue() {
		Issue resultId1 = issueMapper.selectByPrimaryKey(1);
		Issue resultId2 = issueMapper.selectByPrimaryKey(2);
		Issue resultId3 = issueMapper.selectByPrimaryKey(3);
		Issue resultId4 = issueMapper.selectByPrimaryKey(4);
		
		assertEquals("1", String.valueOf(resultId1.getId()));
		assertEquals("2", String.valueOf(resultId2.getId()));
		assertEquals("3", String.valueOf(resultId3.getId()));
		assertNull(resultId4);
	}
}
