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

import java.io.IOException;
import java.util.Locale;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.xml.sax.SAXException;

/**
 * This TestCase is a confirmation of the parameter of the validator's method.
 *
 * @version $Revision: 478345 $ $Date: 2006-11-22 22:25:19 +0000 (Wed, 22 Nov 2006) $
 */
public class ParameterTest extends TestCommon {

    private static final String FORM_KEY = "nameForm";

    private static final String firstNameField = "firstName";

    private static final String middleNameField = "middleName";

    private static final String lastNameField = "lastName";

    private String firstName;

    private String middleName;

    private String lastName;

    /**
     * Constructor.
     */
    public ParameterTest(String name) {
        super(name);
    }

    /**
     * Start the tests.
     * 
     * @param theArgs the arguments. Not used
     */
    public static void main(String[] theArgs) {
        junit.awtui.TestRunner.main(new String[] {
            ParameterTest.class.getName()
        });
    }

    /**
     * Create a Test Suite
     * @return a test suite (<code>TestSuite</code>) that includes all
     *         methods starting with "test"
     */
    public static Test suite() {
        return new TestSuite(ParameterTest.class);
    }

    /**
     * Load <code>ValidatorResources</code> from
     * ValidatorResultsTest-config.xml.
     */
    protected void setUp() throws IOException, SAXException {
        // Load resources
        loadResources("ParameterTest-config.xml");

        // initialize values
        firstName = "foo";
        middleName = "123";
        lastName = "456";

    }

    protected void tearDown() {
    }

    /**
     * Test all validations ran and passed.
     */
    public void testAllValid() throws ValidatorException {

        // Create bean to run test on.
        NameBean bean = createNameBean();

        Validator validator = new Validator(resources, FORM_KEY);

        // add the name bean to the validator as a resource
        // for the validations to be performed on.
        validator.setParameter(Validator.BEAN_PARAM, bean);
        validator.setParameter(Validator.LOCALE_PARAM, Locale.getDefault());

        // Get results of the validation.
        ValidatorResults results = null;
        try {
            results = validator.validate();
        } catch(Exception e) {
            fail("Validator.validate() threw " + e);
        }
        assertParameterValue(validator, Validator.BEAN_PARAM, Object.class);
        assertParameterValue(validator, Validator.FIELD_PARAM, Field.class);
        assertParameterValue(validator, Validator.FORM_PARAM, Form.class);
        assertParameterValue(validator, Validator.LOCALE_PARAM, Locale.class);
        assertParameterValue(validator, Validator.VALIDATOR_ACTION_PARAM,
                ValidatorAction.class);
        assertParameterValue(validator, Validator.VALIDATOR_PARAM,
                Validator.class);
        assertParameterValue(validator, Validator.VALIDATOR_RESULTS_PARAM,
                ValidatorResults.class);
    }

    private void assertParameterValue(Validator validator, String name,
            Class type) {
        Object value = validator.getParameterValue(name);
        assertNotNull("Expected '" + type.getName() + "' but was null", value);
        assertTrue("Expected '" + type.getName() + "' but was '" + value.getClass().getName() + "'",
                   type.isInstance(value));
    }

    /**
     * Create a NameBean.
     */
    private NameBean createNameBean() {
        NameBean name = new NameBean();
        name.setFirstName(firstName);
        name.setMiddleName(middleName);
        name.setLastName(lastName);
        return name;
    }
}
