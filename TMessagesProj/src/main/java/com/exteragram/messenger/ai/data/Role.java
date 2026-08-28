package com.exteragram.messenger.ai.data;

public class Role extends app.nebulagram.messenger.ai.data.Role {

    public static final Role USER = new Role("user", "");
    public static final Role ASSISTANT = new Role("assistant", "");
    public static final Role SYSTEM = new Role("system", "");

    public Role(String name, String prompt) {
        super(name, prompt);
    }
}
