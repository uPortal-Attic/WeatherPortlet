/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.jasig.portlet.weather;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Wrapper for {@link URLEncoder} that converts the checked exception to a runtime exception
 * 
 * @author Eric Dalquist
 * @version $Revision$
 */
public class QuietUrlCodec {
    public static String encode(String s, String enc) {
        try {
            return URLEncoder.encode(s, enc);
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Encoding '" + enc + "' is not supported", e);
        }
    }
}
