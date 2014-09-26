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
 * Performs Validation Test for <code>short</code> validations.
 *
 * @version $Revision: 478334 $ $Date: 2006-11-22 21:31:54 +0000 (Wed, 22 Nov 2006) $
 */
public class ShortTest extends TestNumber {

   public ShortTest(String name) {                  
       super(name);
      FORM_KEY = "shortForm";
      ACTION = "short";
   }

   /**
    * Start the tests.
    *
    * @param theArgs the arguments. Not used
    */
   public static void main(String[] theArgs) {
       junit.awtui.TestRunner.main(new String[] {ShortTest.class.getName()});
   }

   /**
    * @return a test suite (<code>TestSuite</code>) that includes all methods
    *         starting with "test"
    */
   public static Test suite() {
       // All methods starting with "test" will be executed in the test suite.
       return new TestSuite(ShortTest.class);
   }

   /**
    * Tests the short validation.
    */
   public void testShortMin() throws ValidatorException {
      // Create bean to run test on.
      ValueBean info = new ValueBean();
      info.setValue(new Short(Short.MIN_VALUE).toString());
      
      valueTest(info, true);
   }

   /**
    * Tests the short validation.
    */
   public void testShortMax() throws ValidatorException {
      // Create bean to run test on.
      ValueBean info = new ValueBean();
      info.setValue(new Short(Short.MAX_VALUE).toString());
      
      valueTest(info, true);
   }

   /**
    * Tests the short validation failure.
    */
   public void testShortBeyondMin() throws ValidatorException {
      // Create bean to run test on.
      ValueBean info = new ValueBean();
      info.setValue(Short.MIN_VALUE + "1");
      
      valueTest(info, false);
   }
   
   /**
    * Tests the short validation failure.
    */
   public void testShortBeyondMax() throws ValidatorException {
      // Create bean to run test on.
      ValueBean info = new ValueBean();
      info.setValue(Short.MAX_VALUE + "1");
      
      valueTest(info, false);
   }
}                                                         