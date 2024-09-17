package com.fresco;

public class NullStrategy {

    static class User {
        public static final EmptyUser emptyUser = new EmptyUser();

        public void updateInfo() {
            System.out.println("Hola");
        }
        
        static private class EmptyUser extends User {

            @Override
            public void updateInfo() {
            }
        }        
    }



    public static void main(String[] args) {

        var user = User.emptyUser;
        System.out.println(user);
        var other = User.emptyUser;
        System.out.println(other);
    }

}
