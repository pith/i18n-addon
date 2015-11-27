/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.i18n;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.i18n.internal.domain.model.key.Key;
import org.seedstack.i18n.internal.domain.model.key.KeyFactory;
import org.seedstack.i18n.internal.domain.model.key.KeyRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.transaction.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * @author pierre.thirouin@ext.mpsa.com
 */
@JpaUnit("seed-i18n-domain")
@Transactional
@RunWith(SeedITRunner.class)
public class KeyJpaRepositoryIT {

    public String keyId = "key";

    private Key expectedKey;

    @Inject
    private KeyFactory factory;
    @Inject
    private KeyRepository keyRepository;

    @Before
    public void setUp() {
        keyId = UUID.randomUUID().toString();
        expectedKey = factory.createKey(keyId);
        expectedKey.setComment("comment");
        expectedKey.addTranslation("fr", "traduction");
        expectedKey.addTranslation("en", "translation");
    }

    @After
    public void cleanUp() {
        List<Key> keys = keyRepository.loadAll();
        for (Key key : keys) {
            keyRepository.delete(key);
        }
    }

    @Test
    public void persist_then_load() {
        keyRepository.persist(expectedKey);
        Key key2 = keyRepository.load(expectedKey.getEntityId());
        Assertions.assertThat(key2).isEqualTo(expectedKey);
    }

    @Test
    public void count() {
        keyRepository.persist(expectedKey);
        Long count = keyRepository.count();
        Assertions.assertThat(count).isEqualTo(1);
    }

    @Test
    public void save_then_load() {
        keyRepository.save(expectedKey);
        Key key = keyRepository.load(expectedKey.getEntityId());
        Assertions.assertThat(key.getComment()).isEqualTo("comment");
    }

    @Test
    public void save_then_delete_multiple_keys() {
        Key key1 = factory.createKey(keyId + 1);
        key1.addTranslation("fr", "traduction");
        key1.addTranslation("en", "translation");

        Key key2 = factory.createKey(keyId + 2);
        key2.addTranslation("fr", "traduction");
        key2.addTranslation("en", "translation");

        List<Key> keys = Lists.newArrayList(expectedKey, key1, key2);

        keyRepository.persist(keys);

        List<Key> key = keyRepository.loadAll();
        Assertions.assertThat(key.size()).isEqualTo(3);

        keyRepository.delete(keys);

        List<Key> result = keyRepository.loadAll();
        Assertions.assertThat(result.size()).isEqualTo(0);

    }

    @Test
    public void persist_load_then_delete() {
        keyRepository.persist(expectedKey);
        Key key = keyRepository.load(keyId);
        Assertions.assertThat(key).isNotNull();
        keyRepository.delete(key);
        key = keyRepository.load(keyId);
        Assertions.assertThat(key).isNull();
    }
}
