package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below-mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashSet<String> userMobile;
    private int customGroupCount;
    private int messageId;

    //HashMap<String,User> userMap = new HashMap<>();


    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }

    public String saveUser(String name, String mobile) throws Exception {

        if(userMobile.contains(mobile)){
            throw new Exception("User already exists");
        }
        userMobile.add(mobile);
        User user = new User(name,mobile);
        return "SUCCESS";
        // userMap.put(name,user);
    }



    public Group createGroup(List<User> users) {

        List<User> listOfUser = users;
        User admin = listOfUser.get(0);
        String groupName = "";
        int numberOfParticipants = listOfUser.size();

        if(listOfUser.size() == 2){//the group is a personal chat
            groupName = listOfUser.get(1).getName();
        }
        else{
            this.customGroupCount += 1;
            groupName = "Group " + customGroupCount;
        }

        Group group = new Group(groupName,numberOfParticipants);
        adminMap.put(group,admin);// add admin to the adminMap
        groupUserMap.put(group,users);// add the list of group to the group-user-Map
        return  group;
    }

    public int createMessage(String content) {
        this.messageId += 1;
        Message message = new Message(messageId,content);
        return messageId;
    }



    public int sendMessage(Message message, User sender, Group group) throws Exception{

        if(adminMap.containsKey(group)){
            List<User> users = groupUserMap.get(group);
            Boolean userFound = false;
            for(User user: users){
                if(user.equals(sender)){
                    userFound = true;
                    break;
                }
            }
            if(userFound){
                senderMap.put(message, sender);
                if(groupUserMap.containsKey(group)){
                    if(groupMessageMap.get(group) !=null ){
                        List<Message> messages = groupMessageMap.get(group);// it was giving me null pointer exception

                        messages.add(message);
                        groupMessageMap.put(group, messages);
                        return messages.size();
                    }else{
                        List<Message> newMessage = new ArrayList<>();
                        newMessage.add(message);
                        groupMessageMap.put(group, newMessage);
                        return newMessage.size();
                    }

                }

            }
            throw new Exception("You are not allowed to send message");
        }
        throw new Exception("Group does not exist");
    }

    public String changeAdmin(User approver, User user, Group group) throws Exception {

        if(groupUserMap.containsKey(group)){
            if(adminMap.containsKey(group)){
                List<User> listOfUser = groupUserMap.get(group);
                if(listOfUser.contains(user)) {

                    adminMap.put(group,user);
                    return "SUCCESS";
                }
                throw new Exception("User is not a participant");
            }
            throw new Exception("Approver does not have rights");
        }
        throw new Exception("Group does not exist");
    }

    public int removeUser(User user) {
        return 0;
    }

    public String findMessage(Date start, Date end, int k) {
        //This is a bonus problem and does not contain any marks
        // Find the Kth the latest message between start and end (excluding start and end)
        // If the number of messages between given time is less than K, throw "K is greater than the number of messages" exception

        return "Aakash";

    }
}