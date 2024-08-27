package com.myapps.mywallet.utility;

import com.password4j.BcryptFunction;
import com.password4j.Hash;
import com.password4j.Password;
import com.password4j.types.Bcrypt;

public class Hashing {

    private static final BcryptFunction bcryptFunction = BcryptFunction.getInstance(Bcrypt.B, 12);

    public Hashing() {}

    public static String hashPassword(String password) {
        Hash hash = Password.hash(password)
                .addPepper("shared-secret")
                .with(bcryptFunction);

        return hash.getResult();
    }

    public static boolean compareHash(String inputPassword, String dbPassword) {
        return Password.check(inputPassword, dbPassword)
                .addPepper("shared-secret")
                .with(bcryptFunction);
    }
}
