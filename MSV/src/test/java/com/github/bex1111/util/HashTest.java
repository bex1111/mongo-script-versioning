package com.github.bex1111.util;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class HashTest {

    @Test
    public void hashTestNotEquals1() {
        Assertions.assertNotEquals(Hash.generateSha512("asdasd"), Hash.generateSha512("asddfgjkdfgnd"));
    }

    @Test
    public void hashTestNotEquals2() {
        Assertions.assertNotEquals(Hash.generateSha512("ghfélmhpfgmdhpfgmhlfmghélfgmhélfmghlfégmhlé"),
                Hash.generateSha512("kgmndfgkéldfmgéldfmgéldfmgéldmfgméldfgédlfgmdélfmgéldmfgéldmf"));
    }

    @Test
    public void hashTestNotEquals3() {
        Assertions.assertNotEquals(Hash.generateSha512("gh45?:!%/%=%félmhpfgmdhpfgmhlfmghélfgmhélfmghlfégmhlé"),
                Hash.generateSha512("kgmndfgkéldfmgé::_?:_!%ldfmgéldfmgéldmfgméldfgédlfgmdélfmgéldmfgéldmf"));
    }

    @Test
    public void hashTestEquals1() {
        Assertions.assertEquals(Hash.generateSha512("df5g496df4g1d65f1g6d5f1g6df51g65df1g98erfg51;%!/!%?3r2r834nfodno,"),
                Hash.generateSha512("df5g496df4g1d65f1g6d5f1g6df51g65df1g98erfg51;%!/!%?3r2r834nfodno,"));
    }

    @Test
    public void hashTestEquals2() {
        Assertions.assertEquals(Hash.generateSha512("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"),
                Hash.generateSha512("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
    }


}
