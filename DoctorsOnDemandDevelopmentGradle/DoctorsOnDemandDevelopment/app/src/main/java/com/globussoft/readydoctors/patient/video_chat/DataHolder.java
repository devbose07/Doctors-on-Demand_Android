package com.globussoft.readydoctors.patient.video_chat;


import com.globussoft.readydoctors.patient.Utills.Singleton;

import java.util.ArrayList;
import java.util.List;

public class DataHolder {

    private static final String TAG = DataHolder.class.getSimpleName();
    public static ArrayList<User> usersList;
    public static final String PASSWORD = "12345678";

    public void setUsersList(List<User> UsersList) {
        this.usersList = usersList;
    }

    public int getUserListSize() {
        return usersList.size();
    }

    public String getUserName(int index) {
        return usersList.get(index).getFullName();
    }

    public User getUser(int index) {
        return usersList.get(index);
    }

    public User getLastQBUser() {
        return usersList.get(usersList.size() - 1);
    }

    public void addUserToList(User user) {
        usersList.add(user);
    }

    public static ArrayList<User> getUsersList() {

        if (usersList == null) {
            usersList = new ArrayList<>();

            usersList.add(new User(1, "User 1", "test1", PASSWORD, 6222594));
            usersList.add(new User(2, "User 2", "test2", PASSWORD, 6222603));
            usersList.add(new User(3, "User 3", "webrtc_user3", PASSWORD, 2436257));
            usersList.add(new User(4, "User 4", "webrtc_user4", PASSWORD, 2436258));
            usersList.add(new User(5, "User 5", "webrtc_user5", PASSWORD, 2436259));
            usersList.add(new User(6, "User 6", "webrtc_user6", PASSWORD, 2436262));
            usersList.add(new User(7, "User 7", "webrtc_user7", PASSWORD, 2436263));
            usersList.add(new User(8, "User 8", "webrtc_user8", PASSWORD, 2436265));
            usersList.add(new User(9, "User 9", "webrtc_user9", PASSWORD, 2436266));
            usersList.add(new User(10, "User 10", "webrtc_user10", PASSWORD, 2436269));
        }


        return usersList;
    }


    public static String getUserNameByID(Integer callerID)
    {
        for (User user : getUsersList())
        {
            if (user.getId().equals(callerID))
            {
                return user.getFullName();
            }
        }
        return Singleton.Name;
    }

    public static String getUserNameByLogin(String login)
    {
        for (User user : getUsersList())
        {
            if (user.getLogin().equals(login))
            {
                return user.getFullName();
            }
        }
        return Singleton.Name;
    }

    public static User getUserByID(Integer callerID) {
        for (User user : getUsersList()) {
            if (user.getId().equals(callerID)) {
                return user;
            }
        }
        return null;
    }

    public static int getUserIndexByID(Integer callerID) {
        for (User user : getUsersList()) {
            if (user.getId().equals(callerID)) {
                return usersList.indexOf(user);
            }
        }
        return -1;
    }

    public static int getUserIndexByFullName(String fullName) {
        for (User user : getUsersList()) {
            if (user.getFullName().equals(fullName)) {
                return usersList.indexOf(user);
            }
        }
        return -1;
    }

    public static ArrayList<Integer> getIdsAiiUsers (){
        ArrayList<Integer> ids = new ArrayList<>();
        for (User user : getUsersList()){
            ids.add(user.getId());
        }
        return ids;
    }
}