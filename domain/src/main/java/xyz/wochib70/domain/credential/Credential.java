package xyz.wochib70.domain.credential;

import xyz.wochib70.domain.Aggregate;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;

public sealed interface Credential extends Aggregate<Long, Long> permits CredentialImpl {

    IdentifierId<Long> getCredentialId();


    /**
     * 参与活动
     *
     * @param userId 参与的用户id
     */
    void participate(UserId userId);

    /**
     * 禁用凭证
     */
    void invalid();

    /**
     * 启用凭证
     */
    void valid();

    /**
     * 修改凭证有效期
     *
     * @param duration 修改的凭证有效期
     */
    void modifyDuration(CredentialDuration duration);


    /**
     * 修改未使用次数
     *
     * @param unusedCount 修改的未使用次数
     * @throws CredentialUnusedCountInvalidException 修改的未使用次数不能小于0
     */
    void modifyUnusedCount(int unusedCount);
}
