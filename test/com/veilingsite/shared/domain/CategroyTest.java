package com.veilingsite.shared.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class CategroyTest {

	@Test
	public void testCategory() {
		// Testcase: create Category
		Category c = new Category();
		assertTrue(c instanceof Category);
	}

	@Test
	public void testCategoryStringInt() {
		// Testcase: create Category full constructor
		Category c = new Category("categorie",4);
		assertEquals("categorie", c.getTitle());
		assertEquals(4, c.getParent());
	}

	@Test
	public void testGetTitle() {
		// Testcase: set & retrieve category title
		Category c = new Category();
		c.setTitle("categorie");
		assertEquals("categorie", c.getTitle());
	}

	@Test
	public void testGetParent() {
		// Testcase: set & retrieve category parent
		Category c = new Category();
		c.setParent(3);
		assertEquals(3, c.getParent());
	}

}
