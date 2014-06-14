/* with
 DHash|QHash|LHash hash
 char|byte|short|int|long|float|double|obj key
 short|byte|char|int|long|float|double|obj value
*/
/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.openhft.collect.impl.hash;

import net.openhft.collect.*;
import net.openhft.collect.impl.Primitives;
import net.openhft.collect.map.hash.HashCharShortMapFactory;
import net.openhft.function.*;
import net.openhft.collect.map.hash.HashCharShortMap;

import java.util.*;

import static net.openhft.collect.impl.Containers.sizeAsInt;


public abstract class DHashCharShortMapFactoryGO/*<>*/ extends DHashCharShortMapFactorySO/*<>*/ {

    DHashCharShortMapFactoryGO(/* if !(float|double key) */CharHashConfig
            /* elif float|double key //HashConfig// endif */ conf) {
        super(conf);
    }

    @Override
    public String toString() {
        return "HashCharShortMapFactory[" +
                "keyConfig=" + getConfig() + "," +
                /* if obj key */"keyEquivalence=" + getKeyEquivalence() + "," +/* endif */
                /* if obj value */"valueEquivalence=" + getValueEquivalence() +
                /* elif !(obj value) */"defaultValue=" + getDefaultValue() +/* endif */
        "]";
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = hashCode * 31 + getConfig().hashCode();
        /* if obj key */
        hashCode = hashCode * 31 + NullableObjects.hashCode(getKeyEquivalence());
        /* endif */
        /* if obj value */
        hashCode = hashCode * 31 + NullableObjects.hashCode(getValueEquivalence());
        /* elif !(obj value) */
        hashCode = hashCode * 31 + Primitives.hashCode(getDefaultValue());
        /* endif */
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj instanceof HashCharShortMapFactory) {
            HashCharShortMapFactory factory = (HashCharShortMapFactory) obj;
            if (!this.getConfig().equals(factory.getConfig()))
                return false;
            /* if obj key */
            if (!NullableObjects.equals(this.getKeyEquivalence(), factory.getKeyEquivalence()))
                return false;
            /* endif */
            /* if obj value */
            return NullableObjects.equals(this.getValueEquivalence(),
                    factory.getValueEquivalence());
            /* elif !(obj value) */
            return ((Short) this.getDefaultValue()).equals(factory.getDefaultValue());
            /* endif */
        } else {
            return false;
        }
    }

    /* if !(obj value) */
    @Override
    public short getDefaultValue() {
        return /* const value 0 */0;
    }
    /* elif obj value */
    @Override
    public Equivalence<Short> getValueEquivalence() {
        return null;
    }
    /* endif */

    /* define p1 */
    /* if obj key obj value //<K2 extends K, V2 extends V>// elif obj key //<K2 extends K>
    // elif obj value //<V2 extends V>// endif */
    /* enddefine */

    /* define p2 */
    /* if obj key obj value //<K2, V2>// elif obj key //<K2>// elif obj value //<V2>// endif */
    /* enddefine */

    /* define ek */
    /* if obj key //? extends K2// elif !(obj key) //Character// endif */
    /* enddefine */

    /* define ev */
    /* if obj value //? extends V2// elif !(obj value) //Short// endif */
    /* enddefine */

    /* define ep //<// ek //, // ev //>// enddefine */

    /* define ep2 */
    /* if obj key obj value //<? extends K2, ? extends V2>// elif obj key //<? extends K2>
    // elif obj value //<? extends V2>// endif */
    /* enddefine */

    /* define pk *//* if !(obj key) //char// elif obj key //K2// endif *//* enddefine */
    /* define pv *//* if !(obj value) //short// elif obj value //V2// endif *//* enddefine */

    /* define gk *//* if !(obj key) //Character// elif obj key //K2// endif *//* enddefine */
    /* define gv *//* if !(obj value) //Short// elif obj value //V2// endif *//* enddefine */

    private /*p1*/ UpdatableDHashCharShortMapGO/*p2*/ shrunk(UpdatableDHashCharShortMapGO/*p2*/ map) {
        Predicate<HashContainer> shrinkCondition;
        if ((shrinkCondition = hashConf.getShrinkCondition()) != null) {
            if (shrinkCondition.test(map))
                map.shrink();
        }
        return map;
    }

    /* with Updatable|Mutable mutability */
    @Override
    public /*p1*/ UpdatableDHashCharShortMapGO/*p2*/ newUpdatableMap() {
        return newUpdatableMap(hashConf.getDefaultExpectedSize());
    }
    /* endwith */

    @Override
    public /*p1*/ UpdatableDHashCharShortMapGO/*p2*/ newUpdatableMap(
            Map/*ep*/<Character, Short>/**/ map) {
        /* if !(obj key) */
        return shrunk(super.newUpdatableMap(map));
        /* elif obj key //
        return newUpdatableMap(map, map.size());
        // endif */
    }

    @Override
    public /*p1*/ UpdatableDHashCharShortMapGO/*p2*/ newUpdatableMap(
            Map/*ep*/<Character, Short>/**/ map1, Map/*ep*/<Character, Short>/**/ map2) {
        long expectedSize = (long) map1.size();
        expectedSize += (long) map2.size();
        return newUpdatableMap(map1, map2, sizeAsInt(expectedSize));
    }

    @Override
    public /*p1*/ UpdatableDHashCharShortMapGO/*p2*/ newUpdatableMap(
            Map/*ep*/<Character, Short>/**/ map1, Map/*ep*/<Character, Short>/**/ map2,
            Map/*ep*/<Character, Short>/**/ map3) {
        long expectedSize = (long) map1.size();
        expectedSize += (long) map2.size();
        expectedSize += (long) map3.size();
        return newUpdatableMap(map1, map2, map3, sizeAsInt(expectedSize));
    }

    @Override
    public /*p1*/ UpdatableDHashCharShortMapGO/*p2*/ newUpdatableMap(
            Map/*ep*/<Character, Short>/**/ map1, Map/*ep*/<Character, Short>/**/ map2,
            Map/*ep*/<Character, Short>/**/ map3, Map/*ep*/<Character, Short>/**/ map4) {
        long expectedSize = (long) map1.size();
        expectedSize += (long) map2.size();
        expectedSize += (long) map3.size();
        expectedSize += (long) map4.size();
        return newUpdatableMap(map1, map2, map3, map4, sizeAsInt(expectedSize));
    }

    @Override
    public /*p1*/ UpdatableDHashCharShortMapGO/*p2*/ newUpdatableMap(
            Map/*ep*/<Character, Short>/**/ map1, Map/*ep*/<Character, Short>/**/ map2,
            Map/*ep*/<Character, Short>/**/ map3, Map/*ep*/<Character, Short>/**/ map4,
            Map/*ep*/<Character, Short>/**/ map5) {
        long expectedSize = (long) map1.size();
        expectedSize += (long) map2.size();
        expectedSize += (long) map3.size();
        expectedSize += (long) map4.size();
        expectedSize += (long) map5.size();
        return newUpdatableMap(map1, map2, map3, map4, map5, sizeAsInt(expectedSize));
    }

    /* if obj key */
    @Override
    public /*p1*/ UpdatableDHashCharShortMapGO/*p2*/ newUpdatableMap(
            Map/*ep*/<Character, Short>/**/ map, int expectedSize) {
        return shrunk(super.newUpdatableMap(map, expectedSize));
    }
    /* endif */

    @Override
    public /*p1*/ UpdatableDHashCharShortMapGO/*p2*/ newUpdatableMap(
            Map/*ep*/<Character, Short>/**/ map1, Map/*ep*/<Character, Short>/**/ map2,
            int expectedSize) {
        UpdatableDHashCharShortMapGO/*p2*/ map = newUpdatableMap(expectedSize);
        map.putAll(map1);
        map.putAll(map2);
        return shrunk(map);
    }

    @Override
    public /*p1*/ UpdatableDHashCharShortMapGO/*p2*/ newUpdatableMap(
            Map/*ep*/<Character, Short>/**/ map1, Map/*ep*/<Character, Short>/**/ map2,
            Map/*ep*/<Character, Short>/**/ map3, int expectedSize) {
        UpdatableDHashCharShortMapGO/*p2*/ map = newUpdatableMap(expectedSize);
        map.putAll(map1);
        map.putAll(map2);
        map.putAll(map3);
        return shrunk(map);
    }

    @Override
    public /*p1*/ UpdatableDHashCharShortMapGO/*p2*/ newUpdatableMap(
            Map/*ep*/<Character, Short>/**/ map1, Map/*ep*/<Character, Short>/**/ map2,
            Map/*ep*/<Character, Short>/**/ map3, Map/*ep*/<Character, Short>/**/ map4,
            int expectedSize) {
        UpdatableDHashCharShortMapGO/*p2*/ map = newUpdatableMap(expectedSize);
        map.putAll(map1);
        map.putAll(map2);
        map.putAll(map3);
        map.putAll(map4);
        return shrunk(map);
    }

    @Override
    public /*p1*/ UpdatableDHashCharShortMapGO/*p2*/ newUpdatableMap(
            Map/*ep*/<Character, Short>/**/ map1, Map/*ep*/<Character, Short>/**/ map2,
            Map/*ep*/<Character, Short>/**/ map3, Map/*ep*/<Character, Short>/**/ map4,
            Map/*ep*/<Character, Short>/**/ map5, int expectedSize) {
        UpdatableDHashCharShortMapGO/*p2*/ map = newUpdatableMap(expectedSize);
        map.putAll(map1);
        map.putAll(map2);
        map.putAll(map3);
        map.putAll(map4);
        map.putAll(map5);
        return shrunk(map);
    }


    @Override
    public /*p1*/ UpdatableDHashCharShortMapGO/*p2*/ newUpdatableMap(
            Consumer</*f*/CharShortConsumer/*p2*/> entriesSupplier) {
        return newUpdatableMap(entriesSupplier, hashConf.getDefaultExpectedSize());
    }

    @Override
    public /*p1*/ UpdatableDHashCharShortMapGO/*p2*/ newUpdatableMap(
            Consumer</*f*/CharShortConsumer/*p2*/> entriesSupplier, int expectedSize) {
        final UpdatableDHashCharShortMapGO/*p2*/ map = newUpdatableMap(expectedSize);
        entriesSupplier.accept(new /*f*/CharShortConsumer/*p2*/() {
             @Override
             public void accept(/*pk*/char/**/ k, /*pv*/short/**/ v) {
                 map.put(k, v);
             }
         });
        return shrunk(map);
    }

    @Override
    public /*p1*/ UpdatableDHashCharShortMapGO/*p2*/ newUpdatableMap(/*pk*/char/**/[] keys,
            /*pv*/short/**/[] values) {
        return newUpdatableMap(keys, values, keys.length);
    }

    @Override
    public /*p1*/ UpdatableDHashCharShortMapGO/*p2*/ newUpdatableMap(/*pk*/char/**/[] keys,
            /*pv*/short/**/[] values, int expectedSize) {
        UpdatableDHashCharShortMapGO/*p2*/ map = newUpdatableMap(expectedSize);
        int keysLen = keys.length;
        if (keysLen != values.length)
            throw new IllegalArgumentException("keys and values arrays must have the same size");
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i], values[i]);
        }
        return shrunk(map);
    }

    /* if !(obj key obj value) */
    @Override
    public /*p1*/ UpdatableDHashCharShortMapGO/*p2*/ newUpdatableMap(
            /*gk*/Character/**/[] keys, /*gv*/Short/**/[] values) {
        return newUpdatableMap(keys, values, keys.length);
    }

    @Override
    public /*p1*/ UpdatableDHashCharShortMapGO/*p2*/ newUpdatableMap(
            /*gk*/Character/**/[] keys, /*gv*/Short/**/[] values, int expectedSize) {
        UpdatableDHashCharShortMapGO/*p2*/ map = newUpdatableMap(expectedSize);
        int keysLen = keys.length;
        if (keysLen != values.length)
            throw new IllegalArgumentException("keys and values arrays must have the same size");
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i], values[i]);
        }
        return shrunk(map);
    }
    /* endif */

    @Override
    public /*p1*/ UpdatableDHashCharShortMapGO/*p2*/ newUpdatableMap(Iterable</*ek*/Character/**/> keys,
            Iterable</*ev*/Short/**/> values) {
        int expectedSize = keys instanceof Collection ?
                ((Collection) keys).size() :
                hashConf.getDefaultExpectedSize();
        return newUpdatableMap(keys, values, expectedSize);
    }

    @Override
    public /*p1*/ UpdatableDHashCharShortMapGO/*p2*/ newUpdatableMap(Iterable</*ek*/Character/**/> keys,
            Iterable</*ev*/Short/**/> values, int expectedSize) {
        UpdatableDHashCharShortMapGO/*p2*/ map = newUpdatableMap(expectedSize);
        Iterator</*ek*/Character/**/> keysIt = keys.iterator();
        Iterator</*ev*/Short/**/> valuesIt = values.iterator();
        try {
            while (keysIt.hasNext()) {
                map.put(keysIt.next(), valuesIt.next());
            }
            return shrunk(map);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException(
                    "keys and values iterables must have the same size", e);
        }
    }

    @Override
    public /*p1*/ UpdatableDHashCharShortMapGO/*p2*/ newUpdatableMapOf(
            /*pk*/char/**/ k1, /*pv*/short/**/ v1) {
        UpdatableDHashCharShortMapGO/*p2*/ map = newUpdatableMap(1);
        map.put(k1, v1);
        return map;
    }

    @Override
    public /*p1*/ UpdatableDHashCharShortMapGO/*p2*/ newUpdatableMapOf(
            /*pk*/char/**/ k1, /*pv*/short/**/ v1, /*pk*/char/**/ k2, /*pv*/short/**/ v2) {
        UpdatableDHashCharShortMapGO/*p2*/ map = newUpdatableMap(2);
        map.put(k1, v1);
        map.put(k2, v2);
        return map;
    }

    @Override
    public /*p1*/ UpdatableDHashCharShortMapGO/*p2*/ newUpdatableMapOf(
            /*pk*/char/**/ k1, /*pv*/short/**/ v1, /*pk*/char/**/ k2, /*pv*/short/**/ v2,
            /*pk*/char/**/ k3, /*pv*/short/**/ v3) {
        UpdatableDHashCharShortMapGO/*p2*/ map = newUpdatableMap(3);
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        return map;
    }

    @Override
    public /*p1*/ UpdatableDHashCharShortMapGO/*p2*/ newUpdatableMapOf(
            /*pk*/char/**/ k1, /*pv*/short/**/ v1, /*pk*/char/**/ k2, /*pv*/short/**/ v2,
            /*pk*/char/**/ k3, /*pv*/short/**/ v3, /*pk*/char/**/ k4, /*pv*/short/**/ v4) {
        UpdatableDHashCharShortMapGO/*p2*/ map = newUpdatableMap(4);
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        return map;
    }

    @Override
    public /*p1*/ UpdatableDHashCharShortMapGO/*p2*/ newUpdatableMapOf(
            /*pk*/char/**/ k1, /*pv*/short/**/ v1, /*pk*/char/**/ k2, /*pv*/short/**/ v2,
            /*pk*/char/**/ k3, /*pv*/short/**/ v3, /*pk*/char/**/ k4, /*pv*/short/**/ v4,
            /*pk*/char/**/ k5, /*pv*/short/**/ v5) {
        UpdatableDHashCharShortMapGO/*p2*/ map = newUpdatableMap(5);
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        map.put(k5, v5);
        return map;
    }

    /* with Mutable|Immutable mutability */
    /* with with|without expectedSize */
    /* define arg *//* if with expectedSize //, int expectedSize// endif *//* enddefine */
    /* define apply *//* if with expectedSize //, expectedSize// endif *//* enddefine */

     /* if obj key || without expectedSize */
    @Override
    public /*p1*/ HashCharShortMap/*p2*/ newMutableMap(
            Map/*ep*/<Character, Short>/**/ map/*arg*/) {
        MutableDHashCharShortMapGO/*p2*/ res = uninitializedMutableMap();
        res.move(newUpdatableMap(map/*apply*/));
        return res;
    }
    /* endif */

    @Override
    public /*p1*/ HashCharShortMap/*p2*/ newMutableMap(Map/*ep*/<Character, Short>/**/ map1,
            Map/*ep*/<Character, Short>/**/ map2/*arg*/) {
        MutableDHashCharShortMapGO/*p2*/ res = uninitializedMutableMap();
        res.move(newUpdatableMap(map1, map2/*apply*/));
        return res;
    }

    @Override
    public /*p1*/ HashCharShortMap/*p2*/ newMutableMap(Map/*ep*/<Character, Short>/**/ map1,
            Map/*ep*/<Character, Short>/**/ map2, Map/*ep*/<Character, Short>/**/ map3/*arg*/) {
        MutableDHashCharShortMapGO/*p2*/ res = uninitializedMutableMap();
        res.move(newUpdatableMap(map1, map2, map3/*apply*/));
        return res;
    }

    @Override
    public /*p1*/ HashCharShortMap/*p2*/ newMutableMap(Map/*ep*/<Character, Short>/**/ map1,
            Map/*ep*/<Character, Short>/**/ map2, Map/*ep*/<Character, Short>/**/ map3,
            Map/*ep*/<Character, Short>/**/ map4/*arg*/) {
        MutableDHashCharShortMapGO/*p2*/ res = uninitializedMutableMap();
        res.move(newUpdatableMap(map1, map2, map3, map4/*apply*/));
        return res;
    }

    @Override
    public /*p1*/ HashCharShortMap/*p2*/ newMutableMap(Map/*ep*/<Character, Short>/**/ map1,
            Map/*ep*/<Character, Short>/**/ map2, Map/*ep*/<Character, Short>/**/ map3,
            Map/*ep*/<Character, Short>/**/ map4, Map/*ep*/<Character, Short>/**/ map5/*arg*/) {
        MutableDHashCharShortMapGO/*p2*/ res = uninitializedMutableMap();
        res.move(newUpdatableMap(map1, map2, map3, map4, map5/*apply*/));
        return res;
    }

    /* endwith */

    @Override
    public /*p1*/ HashCharShortMap/*p2*/ newMutableMap(
            Consumer</*f*/CharShortConsumer/*p2*/> entriesSupplier) {
        MutableDHashCharShortMapGO/*p2*/ map = uninitializedMutableMap();
        map.move(newUpdatableMap(entriesSupplier));
        return map;
    }

    @Override
    public /*p1*/ HashCharShortMap/*p2*/ newMutableMap(
            Consumer</*f*/CharShortConsumer/*p2*/> entriesSupplier, int expectedSize) {
        MutableDHashCharShortMapGO/*p2*/ map = uninitializedMutableMap();
        map.move(newUpdatableMap(entriesSupplier, expectedSize));
        return map;
    }

    @Override
    public /*p1*/ HashCharShortMap/*p2*/ newMutableMap(/*pk*/char/**/[] keys,
            /*pv*/short/**/[] values) {
        MutableDHashCharShortMapGO/*p2*/ map = uninitializedMutableMap();
        map.move(newUpdatableMap(keys, values));
        return map;
    }

    @Override
    public /*p1*/ HashCharShortMap/*p2*/ newMutableMap(/*pk*/char/**/[] keys,
            /*pv*/short/**/[] values, int expectedSize) {
        MutableDHashCharShortMapGO/*p2*/ map = uninitializedMutableMap();
        map.move(newUpdatableMap(keys, values, expectedSize));
        return map;
    }

    /* if !(obj key obj value) */
    @Override
    public /*p1*/ HashCharShortMap/*p2*/ newMutableMap(
            /*gk*/Character/**/[] keys, /*gv*/Short/**/[] values) {
        MutableDHashCharShortMapGO/*p2*/ map = uninitializedMutableMap();
        map.move(newUpdatableMap(keys, values));
        return map;
    }

    @Override
    public /*p1*/ HashCharShortMap/*p2*/ newMutableMap(
            /*gk*/Character/**/[] keys, /*gv*/Short/**/[] values, int expectedSize) {
        MutableDHashCharShortMapGO/*p2*/ map = uninitializedMutableMap();
        map.move(newUpdatableMap(keys, values, expectedSize));
        return map;
    }
    /* endif */

    @Override
    public /*p1*/ HashCharShortMap/*p2*/ newMutableMap(Iterable</*ek*/Character/**/> keys,
            Iterable</*ev*/Short/**/> values) {
        MutableDHashCharShortMapGO/*p2*/ map = uninitializedMutableMap();
        map.move(newUpdatableMap(keys, values));
        return map;
    }

    @Override
    public /*p1*/ HashCharShortMap/*p2*/ newMutableMap(Iterable</*ek*/Character/**/> keys,
            Iterable</*ev*/Short/**/> values, int expectedSize) {
        MutableDHashCharShortMapGO/*p2*/ map = uninitializedMutableMap();
        map.move(newUpdatableMap(keys, values, expectedSize));
        return map;
    }

    @Override
    public /*p1*/ HashCharShortMap/*p2*/ newMutableMapOf(/*pk*/char/**/ k1, /*pv*/short/**/ v1) {
        MutableDHashCharShortMapGO/*p2*/ map = uninitializedMutableMap();
        map.move(newUpdatableMapOf(k1, v1));
        return map;
    }

    @Override
    public /*p1*/ HashCharShortMap/*p2*/ newMutableMapOf(/*pk*/char/**/ k1, /*pv*/short/**/ v1,
             /*pk*/char/**/ k2, /*pv*/short/**/ v2) {
        MutableDHashCharShortMapGO/*p2*/ map = uninitializedMutableMap();
        map.move(newUpdatableMapOf(k1, v1, k2, v2));
        return map;
    }

    @Override
    public /*p1*/ HashCharShortMap/*p2*/ newMutableMapOf(/*pk*/char/**/ k1, /*pv*/short/**/ v1,
             /*pk*/char/**/ k2, /*pv*/short/**/ v2, /*pk*/char/**/ k3, /*pv*/short/**/ v3) {
        MutableDHashCharShortMapGO/*p2*/ map = uninitializedMutableMap();
        map.move(newUpdatableMapOf(k1, v1, k2, v2, k3, v3));
        return map;
    }

    @Override
    public /*p1*/ HashCharShortMap/*p2*/ newMutableMapOf(/*pk*/char/**/ k1, /*pv*/short/**/ v1,
             /*pk*/char/**/ k2, /*pv*/short/**/ v2, /*pk*/char/**/ k3, /*pv*/short/**/ v3,
             /*pk*/char/**/ k4, /*pv*/short/**/ v4) {
        MutableDHashCharShortMapGO/*p2*/ map = uninitializedMutableMap();
        map.move(newUpdatableMapOf(k1, v1, k2, v2, k3, v3, k4, v4));
        return map;
    }

    @Override
    public /*p1*/ HashCharShortMap/*p2*/ newMutableMapOf(/*pk*/char/**/ k1, /*pv*/short/**/ v1,
             /*pk*/char/**/ k2, /*pv*/short/**/ v2, /*pk*/char/**/ k3, /*pv*/short/**/ v3,
             /*pk*/char/**/ k4, /*pv*/short/**/ v4, /*pk*/char/**/ k5, /*pv*/short/**/ v5) {
        MutableDHashCharShortMapGO/*p2*/ map = uninitializedMutableMap();
        map.move(newUpdatableMapOf(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5));
        return map;
    }
    /* endwith */
}