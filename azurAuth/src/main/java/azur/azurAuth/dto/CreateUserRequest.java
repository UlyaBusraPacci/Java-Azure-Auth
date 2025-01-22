package azur.azurAuth.dto;

import lombok.Builder;

import java.util.Set;

import azur.azurAuth.model.Role;


@Builder
public record CreateUserRequest(
        String name,
        String username,
        String password,
        Set<Role> authorities
){
}