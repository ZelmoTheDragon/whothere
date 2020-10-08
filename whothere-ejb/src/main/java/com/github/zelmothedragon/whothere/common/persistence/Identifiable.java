package com.github.zelmothedragon.whothere.common.persistence;

public interface Identifiable<K> {

    K getId();

    void setId(K id);
}
