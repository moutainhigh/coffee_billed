package cn.sisyphe.coffee.bill;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
/**
 *@date: 2017/12/29
 *@description:
 *@author：xieweiguang
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClientApplication.class)
public class FlowTest {

/**
 *
 *notes :
 *  一般步骤：
 *      1，获取构建标准DTO copy property—— business bill
 *      2，工厂模式 传入business bill生成对应的service
 *      3，装配注入，工厂模式生成的总服务台并没有注入所有的属性如：JpaRepo behavior
 *      4，开启各种服务调用
 *          核心：dispose（new several behavior）策略模式
 *             注：new behavior 是lambda表达式 预示着behavior接口方法里的代码块会执行 执行前会注入behavior并完成反向注入
 *                     特别的，purpose behavior 会进一步调用 purpose.handle()
 *          其次：JpaMethod（）委托给Repo
 *          最后：sendEvent , provide Event with Behavior which injected in step 3（依赖于behavior）
 *              注：事件是有状态的business bill+ 几个Enum的封装
 *
 */
    @Test
    public void juice(){

    }


}
