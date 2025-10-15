package xyz.wochib70.domain.credential;

import xyz.wochib70.domain.IdentifierId;

public interface CredentialRepository {

    /**
     * 根据凭证Id查询凭证
     *
     * @param credentialId 凭证Id
     * @return 凭证
     * @throws NoSuchCredentialException 凭证不存在
     */
    Credential findByIdOrThrow(IdentifierId<Long> credentialId);

    /**
     * 根据使用码查询凭证
     *
     * @param usageCode 使用码
     * @return 凭证
     * @throws NoSuchCredentialException 凭证不存在
     */
    Credential findByUsageCodeOrThrow(String usageCode);

    void save(Credential credential);

    /**
     * 查询使用码
     *
     * @param usageCode 使用码
     * @return 凭证
     */
    Credential queryCredentialByUsageCodeOrThrow(String usageCode);

    /**
     * 更新凭证
     *
     * @param credential 凭证
     */
    void update(Credential credential);
}
