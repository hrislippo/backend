package lippo.hris.system.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final String username;
    private final List<String> roles;
    private final List<String> permissions;
    private final List<String> authorizations;

    public CustomUserDetails(String username, List<String> roles, List<String> permissions, List<String> authorizations) {
        this.username = username;
        this.roles = roles;
        this.permissions = permissions;
        this.authorizations = authorizations;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return null;
    }

    public List<String> getRoles() {return roles;}

    public List<String> getAuthorizations() {return authorizations;}

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
