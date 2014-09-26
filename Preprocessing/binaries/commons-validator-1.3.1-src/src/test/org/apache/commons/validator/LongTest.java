/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.validator;

import junit.framework.Test;
import junit.framework.TestSuite;

/**                                                       
 * Performs Validation Test for <code>long</code> validations.
 *
 * @version $Revision: 478334 $ $Date: 2006-11-22 21:31:54 +0000 (Wed, 22 Nov 2006) $
 */
public class LongTest extends TestNumber {

    public LongTest(String name) {
        super(name);
        FORM_KEY = "longForm";
        ACTION = "long";
    }

    /**
     * Start the tests.
     *
     * @param theArgs the arguments. Not used
     */
    public static void main(String[] theArgs) {
        junit.awtui.TestRunner.main(new String[]{LongTest.class.getName()});
    }

    /**
     * @return a test suite (<code>TestSuite</code>) that includes all methods
     *         starting with "test"
     */
    public static Test suite() {
        // All methods starting with "test" will be executed in the test suite.
        return new TestSuite(LongTest.class);
    }

    /**
     * Tests the long validation.
     */
    public void testLong() throws ValidatorException {
        // Create bean to run test on.
        ValueBean info = new ValueBean();
        info.setValue("0");

        valueTest(info, true);
    }

    /**
     * Tests the long validation.
     */
    public void testLongMin() throws ValidatorException {
        // Create bean to run test on.
        ValueBean info = new ValueBean();
        info.setValue(new Long(Long.MIN_VALUE).toString());

        valueTest(info, true);
    }

    /**
     * Tests the long validation.
     */
    public void testLongMax() throws ValidatorException {
        // Create bean to run test on.
        ValueBean info = new ValueBean();
        info.setValue(new Long(Long.MAX_VALUE).toString());

        valueTest(info, true);
    }

    /**
     * Tests the long validation failure.
     */
    public void testLongFailure() throws ValidatorException {
        // Create bean to run test on.
        ValueBean info = new ValueBean();

        valueTest(info, false);
    }

    /**
     * Tests the long validation failure.
     */
    public void testLongBeyondMin() throws ValidatorException {
        // Create bean to run test on.
        ValueBean info = new ValueBean();
        info.setValue(Long.MIN_VALUE + "1");

        valueTest(info, false);
    }

    /**
     * Tests the long validation failure.
     */
    public void testLongBeyondMax() throws ValidatorException {
        // Create bean to run test on.
        ValueBean info = new ValueBean();
        info.setValue(Long.MAX_VALUE + "1");

        valueTest(info, false);
    }

}