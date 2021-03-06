/*
 * This file is part of ELKI:
 * Environment for Developing KDD-Applications Supported by Index-Structures
 *
 * Copyright (C) 2017
 * ELKI Development Team
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package de.lmu.ifi.dbs.elki.algorithm.clustering.subspace;

import org.junit.Test;

import de.lmu.ifi.dbs.elki.algorithm.clustering.AbstractClusterAlgorithmTest;
import de.lmu.ifi.dbs.elki.data.Clustering;
import de.lmu.ifi.dbs.elki.data.DoubleVector;
import de.lmu.ifi.dbs.elki.data.model.SubspaceModel;
import de.lmu.ifi.dbs.elki.database.Database;
import de.lmu.ifi.dbs.elki.utilities.ELKIBuilder;

/**
 * Performs a full SUBCLU run, and compares the result with a clustering derived
 * from the data set labels. This test ensures that SUBCLU performance doesn't
 * unexpectedly drop on this data set (and also ensures that the algorithms
 * work, as a side effect).
 * 
 * @author Elke Achtert
 * @author Katharina Rausch
 * @author Erich Schubert
 * @since 0.3
 */
public class SUBCLUTest extends AbstractClusterAlgorithmTest {
  /**
   * Run SUBCLU with fixed parameters and compare the result to a golden
   * standard.
   */
  @Test
  public void testSUBCLUResults() {
    Database db = makeSimpleDatabase(UNITTEST + "subspace-simple.csv", 600);
    Clustering<SubspaceModel> result = new ELKIBuilder<SUBCLU<DoubleVector>>(SUBCLU.class) //
        .with(SUBCLU.EPSILON_ID, 0.001) //
        .with(SUBCLU.MINPTS_ID, 100) //
        .build().run(db);
    // PairCounting is not appropriate here: overlapping clusterings!
    // testFMeasure(db, result, 0.9090);
    testClusterSizes(result, new int[] { 191, 194, 395 });
  }

  /**
   * Run SUBCLU with fixed parameters and compare the result to a golden
   * standard.
   */
  @Test
  public void testSUBCLUSubspaceOverlapping() {
    Database db = makeSimpleDatabase(UNITTEST + "subspace-overlapping-3-4d.ascii", 850);
    Clustering<SubspaceModel> result = new ELKIBuilder<SUBCLU<DoubleVector>>(SUBCLU.class) //
        .with(SUBCLU.EPSILON_ID, 0.04) //
        .with(SUBCLU.MINPTS_ID, 70) //
        .build().run(db);
    // PairCounting is not appropriate here: overlapping clusterings!
    // testFMeasure(db, result, 0.49279033);
    testClusterSizes(result, new int[] { 99, 247, 303, 323, 437, 459 });
  }
}
