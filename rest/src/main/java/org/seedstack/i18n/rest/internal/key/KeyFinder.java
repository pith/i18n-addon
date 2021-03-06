/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.i18n.rest.internal.key;


import org.seedstack.business.finder.Finder;
import org.seedstack.business.view.Page;
import org.seedstack.business.view.PaginatedView;

import java.util.List;

/**
 * @author pierre.thirouin@ext.mpsa.com
 */
@Finder
public interface KeyFinder {

    /**
     * Returns a key for a specified locale.
     *
     * @param name key name
     * @return a key representation
     */
    KeyRepresentation findKeyWithName(String name);

    /**
     * Returns request ranged result of key representations.
     *
     * @param page    the page to query
     * @param criteria the criteria filters
     * @return paginated key representations
     */
    PaginatedView<KeyRepresentation> findKeysWithTheirDefaultTranslation(Page page, KeySearchCriteria criteria);

    /**
     * Finds all keys with criteria.
     *
     * @param criteria criteria
     * @return list of key representation
     */
    List<KeyRepresentation> findKeysWithTheirDefaultTranslation(KeySearchCriteria criteria);
}
