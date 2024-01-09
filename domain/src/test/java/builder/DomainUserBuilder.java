package builder;

import com.victor.kochnev.domain.entity.User;

import java.time.ZonedDateTime;
import java.util.UUID;

public class DomainUserBuilder {
    public static final String DEFAULT_EMAIL = "victor_k02@mail.ru";
    private DomainUserBuilder() {
    }

    public static User.UserBuilder<?, ?> persistedSimpleUser() {
        return User.builder()
                .id(UUID.randomUUID())
                .createDate(ZonedDateTime.now())
                .lastChangeDate(ZonedDateTime.now())
                .version(0L)
                .email(DEFAULT_EMAIL)
                .password("password");
    }
}
