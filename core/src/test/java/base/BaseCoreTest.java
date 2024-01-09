package base;

import com.victor.kochnev.core.converter.DomainUserMapper;
import com.victor.kochnev.core.converter.DomainUserMapperImpl;
import com.victor.kochnev.core.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public abstract class BaseCoreTest {
    @Mock
    protected UserRepository userRepository;
    @Spy
    protected DomainUserMapper domainUserMapper = new DomainUserMapperImpl();
}
