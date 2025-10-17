package xyz.wochib70.domain.credential.cmd;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.credential.Credential;
import xyz.wochib70.domain.credential.CredentialFactory;
import xyz.wochib70.domain.credential.CredentialRepository;

@RequiredArgsConstructor
@Service
public class CreateCredentialCmdHandler {


    private final CredentialRepository credentialRepository;

    private final CredentialFactory credentialFactory;

    public IdentifierId<Long> handle(CreateCredentialCmd cmd) {
        Credential credential = credentialFactory.create(
                cmd.duration(),
                cmd.unusedCount(),
                cmd.userId()
        );
        credentialRepository.save(credential);
        return credential.getCredentialId();
    }
}
