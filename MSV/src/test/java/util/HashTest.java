package util;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class HashTest {

    @Test
    public void hashTestNotEquals1() {
        assertNotEquals(Hash.generateSha512("asdasd"), Hash.generateSha512("asddfgjkdfgnd"));
    }

    @Test
    public void hashTestNotEquals2() {
        assertNotEquals(Hash.generateSha512("ghfélmhpfgmdhpfgmhlfmghélfgmhélfmghlfégmhlé"),
                Hash.generateSha512("kgmndfgkéldfmgéldfmgéldfmgéldmfgméldfgédlfgmdélfmgéldmfgéldmf"));
    }

    @Test
    public void hashTestNotEquals3() {
        assertNotEquals(Hash.generateSha512("gh45?:!%/%=%félmhpfgmdhpfgmhlfmghélfgmhélfmghlfégmhlé"),
                Hash.generateSha512("kgmndfgkéldfmgé::_?:_!%ldfmgéldfmgéldmfgméldfgédlfgmdélfmgéldmfgéldmf"));
    }

    @Test
    public void hashTestEquals1() {
        assertEquals(Hash.generateSha512("df5g496df4g1d65f1g6d5f1g6df51g65df1g98erfg51;%!/!%?3r2r834nfodno,"),
                Hash.generateSha512("df5g496df4g1d65f1g6d5f1g6df51g65df1g98erfg51;%!/!%?3r2r834nfodno,"));
    }

    @Test
    public void hashTestEquals2() {
        assertEquals(Hash.generateSha512("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"),
                Hash.generateSha512("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
    }


}
