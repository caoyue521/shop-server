package com.github.caoyue521.shopserver.util;

import com.github.caoyue521.shopserver.entity.User;

public class UserUtil {
   private static  final ThreadLocal<User> userThreadLocal = new ThreadLocal<>();
   public static User get()  {
     User user =  userThreadLocal.get();
     if(null==user){
         throw new RuntimeException("User can not be null");
     }
     return user;
   }
   public static void set(User user){
       userThreadLocal.set(user);
   }
   public static void remove(){
       userThreadLocal.remove();;
   }

}
