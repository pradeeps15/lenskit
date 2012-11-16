/*
 * LensKit, an open source recommender systems toolkit.
 * Copyright 2010-2012 Regents of the University of Minnesota and contributors
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package org.grouplens.lenskit.transform.quantize;

import org.grouplens.lenskit.core.Shareable;

import java.io.Serializable;

/**
 * Abstract quantizer implementation using a pre-generated array of possible
 * values. Values are quantized to their closest discrete possibility.
 *
 * @author Michael Ekstrand
 */
@Shareable
public class ValueArrayQuantizer implements Quantizer, Serializable {
    private static final long serialVersionUID = 2150927895689488171L;

    protected final double[] values;

    /**
     * Construct a new quantizer using the specified array of values.
     *
     * @param vs The discrete values to quantize to.
     */
    public ValueArrayQuantizer(double[] vs) {
        if (vs.length <= 0) {
            throw new IllegalArgumentException("must have at least one value");
        }
        values = vs;
    }

    @Override
    public double[] getValues() {
        return values;
    }

    @Override
    public double getValue(int i) {
        return getIndexValue(i);
    }

    @Override
    public int apply(double v) {
        return index(v);
    }

    @Override
    public int getCount() {
        return values.length;
    }

    @Override
    public double getIndexValue(int i) {
        try {
            return values[i];
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("invalid discrete value", e);
        }
    }

    @Override
    public int index(double val) {
        final int n = values.length;
        assert n > 0;
        int closest = -1;
        double closev = Double.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            double diff = Math.abs(val - values[i]);
            if (diff <= closev) { // <= to round up
                closev = diff;
                closest = i;
            }
        }
        if (closest < 0) {
            throw new RuntimeException("could not quantize value");
        } else {
            return closest;
        }
    }

    @Override
    public double quantize(double val) {
        return getIndexValue(index(val));
    }
}
