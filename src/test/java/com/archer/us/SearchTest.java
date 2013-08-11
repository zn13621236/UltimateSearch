package com.archer.us;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.archer.us.service.SearchService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/root-context.xml" })
public class SearchTest {
	@Autowired
	SearchService ss;

	@Test
	public void test() {
		ss.searchEmail("https://www.google.com/?q=seattle+roofing&q=seattle+roofing#bav=on.2,or.r_cp.r_qf.&ei=e9cHUs3qJKGFyQGGrIHIBQ&fp=b6cfaf492201c2a5&q=seattle+roofing+seattle+roofing&sa=N&start=60");
	}

}
