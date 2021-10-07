package ir.ceit.resa.service;

import android.content.Context;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ir.ceit.resa.service.storage.ResaSharedPreferences;
import ir.ceit.resa.model.ERole;
import ir.ceit.resa.model.UserProfile;

public class UserProfileUtil {

    public static ERole getHighLevelRole(List<String> roles) {
        boolean creatorFound = false;
        for (int i = 0; i < roles.size(); i++) {
            if (roles.get(i).equals("ROLE_ADMIN")) {
                return ERole.ROLE_ADMIN;
            } else if (roles.get(i).equals("ROLE_CREATOR")) {
                creatorFound = true;
            }
        }
        if (creatorFound) {
            return ERole.ROLE_CREATOR;
        }
        return ERole.ROLE_USER;
    }

    public static Set<String> getRoleSet(ERole highestLevelRole){
        Set<String> roles = new HashSet<>();
        switch (highestLevelRole){
            case ROLE_USER:
                roles.add(Constants.USER);
                break;
            case ROLE_CREATOR:
                roles.add(Constants.USER);
                roles.add(Constants.CREATOR);
                break;
            case ROLE_ADMIN:
                roles.add(Constants.USER);
                roles.add(Constants.CREATOR);
                roles.add(Constants.ADMIN);
                break;
            default:
                break;
        }
        return roles;
    }

    public static UserProfile createUserProfile(Context context) {
        if (ResaSharedPreferences.getToken(context) == null)
            return null;

        String username = ResaSharedPreferences.getUserName(context);
        String email = ResaSharedPreferences.getUserEmail(context);
        String firstname = ResaSharedPreferences.getUserFirstName(context);
        String lastname = ResaSharedPreferences.getUserLastName(context);
        String faculty = ResaSharedPreferences.getUserFaculty(context);
        ERole role = ResaSharedPreferences.getRole(context);

        if (username == null || email == null || firstname == null || lastname == null || role == null)
            return null;

        return new UserProfile(username, email, firstname, lastname, faculty, role);
    }
}
