package com.driver;

import java.util.*;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/whatsapp")
public class WhatsappController {

    //Autowired will not work in this case, no need to change this and add autowired
    WhatsappService whatsappService = new WhatsappService();


    @PostMapping("/add-user")
    public String createUser(@RequestParam String name,@RequestParam String mobile) throws Exception {
        //If the mobile number exists in database, throw "User already exists" exception
        //Otherwise, create the user and return "SUCCESS"
//        try{
//            whatsappService.createUser(name, mobile);
//            return "SUCCESS";
//        }catch (UserAlreadyExistsException exp){
//            throw new UserAlreadyExistsException();
//        }
//        catch (Exception exp){
//            throw new Exception("Something went wrong");
//        }

        return whatsappService.createUser(name, mobile);
    }

    @PostMapping("/add-group")
    public Group createGroup(@RequestBody List<User> users){

//        {
//            "users" : [
//                        {"name" : "Rahul", "mobile" : "987" },
//                        {"name" : "Mohit", "mobile" : "456"}
//                    ]
//        }


        // The list contains at least 2 users where the first user is the admin. A group has exactly one admin.
        // If there are only 2 users, the group is a personal chat and the group name should be kept as the name of the second user(other than admin)
        // If there are 2+ users, the name of group should be "Group count". For example, the name of first group would be "Group 1", second would be "Group 2" and so on.
        // Note that a personal chat is not considered a group and the count is not updated for personal chats.
        // If group is successfully created, return group.

        //For example: Consider userList1 = {Alex, Bob, Charlie}, userList2 = {Dan, Evan}, userList3 = {Felix, Graham, Hugh}.
        //If createGroup is called for these userLists in the same order, their group names would be "Group 1", "Evan", and "Group 2" respectively.

        return whatsappService.createGroup(users);

    }

    @PostMapping("/add-message")
    public int createMessage(String content){
        // The 'i^th' created message has message id 'i'.
        // Return the message id.

        return whatsappService.createMessage(content);
    }



    @PutMapping("/send-message")
    public int sendMessage(Message message, User sender, Group group) throws Exception{
        //Throw "Group does not exist" if the mentioned group does not exist
        //If the sender is not a member of the group, the application will throw an exception.
        //Throw "You are not allowed to send message" if the sender is not a member of the group
        //If the message is sent successfully, return the final number of messages in that group.

//        try {
//            Integer numberOfMessage = whatsappService.sendMessage(message,sender,group);
//            return numberOfMessage;
//        }
        return whatsappService.sendMessage(message,sender,group);
//        catch (GroupDoesNotExistException exp){
//            throw new GroupDoesNotExistException();
//        }catch (SenderNotMemberException exp){
//            throw new SenderNotMemberException();
//        }
//        catch (Exception exp){
//            throw new Exception();
//        }
    }

    @PutMapping("/change-admin")
    public String changeAdmin(User approver, User user, Group group) throws Exception{
        //Throw "Group does not exist" if the mentioned group does not exist
        //Throw "Approver does not have rights" if the approver is not the current admin of the group
        //Throw "User is not a participant" if the user is not a part of the group
        //Change the admin of the group to "user" and return "SUCCESS". Note that at one time there is only one admin and the admin rights are transferred from approver to user.
        return whatsappService.changeAdmin(approver,user,group);

//        try {
//            whatsappService.changeAdmin(approver,user,group);
//            return "SUCCESS";
//        }catch (GroupDoesNotExistException exp){
//            throw new GroupDoesNotExistException();
//        }catch (UserNotMemberException exp){
//            throw new UserNotMemberException();
//        }catch (ApproverNotAdminException exp){
//            throw new ApproverNotAdminException();
//        }
//        catch (Exception exp){
//            throw new Exception("Something went Wrong");
//     }

    }

    @DeleteMapping("/remove-user")
    public int removeUser(User user) throws Exception{
        //This is a bonus problem and does not contain any marks
        //A user belongs to exactly one group
        //If user is not found in any group, throw "User not found" exception
        //If user is found in a group, and it is the admin, throw "Cannot remove admin" exception
        //If user is not the admin, remove the user from the group, remove all its messages from all the databases, and update relevant attributes accordingly.
        //If user is removed successfully, return (the updated number of users in the group + the updated number of messages in group + the updated number of overall messages)

        return whatsappService.removeUser(user);
    }

    @GetMapping("/find-messages")
    public String findMessage(Date start, Date end, int K) throws Exception{
        //This is a bonus problem and does not contain any marks
        // Find the Kth the latest message between start and end (excluding start and end)
        // If the number of messages between given time is less than K, throw "K is greater than the number of messages" exception

        return whatsappService.findMessage(start, end, K);
    }
}