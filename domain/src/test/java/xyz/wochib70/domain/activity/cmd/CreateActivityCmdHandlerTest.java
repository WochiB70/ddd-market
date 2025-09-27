package xyz.wochib70.domain.activity.cmd;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.activity.*;

import java.time.LocalDateTime;
import java.util.Collections;

@SpringBootTest
class CreateActivityCmdHandlerTest extends ActivityTestBase {

    @Resource
    private CreateActivityCmdHandler handler;

    @Test
    public void createActivityTest() {
        IdentifierId<Long> activityId = handler.handle(new CreateActivityCmd(
                new ActivityInfo("测试创建活动", "", Collections.emptyList()),
                new ActivityDuration(LocalDateTime.now(), LocalDateTime.of(2029, 4, 12, 23, 59, 59)),
                new ActivityCountLimit(CountLimitType.INFINITE, 0),
                false,
                ActivityAwardType.REDEEM
        ));
        Assert.isTrue(activityId.getId() == 1, "创建成功");
    }

    @Test
    public void createActivityFailTest() {
        try {
            handler.handle(new CreateActivityCmd(
                    null,
                    new ActivityDuration(LocalDateTime.now(), LocalDateTime.of(2029, 4, 12, 23, 59, 59)),
                    new ActivityCountLimit(CountLimitType.INFINITE, 0),
                    false,
                    ActivityAwardType.REDEEM
            ));
        } catch (Exception e) {
            Assert.isTrue(e instanceof NullPointerException, "创建失败");
            Assert.isTrue(e.getMessage().contains("Activity基本信息不能为null"), "创建失败");
        }
    }

    @Test
    public void createActivityFailTest2() {
        try {
            handler.handle(new CreateActivityCmd(
                    new ActivityInfo("测试创建活动", "", Collections.emptyList()),
                    null,
                    new ActivityCountLimit(CountLimitType.INFINITE, 0),
                    false,
                    ActivityAwardType.REDEEM
            ));
        } catch (Exception e) {
            Assert.isTrue(e instanceof NullPointerException, "创建失败");
            Assert.isTrue(e.getMessage().contains("Activity持续时间不能为null"), "创建失败");
        }
    }

    @Test
    public void createActivityFailTest3() {
        try {
            handler.handle(new CreateActivityCmd(
                    new ActivityInfo("测试创建活动", "", Collections.emptyList()),
                    new ActivityDuration(LocalDateTime.now(), LocalDateTime.of(2029, 4, 12, 23, 59, 59)),
                    null,
                    false,
                    ActivityAwardType.REDEEM
            ));
        } catch (Exception e) {
            Assert.isTrue(e instanceof NullPointerException, "创建失败");
            Assert.isTrue(e.getMessage().contains("Activity人数限制不能为null"), "创建失败");
        }
    }

    @Test
    public void createActivityFailTest4() {
        try {
            handler.handle(new CreateActivityCmd(
                    new ActivityInfo("测试创建活动", "", Collections.emptyList()),
                    new ActivityDuration(LocalDateTime.now(), LocalDateTime.of(2029, 4, 12, 23, 59, 59)),
                    new ActivityCountLimit(CountLimitType.INFINITE, 0),
                    null,
                    ActivityAwardType.REDEEM
            ));
        } catch (Exception e) {
            Assert.isTrue(e instanceof NullPointerException, "创建失败");
            Assert.isTrue(e.getMessage().contains("Activity凭证限制不能为null"), "创建失败");
        }
    }

    @Test
    public void createActivityFailTest5() {
        try {
            handler.handle(new CreateActivityCmd(
                    new ActivityInfo("测试创建活动", "", Collections.emptyList()),
                    new ActivityDuration(LocalDateTime.now(), LocalDateTime.of(2029, 4, 12, 23, 59, 59)),
                    new ActivityCountLimit(CountLimitType.INFINITE, 0),
                    false,
                    null
            ));
        } catch (Exception e) {
            Assert.isTrue(e instanceof NullPointerException, "创建失败");
            Assert.isTrue(e.getMessage().contains("Activity奖品类型不能为null"), "创建失败");
        }
    }

