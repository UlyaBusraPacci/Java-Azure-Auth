package azur.azurAuth.dto;

public record AuthRequest (
        String username,
        String password
){
}