package xyz.wochib70.domain.credential.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xyz.wochib70.domain.credential.Credential;
import xyz.wochib70.domain.credential.CredentialRepository;

@RequiredArgsConstructor
@Service
public class ModifyCredentialUnusedCountCmdHandler {

    private final CredentialRepository credentialRepository;

    private final ApplicationEventPublisher eventPublisher;

    public void handle(ModifyCredentialUnusedCountCmd cmd) {
        Credential credential = credentialRepository.findByIdOrThrow(cmd.credentialId());
        credential.modifyUnusedCount(cmd.unusedCount());
        credentialRepository.update(credential);
        credential.getEvents().forEach(eventPublisher::publishEvent);
    }
}
