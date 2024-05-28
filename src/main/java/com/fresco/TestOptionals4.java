package com.fresco;

import java.util.ArrayList;
import java.util.Optional;

public class TestOptionals4 {
    public static void main(String[] args) {
        record User(String name, String address) {
            public boolean hasAddress() {
                return address != null;
            }
        }
        var users = new ArrayList<User>() {
            {
                add(new User("Elon Musk", "New York"));
                add(new User("Bill Gates", "California"));
                add(new User("Barack Obama", null));
                add(null);
            }
        };
        users.stream()
                .map(u -> Optional.ofNullable(u))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(User::hasAddress)
                .forEach(u -> System.out.println(u.address));
    }

}
