/**
 * Copyright (c) 2000-2009, Jasig, Inc.
 * See license distributed with this file and available online at
 * https://www.ja-sig.org/svn/jasig-parent/tags/rel-10/license-header.txt
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