    @Test
    public void createActivityFailTest6() {
        try {
            handler.handle(new CreateActivityCmd(
                    new ActivityInfo("", "", Collections.emptyList()),
                    new ActivityDuration(LocalDateTime.now(), LocalDateTime.of(2029, 4, 12, 23, 59, 59)),
                    new ActivityCountLimit(CountLimitType.INFINITE, 0),
                    false,
                    ActivityAwardType.REDEEM
            ));
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "创建失败");
            Assert.isTrue(e.getMessage().contains("activity name不能为null或是空白"), "创建失败");
        }
    }

    @Test
    public void createActivityFailTest7() {
        try {
            handler.handle(new CreateActivityCmd(
                    new ActivityInfo("测试创建活动", "", Collections.emptyList()),
                    new ActivityDuration(LocalDateTime.of(2029, 4, 12, 23, 59, 59), LocalDateTime.now()),
                    new ActivityCountLimit(CountLimitType.INFINITE, 0),
                    false,
                    ActivityAwardType.REDEEM
            ));
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "创建失败");
            Assert.isTrue(e.getMessage().contains("开始时间不能大于结束时间"), "创建失败");
        }
    }

    @Test
    public void createActivityFailTest8() {
        try {
            handler.handle(new CreateActivityCmd(
                    new ActivityInfo("测试创建活动", "", Collections.emptyList()),
                    new ActivityDuration(LocalDateTime.now(), LocalDateTime.of(2029, 4, 12, 23, 59, 59)),
                    new ActivityCountLimit(null, 0),
                    false,
                    ActivityAwardType.REDEEM
            ));
        } catch (Exception e) {
            Assert.isTrue(e instanceof NullPointerException, "创建失败");
            Assert.isTrue(e.getMessage().contains("限制类型不能为空"), "创建失败");
        }
    }

    @Test
    public void createActivityFailTest9() {
        try {
            handler.handle(new CreateActivityCmd(
                    new ActivityInfo("测试创建活动", "", Collections.emptyList()),
                    new ActivityDuration(LocalDateTime.now(), LocalDateTime.of(2029, 4, 12, 23, 59, 59)),
                    new ActivityCountLimit(CountLimitType.DAY_COUNT, -1),
                    false,
                    ActivityAwardType.REDEEM
            ));
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "创建失败");
            Assert.isTrue(e.getMessage().contains("限制数量不能小于0"), "创建失败");
        }
    }

    @Test
    public void createActivityFailTest10() {
        try {
            handler.handle(new CreateActivityCmd(
                    new ActivityInfo("测试创建活动", "测试描述".repeat(50), Collections.emptyList()),
                    new ActivityDuration(LocalDateTime.now(), LocalDateTime.of(2029, 4, 12, 23, 59, 59)),
                    new ActivityCountLimit(CountLimitType.INFINITE, 0),
                    false,
                    ActivityAwardType.REDEEM
            ));
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "创建失败");
            Assert.isTrue(e.getMessage().contains("activity description长度不能超过200"), "创建失败");
        }
    }

    @Test
    public void createActivityFailTest11() {
        try {
            handler.handle(new CreateActivityCmd(
                    new ActivityInfo("测试创建活动", "", Collections.nCopies(6, "image")),
                    new ActivityDuration(LocalDateTime.now(), LocalDateTime.of(2029, 4, 12, 23, 59, 59)),
                    new ActivityCountLimit(CountLimitType.INFINITE, 0),
                    false,
                    ActivityAwardType.REDEEM
            ));
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "创建失败");
            Assert.isTrue(e.getMessage().contains("activity images数量不能超过5"), "创建失败");
        }
    }
}
