/**
 * Copyright (c) 2000-2009, Jasig, Inc.
 * See license distributed with this file and available online at
 * https://www.ja-sig.org/svn/jasig-parent/tags/rel-10/license-header.txt
 */

package org.jasig.portlet.weather;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author Eric Dalquist
 * @version $Revision$
 */
public class DuplicateLocationException extends DataIntegrityViolationException {
    private static final long serialVersionUID = 1L;

    public DuplicateLocationException(String msg, Throwable cause) {
        super(msg, cause);
        // TODO Auto-generated constructor stub
    }

    public DuplicateLocationException(String msg) {
        super(msg);
        // TODO Auto-generated constructor stub
    }
}
