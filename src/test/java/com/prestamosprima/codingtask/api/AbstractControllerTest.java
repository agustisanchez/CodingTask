package com.prestamosprima.codingtask.api;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.prestamosprima.codingtask.app.WebSecurityConfig;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:test-applicationContext.xml", classes = WebSecurityConfig.class)
public abstract class AbstractControllerTest {

	public AbstractControllerTest() {
		super();
	}

	protected String checkLocationAndExtractId(String location) {
		Assert.assertTrue("Empty location header", StringUtils.isNotBlank(location));
		int idIdx = location.lastIndexOf('/');
		Assert.assertTrue("Malformed location URL", idIdx != -1);

		String locationId = location.substring(idIdx + 1);
		return locationId;
	}

}