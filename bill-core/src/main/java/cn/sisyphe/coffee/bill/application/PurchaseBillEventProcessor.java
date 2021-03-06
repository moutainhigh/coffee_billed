package cn.sisyphe.coffee.bill.application;

import cn.sisyphe.coffee.bill.amqp.SenderService;
import cn.sisyphe.coffee.bill.domain.base.behavior.BehaviorEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Created by heyong on 2017/12/25 15:55
 * Description: 进货单事件监听
 *
 * @author heyong
 */
@Service
public class PurchaseBillEventProcessor {
    @Autowired
    PurchaseBillManager purchaseBillManager;

    @Autowired
    SenderService senderService;
    /**
     * 已创建事件
     *
     * @param event
     */
    @EventListener(condition = "#event.billType.toString() ==  'PURCHASE' and #event.billState.toString() == 'SAVED'")
    public void billSave(BehaviorEvent event) {
        System.err.println("SAVED:" + event.getBill());
    }

    /**
     * 已提交事件
     *
     * @param event
     */
    @EventListener(condition = "#event.billType.toString() ==  'PURCHASE' and #event.billState.toString() == 'SUBMITTED'")
    public void billSubmit(BehaviorEvent event) {
        System.err.println("SUBMITTED:" + event.getBill());
    }

    /**
     * 审核失败事件
     *
     * @param event
     */
    @EventListener(condition = "#event.billType.toString() ==  'PURCHASE' and #event.billState.toString() == 'AUDITFAILURE'")
    public void billFailure(BehaviorEvent event) {
        System.err.println("AUDITFAILURE:" + event.getBill());
    }

    /**
     * 审核成功事件
     *
     * @param event
     */
    @EventListener(condition = "#event.billType.toString() ==  'PURCHASE' and #event.billState.toString() == 'AUDITSUCCESS'")
    public void billSuccess(BehaviorEvent event) {
        System.err.println("AUDITSUCCESS:" + event.getBill());
        // 发送消息到冲减系统
        senderService.sendBillToStockOffsetRabbitMQ(event.getBill());
    }

    /**
     * 冲减完成事件
     *
     * @param event
     */
    @EventListener(condition = "#event.billType.toString() ==  'PURCHASE' and #event.billState.toString() == 'DONE'")
    public void billDone(BehaviorEvent event) {
        System.err.println("DONE:" + event.getBill());
    }
}
