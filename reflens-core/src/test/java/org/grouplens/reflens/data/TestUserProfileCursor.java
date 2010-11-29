/*
 * RefLens, a reference implementation of recommender algorithms.
 * Copyright 2010 Michael Ekstrand <ekstrand@cs.umn.edu>
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 *
 * As a special exception, the copyright holders of this library give you
 * permission to link this library with independent modules to produce an
 * executable, regardless of the license terms of these independent modules, and
 * to copy and distribute the resulting executable under terms of your choice,
 * provided that you also meet, for each linked independent module, the terms
 * and conditions of the license of that module. An independent module is a
 * module which is not derived from or based on this library. If you modify this
 * library, you may extend this exception to your version of the library, but
 * you are not obligated to do so. If you do not wish to do so, delete this
 * exception statement from your version.
 */

/**
 * 
 */
package org.grouplens.reflens.data;


import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Michael Ekstrand <ekstrand@cs.umn.edu>
 *
 */
public class TestUserProfileCursor {
	private List<Rating> ratings;
	private Cursor<Rating> ratingCursor;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		ratings = new ArrayList<Rating>();
		ratings.add(new Rating(2, 5, 3.0));
		ratings.add(new Rating(2, 3, 3.0));
		ratings.add(new Rating(2, 39, 2.5));
		ratings.add(new Rating(5, 7, 2.5));
		ratings.add(new Rating(5, 39, 7.2));
		ratingCursor = Cursors.wrap(ratings);
	}

	@Test
	public void testCursor() {
		Cursor<UserRatingProfile> cursor =
			new AbstractRatingDataSource.UserProfileCursor(ratingCursor);
		assertTrue(cursor.hasNext());
		UserRatingProfile profile = cursor.next();
		assertTrue(cursor.hasNext());
		assertEquals(2, profile.getUser());
		assertEquals(3, profile.getRatings().size());
		profile = cursor.next();
		assertFalse(cursor.hasNext());
		assertEquals(5, profile.getUser());
		assertEquals(2, profile.getRatings().size());
	}

}