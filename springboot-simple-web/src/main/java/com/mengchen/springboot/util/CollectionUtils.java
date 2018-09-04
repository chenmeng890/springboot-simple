package com.mengchen.springboot.util;

import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by chenmeng12 on 2017/11/24.
 */
public class CollectionUtils {
    public static String[] toArray(List<String> list) {
        String[] strings = new String[list.size()];
        return list.toArray(strings);
    }

    public static Collection<String> trimCollection(Collection<String> collection) {
        if (collection == null) {
            return null;
        } else {
            int i = 0;
            Iterator str = collection.iterator();

            while (str.hasNext()) {
                String e = (String) str.next();
                if (StringUtils.isBlank(e)) {
                    ++i;
                }
            }

            if (i == collection.size()) {
                return null;
            } else {
                return collection;
            }
        }
    }
}
