package org.example.domain;

import org.example.data.AppUserRepository;
import org.example.models.AppUser;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService implements UserDetailsService {

    private final AppUserRepository repository;

    private final PasswordEncoder encoder;

    public AppUserService(AppUserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String appUserUsername) throws UsernameNotFoundException {
        AppUser appUser = repository.findAppUserByUsername(appUserUsername);

        if (appUser == null || !appUser.isEnabled()) {
            throw new UsernameNotFoundException(appUserUsername + " could not be found.");
        }

        return appUser;
    }

    public Result<AppUser> addAppUser(String email, String username, String password) {
        Result<AppUser> result = new Result<>();

        password = encoder.encode(password);
        AppUser appUser = new AppUser(0, email, username, password, true, List.of("runner"));

        validateAdd(email, username, password);
        if (!result.isSuccess()) {
            return result;
        }

        validateAppUserId(appUser, result);
        if (!result.isSuccess()) {
            return result;
        } else {
            result.setPayload(repository.addAppUser(appUser));
        }

        return result;
    }

    public Result<AppUser> deleteAppUser(int appUserId) throws DataAccessException {
        Result<AppUser> result = new Result<>();

        if (!repository.deleteAppUser(appUserId)) {
            result.addMessage(ActionStatus.NOT_FOUND, "This user cannot be found.");
            return result;
        }

        return result;
    }

    private Result<AppUser> validateAdd(String email, String username, String password) {
        Result<AppUser> result = new Result<>();

        // EMAIL VALIDATION
        validateEmailNulls(email, result);
        if (!result.isSuccess()) {
            return result;
        }

        // USERNAME VALIDATION
        validateUsernameNulls(username, result);
        if (!result.isSuccess()) {
            return result;
        }

        validateDuplicateUsername(username, result);
        if (!result.isSuccess()) {
            return result;
        }

        validateUsernameFieldLength(username, result);
        if (!result.isSuccess()) {
            return result;
        }

        // PASSWORD VALIDATION
        validatePasswordNulls(password, result);
        if (!result.isSuccess()) {
            return result;
        }

        validatePasswordFieldLength(password, result);
        if (!result.isSuccess()) {
            return result;
        }

        return result;
    }

    // EMAIL VALIDATION
    private Result<AppUser> validateEmailNulls(String email, Result<AppUser> result) {
        if (email == null || email.isBlank()) {
            result.addMessage(ActionStatus.INVALID, "Email is required.");
            return result;
        }

        return result;
    }

    // USERNAME VALIDATION
    private Result<AppUser> validateUsernameNulls(String username, Result<AppUser> result) {
        if (username == null || username.isBlank()) {
            result.addMessage(ActionStatus.INVALID, "Username is required.");
            return result;
        }

        return result;
    }

    private Result<AppUser> validateDuplicateUsername(String username, Result<AppUser> result) {
        AppUser existingAppUser = repository.findAppUserByUsername(username);

        if (username.equals(existingAppUser.getUsername())) {
            result.addMessage(ActionStatus.DUPLICATE, "This username is taken.");
        }

        return result;
    }

    private Result<AppUser> validateUsernameFieldLength(String username, Result<AppUser> result) {
        if (username.length() > 32) {
            result.addMessage(ActionStatus.INVALID, "Username cannot be greater than 32 characters.");
        }

        return result;
    }

    // PASSWORD VALIDATION
    private Result<AppUser> validatePasswordNulls(String password, Result<AppUser> result) {
        if (password == null || password.isBlank()) {
            result.addMessage(ActionStatus.INVALID, "Password is required.");
            return result;
        }

        return result;
    }

    private Result<AppUser> validatePasswordFieldLength(String password, Result<AppUser> result) {
        if (password.length() < 8) {
            result.addMessage(ActionStatus.INVALID, "Password must contain at least 8 characters.");
            return result;
        }

        return result;
    }

    // APPUSERID VALIDATION
    private Result<AppUser> validateAppUserId(AppUser appUser, Result<AppUser> result) {
        if (appUser.getAppUserId() != 0) {
            result.addMessage(ActionStatus.INVALID, "You cannot set this user's ID.");
            return result;
        }

        return result;
    }

}
