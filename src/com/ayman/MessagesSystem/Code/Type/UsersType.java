package com.ayman.MessagesSystem.Code.Type;

public enum UsersType {

    ADMINS(1, "Admin"), USER(2, "User");

    private int id;
    private String type;

    private UsersType(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public static UsersType getUserTypeById(int id) {
        //by id will return admin or user
        for (UsersType usersType : UsersType.values()) {
            if (usersType.getId() == id) {
                return usersType;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
