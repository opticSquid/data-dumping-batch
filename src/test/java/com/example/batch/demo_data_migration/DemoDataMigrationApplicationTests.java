package com.example.batch.demo_data_migration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.batch.demo_data_migration.entity.Person;
import com.mysql.cj.xdevapi.PreparableStatement;

@SpringBootTest
@SpringBatchTest
class DemoDataMigrationApplicationTests {
	@MockBean
	@Qualifier("dataSource")
	DataSource dataSourceMock;
	@MockBean
	@Qualifier("dataTarget")
	DataSource dataTargetMock;
	@Autowired
	JobLauncherTestUtils jobLauncherTestUtils;
	@Autowired
	JobRepositoryTestUtils jobRepositoryTestUtils;

	@BeforeEach
	void cleanUp() {
		this.jobRepositoryTestUtils.removeJobExecutions();
	}

	@Test
	void run_job_for_success() {
		// given
		JobParameters JobParameters = new JobParametersBuilder().toJobParameters();
		List<Person> mockPersonList = Arrays.asList(new Person(1L, "John Doe", 30));
		try {
			Mockito.when(dataSourceMock.getConnection()).thenReturn(getConnectionMock());
			Mockito.when(getConnectionMock().prepareStatement(Mockito.anyString())).thenReturn(getStatementMock());
			Mockito.when(getStatementMock().execute()).thenReturn(getResultSetMock(mockPersonList));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Connection getConnectionMock() {

	}

	private PreparableStatement<String> getStatementMock() {

	}

	private ResultSet getResultSetMock(List<Person> data) {

	}
}
